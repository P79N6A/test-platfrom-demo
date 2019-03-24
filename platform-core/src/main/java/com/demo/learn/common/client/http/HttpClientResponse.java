package com.demo.learn.common.client.http;

import com.demo.learn.common.client.ClientResponse;
import com.demo.learn.common.header.Header;
import com.demo.learn.common.header.http.HttpHeader;
import com.demo.learn.common.message.MessageEntity;
import com.demo.learn.common.method.HttpMethod;
import com.demo.learn.common.status.Status;
import com.demo.learn.common.status.http.HttpStatus;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;


/**
 * Extension of {@link MessageEntity} that adds a {@link HttpStatus} status code.
 *
 * by using a builder accessible via static methods:
 * <pre class="code">
 * &#64;RequestMapping("/handle")
 * public HttpClientResponse&lt;String&gt; handle() {
 *   URI location = ...;
 *   return HttpClientResponse.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
 * }
 * </pre>
 *
 */
public class HttpClientResponse<T> extends MessageEntity<T> implements ClientResponse<T> {

    private final HttpStatus status;

    public HttpClientResponse(HttpStatus status) {
        this(null, null, status);
    }

    public HttpClientResponse(T body, HttpStatus status) {
        this(body, null, status);
    }

    public HttpClientResponse(Header headers, HttpStatus status) {
        this(null, headers, status);
    }

    public HttpClientResponse(T body, Header headers, HttpStatus status) {
        super(body, headers);
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpClientResponse)) return false;
        if (!super.equals(o)) return false;
        HttpClientResponse<?> httpClientResponse = (HttpClientResponse<?>) o;
        return status == httpClientResponse.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }

    @Override
    public String toString() {
        return "HttpClientResponse{" + "status=" + status + ", body=" + body + ", headers=" + headers + '}';
    }

    public static BodyBuilder status(HttpStatus status) {
        return new DefaultBuilder(status);
    }


    public static <T> HttpClientResponse of(Optional<T> body) {
        return body.map(HttpClientResponse::ok).orElse(notFound().build());
    }



    public static BodyBuilder ok() {
        return status(HttpStatus.OK);
    }


    public static <T> HttpClientResponse<T> ok(T body) {
        BodyBuilder builder = ok();
        return builder.body(body);
    }



    public static BodyBuilder accepted() {
        return status(HttpStatus.ACCEPTED);
    }


    public static HeadersBuilder noContent() {
        return status(HttpStatus.NO_CONTENT);
    }


    public static BodyBuilder badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }

    public static HeadersBuilder notFound() {
        return status(HttpStatus.NOT_FOUND);
    }

    public static BodyBuilder unprocessableEntity() {
        return status(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    public Status<?, ?> getStatus() {
        return status;
    }


    public interface HeadersBuilder<B extends HeadersBuilder<B>> {

        B header(String headerName, String... headerValues);

        B headers(HttpHeader headers);

        B allow(HttpMethod... allowedMethods);

        <T> HttpClientResponse<T> build();
    }

    public interface BodyBuilder extends HeadersBuilder<BodyBuilder> {

        BodyBuilder contentLength(long contentLength);

        BodyBuilder contentType(String contentType);

        <T> HttpClientResponse<T> body(T body);
    }


     static class DefaultBuilder implements BodyBuilder {

        private final HttpStatus statusCode;

        private final HttpHeader headers = new HttpHeader();

        public DefaultBuilder(HttpStatus statusCode) {
            this.statusCode = statusCode;
        }

        @Override
        public BodyBuilder header(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.addHeader(headerName, headerValue);
            }
            return this;
        }

        @Override
        public BodyBuilder headers(HttpHeader headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        @Override
        public BodyBuilder allow(HttpMethod... allowedMethods) {
            this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
            return this;
        }

        @Override
        public BodyBuilder contentLength(long contentLength) {
            this.headers.contentLength(contentLength);
            return this;
        }

        @Override
        public BodyBuilder contentType(String contentType) {
            this.headers.contentType(contentType);
            return this;
        }


        @Override
        public <T> HttpClientResponse<T> build() {
            return body(null);
        }

        @Override
        public <T> HttpClientResponse<T> body(T body) {
            return new HttpClientResponse<>(body, this.headers, this.statusCode);
        }
    }

}
