package com.demo.learn.common.header.http;

import com.demo.learn.common.header.Header;
import com.demo.learn.common.method.HttpMethod;
import com.demo.learn.utils.StringUtils;

import java.nio.charset.Charset;
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

    public void setAccept(List<String> accepts) {
        set(ACCEPT, reduce(accepts));
    }

    public void setContentLength(long contentLength) {
        set(CONTENT_LENGTH, Long.toString(contentLength));
    }

    public void setContentType(String mediaType) {
            set(CONTENT_TYPE, mediaType);
    }

    public void setAcceptCharset(List<Charset> acceptableCharsets) {
        StringBuilder builder = new StringBuilder();
        for (Iterator<Charset> iterator = acceptableCharsets.iterator(); iterator.hasNext();) {
            Charset charset = iterator.next();
            builder.append(charset.name().toLowerCase(Locale.ENGLISH));
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        set(ACCEPT_CHARSET, builder.toString());
    }

    public void setAllow(Set<HttpMethod> allowedMethods) {
        set(ALLOW, StringUtils.collectionToCommaDelimitedString(allowedMethods));
    }


    public void set(String headerName,  String headerValue) {
        List<String> headerValues = new LinkedList<>();
        headerValues.add(headerValue);
        this.headers.put(headerName, headerValues);
    }

    private static String reduce(Collection<String> collection) {
        StringBuilder builder = new StringBuilder();
        for (Iterator<String> iterator = collection.iterator(); iterator.hasNext();) {
            String type = iterator.next();
            builder.append(type);
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }


}
