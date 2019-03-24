package com.demo.learn.common.converter.http.strategy;


import com.demo.learn.common.converter.http.HttpEntityConverter;
import com.demo.learn.common.header.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;

/**
 * Created by temper on 2019/3/22,上午12:18.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class HttpJsonEntityConverter<T> extends AbstractHttpEntityConverter<T>{

    private ObjectMapper mapper;

    public HttpJsonEntityConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public HttpJsonEntityConverter(List<String> supportedMediaTypes, ObjectMapper mapper) {
        super(supportedMediaTypes);
        this.mapper = mapper;
    }

    @Override
    boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public T read(Class<? extends T> clazz, Object input) throws IOException {
        return mapper.readValue(input.toString(), clazz);
    }

    @Override
    public void write(T t, OutputStream outputMessage) throws IOException {
        outputMessage.write(mapper.writeValueAsBytes(t));
    }
}
