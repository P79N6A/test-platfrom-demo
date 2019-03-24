package com.demo.learn.common.client;

import com.demo.learn.common.status.Status;

/**
 * Created by teemper on 2019/3/19, 20:02.
 *
 * @author Zed.
 * github:https://github.com/twentyworld/
 * <p>
 * copy as you like, but with these words.
 * please kindly write to teemper@163.com if anthing.
 * from win.
 */
public interface ClientResponse<T> {
    T getBody();
    Status<?,?> getStatus();
}
