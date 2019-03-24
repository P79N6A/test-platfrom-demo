package com.demo.learn.template;

import com.demo.learn.HttpExchange;

import com.demo.learn.common.client.http.HttpClientRequest;
import com.demo.learn.common.client.http.HttpClientResponse;
import com.demo.learn.common.converter.http.strategy.HttpJsonEntityConverter;
import com.demo.learn.common.converter.http.HttpEntityConverter;
import com.demo.learn.common.header.Header;
import com.demo.learn.common.header.http.HttpHeader;
import com.demo.learn.common.status.http.HttpStatus;
import com.demo.learn.exception.HttpExchangeException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpTemplate<REQ, RESP> implements HttpExchange<REQ, RESP> {
    private Logger logger = LoggerFactory.getLogger(HttpTemplate.class);

    private List<HttpEntityConverter<REQ>> requestMessageConverters;
    private List<HttpEntityConverter<RESP>> responseMessageConverters;
    private OkHttpClient okHttpClient = new OkHttpClient();

    public HttpTemplate() {
        requestMessageConverters = new ArrayList<>();
        requestMessageConverters.add(new HttpJsonEntityConverter<>());

        responseMessageConverters = new ArrayList<>();
        responseMessageConverters.add(new HttpJsonEntityConverter<>());

    }


    @Override
    public HttpClientResponse<RESP> exchange(HttpClientRequest<REQ> requestMessage, Class<RESP> responseType) {
        if (requestMessage==null) throw new IllegalArgumentException("request should not be null.");


        Request request;
        try {
            request = buildRequest(requestMessage);
        } catch (IOException e) {
            logger.error("error occurred while exchanging throw HTTP.", e);
            throw new HttpExchangeException("error occurred while exchanging throw HTTP.", e);
        }


        try (Response response = okHttpClient.newCall(request).execute()) {
            //TODO, using message converter to convert the message and it can be easily translate into RESP type.
            return buildResponse(response, responseType);
        } catch (IOException e) {
            throw new HttpExchangeException("http request error", e);
        }
    }

    private HttpClientResponse<RESP> buildResponse(Response response, Class<RESP> responseType) throws IOException {

//        logger.debug("get response: {}", new String(response.body().bytes(), StandardCharsets.UTF_8));
        HttpStatus status = HttpStatus.resolve(response.code());
        HttpHeader header = new HttpHeader().parseOkHttpHeaders(response.headers());
        MediaType mediaType = MediaType.parse(getContentType(header));
        HttpClientResponse<RESP> clientResponse = new HttpClientResponse<>(header, status);


        ResponseBody responseBody = response.body();
        if (responseBody!=null) {

        }


        RESP resp = null;

        //TODO. refactor it later. to Chain of Responsibility Pattern. this is a temporary solution.
        for (HttpEntityConverter<RESP> messageConverters : getResponseMessageConverters()) {
            if (messageConverters.canRead(responseType, mediaType)) {
                resp = messageConverters.read(responseType, responseBody.bytes());
            }
        }
        clientResponse.setBody(resp);

        return clientResponse;

    }


    private Request buildRequest(HttpClientRequest<REQ> requestMessage) throws IOException {
        MediaType mediaType = MediaType.parse(getContentType(requestMessage.getHeaders()));

        RequestBody body =null;

        if (requestMessage.getBody()!=null) {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            //TODO. refactor it later. to Chain of Responsibility Pattern. this is a temporary solution.
            for (HttpEntityConverter<REQ> messageConverters : getRequestMessageConverters()) {
                if (messageConverters.canWrite(requestMessage.getBody().getClass(), mediaType)) {
                    messageConverters.write(requestMessage.getBody(), outputStream);
                    break;
                }
                //TODO return default handler about how to convert the message.
            }

            byte[] formattedResult = outputStream.toByteArray();
            body = RequestBody.create(mediaType, formattedResult);

        }


        Request request;
        try {
            request = new Request.Builder().headers(buildOkHttpHeaders(requestMessage.getHeaders())).method(requestMessage.getMethod().toString(), body).url(requestMessage.getUrl().toURL()).build();

        } catch (MalformedURLException e) {
            String error = String.format("url is not acceptable, please check url: %s", requestMessage.getUrl());
            throw new HttpExchangeException(error, e);
        }
        return request;
    }

    private String getContentType(Header header) {

        String contentType = null;
        if (header!=null) {
            contentType = header.getValue(HttpHeader.CONTENT_TYPE);
        }

        if (contentType==null) contentType = com.demo.learn.common.header.http.MediaType.APPLICATION_JSON;
        return contentType;

    }


    private Headers buildOkHttpHeaders(Header header) {
        Headers.Builder output = new Headers.Builder();

        header.documentHeader().forEach((key, value) -> {
            output.add(key + ":" + value);
        });

        return output.build();
    }


    public List<HttpEntityConverter<REQ>> getRequestMessageConverters() {
        return requestMessageConverters;
    }

    public void setRequestMessageConverters(List<HttpEntityConverter<REQ>> requestMessageConverters) {
        this.requestMessageConverters = requestMessageConverters;
    }

    public List<HttpEntityConverter<RESP>> getResponseMessageConverters() {
        return responseMessageConverters;
    }

    public void setResponseMessageConverters(List<HttpEntityConverter<RESP>> responseMessageConverters) {
        this.responseMessageConverters = responseMessageConverters;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }


    public Class<RESP> getT() {
        Type sType = getClass().getGenericSuperclass();
        Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
        return (Class<RESP>) (generics[1]);
    }
}
