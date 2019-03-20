package com.demo.learn.common.client.http;


import com.demo.learn.common.client.ClientRequest;
import com.demo.learn.common.header.Header;
import com.demo.learn.common.header.http.HttpHeader;
import com.demo.learn.common.message.MessageEntity;

import com.demo.learn.common.method.HttpMethod;

import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by teemper on 2019/3/19, 20:09.
 *
 * @author Zed.
 * github:https://github.com/twentyworld/
 * <p>
 * copy as you like, but with these words.
 * please kindly write to teemper@163.com if anthing.
 * from win.
 */
public class HttpClientRequest<T> extends MessageEntity<T> implements ClientRequest {

    private HttpMethod method;

    private URI url;

    private Type type;

    /**
     * Create a new {@code HttpClientRequest} with the given body and headers.
     *
     * @param body    the entity body
     * @param headers the entity headers
     * @param method  the entity method
     * @param url     the entity url
     * @param type    the body type
     */
    public HttpClientRequest(T body, Header headers, HttpMethod method, URI url, Type type) {
        super(body, headers);
        this.method = method;
        this.url = url;
        this.type = type;
    }

    /**
     * @param headers the entity headers
     * @param method  the entity method
     * @param url     the entity url
     */
    public HttpClientRequest(Header headers, HttpMethod method, URI url) {
        super(null, headers);
        this.method = method;
        this.url = url;
        this.type = type;
    }

    /**
     * Create a new {@code HttpClientRequest} with the given body and headers.
     *
     * @param body    the entity body
     * @param headers the entity headers
     * @param method  the entity method
     * @param url     the entity url
     */
    public HttpClientRequest(T body, Header headers, HttpMethod method, URI url) {
        super(body, headers);
        this.method = method;
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    // Static builder methods

    /**
     * Create a builder with the given method and url.
     * @param method the HTTP method (GET, POST, etc)
     * @param url the URL
     * @return the created builder
     */
    public static BodyBuilder method(HttpMethod method, URI url) {
        return new DefaultBodyBuilder(method, url);
    }

    /**
     * Create an HTTP GET builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static HeadersBuilder<?> get(URI url) {
        return method(HttpMethod.GET, url);
    }

    /**
     * Create an HTTP HEAD builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static HeadersBuilder<?> head(URI url) {
        return method(HttpMethod.HEAD, url);
    }

    /**
     * Create an HTTP POST builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static BodyBuilder post(URI url) {
        return method(HttpMethod.POST, url);
    }

    /**
     * Create an HTTP PUT builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static BodyBuilder put(URI url) {
        return method(HttpMethod.PUT, url);
    }

    /**
     * Create an HTTP PATCH builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static BodyBuilder patch(URI url) {
        return method(HttpMethod.PATCH, url);
    }

    /**
     * Create an HTTP DELETE builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static HeadersBuilder<?> delete(URI url) {
        return method(HttpMethod.DELETE, url);
    }

    /**
     * Creates an HTTP OPTIONS builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static HeadersBuilder<?> options(URI url) {
        return method(HttpMethod.OPTIONS, url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpClientRequest)) return false;
        if (!super.equals(o)) return false;
        HttpClientRequest that = (HttpClientRequest) o;
        return method == that.method && Objects.equals(url, that.url) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), method, url, type);
    }

    @Override
    public URI getURI() {
        return url;
    }

    /**
     * Defines a builder that adds headers to the request entity.
     * @param <B> the builder subclass
     */
    public interface HeadersBuilder<B extends HeadersBuilder<B>> {

        B withHeader(String headerName, String... headerValues);

        B accept(String... acceptableMediaTypes);

        B acceptCharset(Charset... acceptableCharsets);

        HttpClientRequest<Void> build();
    }


    /**
     * Defines a builder that adds a body to the response entity.
     */
    public interface BodyBuilder extends HeadersBuilder<BodyBuilder> {

        BodyBuilder contentLength(long contentLength);

        <T> HttpClientRequest<T> body(T body);

        BodyBuilder contentType(String contentType);

        <T> HttpClientRequest<T> body(T body, Type type);
    }


    private static class DefaultBodyBuilder implements BodyBuilder {

        private final HttpMethod method;

        private final URI url;

        private final HttpHeader headers = new HttpHeader();

        public DefaultBodyBuilder(HttpMethod method, URI url) {
            this.method = method;
            this.url = url;
        }

        @Override
        public BodyBuilder withHeader(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.addHeader(headerName, headerValue);
            }
            return this;
        }

        @Override
        public BodyBuilder contentType(String contentType) {
            this.headers.setContentType(contentType);
            return this;
        }

        @Override
        public BodyBuilder accept(String... acceptableMediaTypes) {
            this.headers.setAccept(Arrays.asList(acceptableMediaTypes));
            return this;
        }

        @Override
        public BodyBuilder acceptCharset(Charset... acceptableCharsets) {
            this.headers.setAcceptCharset(Arrays.asList(acceptableCharsets));
            return this;
        }


        @Override
        public BodyBuilder contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }

        @Override
        public HttpClientRequest<Void> build() {
            return new HttpClientRequest<>(this.headers, this.method, this.url);
        }

        @Override
        public <T> HttpClientRequest<T> body(T body) {
            return new HttpClientRequest<>(body, this.headers, this.method, this.url);
        }


        @Override
        public <T> HttpClientRequest<T> body(T body, Type type) {
            return new HttpClientRequest<>(body, this.headers, this.method, this.url, type);
        }
    }

}
