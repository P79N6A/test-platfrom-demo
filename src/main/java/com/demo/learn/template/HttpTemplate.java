package com.demo.learn.template;

import com.demo.learn.HttpExchange;

import com.demo.learn.common.client.http.HttpClientRequest;
import com.demo.learn.common.client.http.HttpClientResponse;
import com.demo.learn.common.converter.http.strategy.HttpJsonEntityConverter;
import com.demo.learn.common.converter.http.HttpEntityConverter;
import com.demo.learn.common.header.Header;
import com.demo.learn.common.header.http.HttpHeader;
import com.demo.learn.exception.HttpExchangeException;
import okhttp3.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class HttpTemplate<REQ, RESP> implements HttpExchange<REQ, RESP> {
    private List<HttpEntityConverter<REQ>> requestMessageConverters;
    private List<HttpEntityConverter<RESP>> responseMessageConverters;
    private OkHttpClient okHttpClient = new OkHttpClient();

    public HttpTemplate() {
        requestMessageConverters = new ArrayList<>();
        requestMessageConverters.add(new HttpJsonEntityConverter<REQ>());

        responseMessageConverters = new ArrayList<>();

    }


    @Override
    public HttpClientResponse<RESP> exchange(HttpClientRequest<REQ> requestMessage) {
        if (requestMessage==null) throw new IllegalArgumentException("request should not be null.");

        Request request = buildRequest(requestMessage);
        try(Response response = okHttpClient.newCall(request).execute()) {
            //TODO, using message converter to convert the message and it can be easily translate into RESP type.
            return buildResponse(response);
        }catch (IOException e) {
            throw new HttpExchangeException("http request error", e);
        }
    }

    private HttpClientResponse<RESP> buildResponse(Response response) {
        return null;
    }


    private Request buildRequest(HttpClientRequest<REQ> requestMessage) {
        MediaType mediaType = MediaType.parse(getContentType(requestMessage.getHeaders()));

        for (HttpEntityConverter<String> messageConverters)

        RequestBody body = RequestBody.create(mediaType, requestMessage.getBody().toString());

        Request request;
        try {
            request = new Request.Builder()
                    .headers(buildOkHttpHeaders(requestMessage.getHeaders()))
                    .method(requestMessage.getMethod().toString(), body)
                    .url(requestMessage.getUrl().toURL())
                    .build();

        } catch (MalformedURLException e) {
            String error= String.format("url is not acceptable, please check url: %s", requestMessage.getUrl());
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
            output.add(key);
            output.add(value);
        });

        return output.build();
    }


}
