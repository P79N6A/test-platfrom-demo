package com.demo.learn.common.message;

import com.demo.learn.common.header.Header;

import java.util.Map;
import java.util.Objects;

/**
 * Created by teemper on 2019/3/18, 22:38.
 *
 * @author Zed.
 * github:https://github.com/twentyworld/
 * <p>
 * copy as you like, but with these words.
 * please kindly write to teemper@163.com if anthing.
 * from win.
 */
public abstract class MessageEntity<T> {

    protected T body;

    protected Header headers;

    /**
     * Create a new, empty {@code MessageEntity}.
     */
    protected MessageEntity() {

    }

    /**
     * Create a new {@code MessageEntity} with the given body and no headers.
     * @param body the entity body
     */
    public MessageEntity(T body) {
        this(body, null);
    }

    /**
     * Create a new {@code MessageEntity} with the given headers and no body.
     * @param headers the entity headers
     */
    public MessageEntity(Header headers) {
        this(null, headers);
    }

    /**
     * Create a new {@code MessageEntity} with the given body and headers.
     * @param body the entity body
     * @param headers the entity headers
     */
    public MessageEntity(T body,Header headers) {
        this.body = body;
        if (headers != null){
            this.headers =  headers;
        }
        else this.headers = new Header();
    }

    public void setBody(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }

    public Header getHeaders() {
        return headers;
    }

    public void setHeaders(Header headers) {
        this.headers = headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageEntity)) return false;
        MessageEntity<?> that = (MessageEntity<?>) o;
        return Objects.equals(body, that.body) && Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, headers);
    }
}
