package com.demo.learn.common.status.http;

import com.demo.learn.common.status.Status;

import java.util.Objects;

/**
 * Created by teemper on 2019/3/19, 21:41.
 *
 * @author Zed.
 * github:https://github.com/twentyworld/
 * <p>
 * copy as you like, but with these words.
 * please kindly write to teemper@163.com if anthing.
 * from win.
 */
public enum HttpStatus implements Status<Integer, String> {

    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported")
    ;



    private Integer key;
    private String phrase;

    HttpStatus(Integer key, String phrase) {
        this.key = key;
        this.phrase = phrase;
    }



    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }


    @Override
    public Integer getStatusKey() {
        return key;
    }

    @Override
    public String getPhrase() {
        return phrase;
    }


    @Override
    public String toString() {
        return "HttpStatus{" + "key=" + key + ", phrase=" + phrase + '}';
    }

    public static HttpStatus resolve(int statusCode) {
        for (HttpStatus status : values()) {
            if (status.key == statusCode) {
                return status;
            }
        }
        return null;
    }

}
