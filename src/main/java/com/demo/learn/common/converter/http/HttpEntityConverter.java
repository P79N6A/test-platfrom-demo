package com.demo.learn.common.converter.http;


import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by temper on 2019/3/22,上午12:09.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public interface HttpEntityConverter<T> {
    boolean canRead(Class<?> clazz, String mediaType);

    boolean canWrite(Class<?> clazz, String mediaType);

    T read(Class<? extends T> clazz, Object input) throws IOException;

    void write(T t, OutputStream outputMessage) throws IOException;

}
