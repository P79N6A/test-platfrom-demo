package com.demo.learn.common.header.http;

import com.demo.learn.common.header.Header;
import com.demo.learn.common.method.HttpMethod;
import com.demo.learn.utils.StringUtils;
import okhttp3.Headers;
import okhttp3.internal.http.HttpHeaders;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by teemper on 2019/3/19, 19:57.
 *
 * @author Zed.
 * github:https://github.com/twentyworld/
 * <p>
 * copy as you like, but with these words.
 * please kindly write to teemper@163.com if anthing.
 * from win.
 */
public class HttpHeader extends Header {
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String ACCEPT = "Accept";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    public static final String ALLOW = "Allow";
    public static final String AUTHORIZATION = "Authorization";

    public void setAccept(List<String> accepts) {
        set(ACCEPT, reduce(accepts));
    }

    public HttpHeader contentLength(long contentLength) {
        set(CONTENT_LENGTH, Long.toString(contentLength));
        return this;
    }

    public HttpHeader contentType(String mediaType) {
            set(CONTENT_TYPE, mediaType);
            return this;

    }

    public HttpHeader acceptCharset(List<Charset> acceptableCharsets) {
        StringBuilder builder = new StringBuilder();
        for (Iterator<Charset> iterator = acceptableCharsets.iterator(); iterator.hasNext();) {
            Charset charset = iterator.next();
            builder.append(charset.name().toLowerCase(Locale.ENGLISH));
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        set(ACCEPT_CHARSET, builder.toString());
        return this;
    }

    public void setAllow(Set<HttpMethod> allowedMethods) {
        set(ALLOW, StringUtils.collectionToCommaDelimitedString(allowedMethods));
    }


    public void set(String headerName,  String headerValue) {
        List<String> headerValues = this.headers.computeIfAbsent(headerName, k -> new LinkedList<>());
        if (!headerValues.contains(headerValue)) headerValues.add(headerValue);
        this.headers.put(headerName, headerValues);
    }


    public void setBasicAuth(String username, String password) {
        setBasicAuth(username, password, null);
    }

    public void setBasicAuth(String username, String password, Charset charset) {
        if (charset == null) {
            charset = StandardCharsets.ISO_8859_1;
        }

        CharsetEncoder encoder = charset.newEncoder();
        if (!encoder.canEncode(username) || !encoder.canEncode(password)) {
            throw new IllegalArgumentException(
                    "Username or password contains characters that cannot be encoded to " + charset.displayName());
        }

        String credentialsString = username + ":" + password;
        byte[] encodedBytes = Base64.getEncoder().encode(credentialsString.getBytes(charset));
        String encodedCredentials = new String(encodedBytes, charset);
        set(AUTHORIZATION, "Basic " + encodedCredentials);
    }

    public HttpHeader parseOkHttpHeaders(Headers okHttpHeaders) {

        setHeaders(headers);
        return this;
    }



}
