package com.demo.learn.common.converter;


import com.demo.learn.common.header.http.MediaType;
import okio.BufferedSink;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by temper on 2019/3/22,上午12:09.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public interface MessageConverter<T> {
    boolean canRead(Class<?> clazz, MediaType mediaType);

    boolean canWrite(Class<?> clazz, MediaType mediaType);

    T read(Class<? extends T> clazz, InputStream inputMessage) throws IOException;

    void write(T t, MediaType contentType, OutputStream outputMessage) throws IOException;

}
