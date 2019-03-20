package com.demo.learn.common.header;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by teemper on 2019/3/18, 22:44.
 *
 * @author Zed.
 * github:https://github.com/twentyworld/
 * <p>
 * copy as you like, but with these words.
 * please kindly write to teemper@163.com if anthing.
 * from win.
 */
public class Header {

    protected Map<String, List<String>> headers;

    public Header(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Header() {
        this.headers = new HashMap<>();
    }

    public Header addHeader(String key, String value) {
        List<String> headerValues = this.headers.computeIfAbsent(key, k -> new LinkedList<>());
        headerValues.add(value);
        return this;
    }

    public Header removeHeader(String key) {
        headers.remove(key);
        return this;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public void putAll(Header header) {
        headers.putAll(header.getHeaders());
    }

}
