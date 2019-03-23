package com.demo.learn.common.converter;


import com.demo.learn.common.header.http.MediaType;
import okio.BufferedSink;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by temper on 2019/3/22,上午12:18.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class JsonMessageConverter<T> implements MessageConverter<T> {


    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public String read(Class<? extends T> clazz, ) throws IOException {
        return null;
    }

    @Override
    public void write(T s, MediaType contentType, OutputStream outputMessage) throws IOException {
        outputMessage.write(s.getBytes());
    }
}
