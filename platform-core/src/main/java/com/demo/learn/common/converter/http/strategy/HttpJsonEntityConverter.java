package com.demo.learn.common.converter.http.strategy;



import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by temper on 2019/3/22,上午12:18.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class HttpJsonEntityConverter<T> extends AbstractHttpEntityConverter<T>{

    private ObjectMapper mapper;


    public HttpJsonEntityConverter() {

        this.mapper = new ObjectMapper();
        supporttedMediaType();
    }

    public HttpJsonEntityConverter(ObjectMapper mapper) {
        this.mapper = mapper;
        supporttedMediaType();
    }

    public HttpJsonEntityConverter(List<okhttp3.MediaType> supportedMediaTypes, ObjectMapper mapper) {
        super(supportedMediaTypes);
        this.mapper = mapper;
        supporttedMediaType();
    }

    private void supporttedMediaType() {
        setSupportedMediaTypes(
                Arrays.asList(
                        MediaType.parse(com.demo.learn.common.header.http.MediaType.APPLICATION_JSON),
                        MediaType.parse(com.demo.learn.common.header.http.MediaType.APPLICATION_JSON_CHARSET)
                )
        );
    }

    @Override
    boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public T read(Class<T> clazz, byte[] input) throws IOException {
        if (input==null) {
            return null;
        }
        return mapper.readValue(input, clazz);
    }

    @Override
    public void write(T t, ByteArrayOutputStream outputMessage) throws IOException {
        outputMessage.write(mapper.writeValueAsBytes(t));
    }
}
