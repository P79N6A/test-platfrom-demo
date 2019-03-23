package com.demo.learn.template;

import com.demo.learn.HttpExchange;
import com.demo.learn.common.client.ClientRequest;
import com.demo.learn.common.client.ClientResponse;
import com.demo.learn.common.client.http.HttpClientRequest;
import com.demo.learn.common.client.http.HttpClientResponse;
import com.demo.learn.common.converter.JsonMessageConverter;
import com.demo.learn.common.converter.MessageConverter;
import com.demo.learn.common.header.Header;
import com.demo.learn.common.header.http.HttpHeader;
import com.demo.learn.exception.HttpExchangeException;
import okhttp3.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

public class HttpTemplate<REQ, RESP> implements HttpExchange<REQ, RESP> {
    MessageConverter<String> respMessageConverter = new JsonMessageConverter();
    OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public HttpClientResponse<RESP> exchange(HttpClientRequest<REQ> requestMessage) {
        if (requestMessage==null) throw new IllegalArgumentException("request should not be null.");

        MediaType mediaType = MediaType.parse(getContentType(requestMessage.getHeaders()));

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

        try(Response response = okHttpClient.newCall(request).execute()) {
            //TODO, using message converter to convert the message and it can be easily translate into RESP type.

            return  HttpClientResponse.ok(response.body()).;


        }catch (IOException e) {
            throw new HttpExchangeException("http request error", e);
        }
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
