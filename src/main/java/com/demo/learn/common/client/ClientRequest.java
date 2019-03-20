package com.demo.learn.common.client;

import com.demo.learn.common.header.Header;

import java.net.URI;

/**
 * Created by teemper on 2019/3/19, 20:01.
 *
 * @author Zed.
 * github:https://github.com/twentyworld/
 * <p>
 * copy as you like, but with these words.
 * please kindly write to teemper@163.com if anthing.
 * from win.
 */
public interface ClientRequest<T> {
    T getBody();

    URI getURI();

    Header getHeaders();
}
