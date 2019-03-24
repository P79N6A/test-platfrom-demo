package com.demo.learn.common.converter.http.strategy;

import com.demo.learn.common.converter.http.HttpEntityConverter;
import okhttp3.MediaType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by teemper on 2019/3/23, 21:20.
 *
 * @author Zed.
 * github:https://github.com/twentyworld/
 * <p>
 * copy as you like, but with these words.
 * please kindly write to teemper@163.com if anthing.
 * from win.
 */

/**
 * you should have extends this class for converting all of object.
 * @param <T>
 */
public abstract class AbstractHttpEntityConverter<T> implements HttpEntityConverter<T> {
    protected List<MediaType> supportedMediaTypes = Collections.emptyList();
    protected Type type;


    public AbstractHttpEntityConverter() {
    }

    public AbstractHttpEntityConverter(List<MediaType> supportedMediaTypes) {
        this.supportedMediaTypes = supportedMediaTypes;
    }

    public List<MediaType> getSupportedMediaTypes() {
        return Collections.unmodifiableList(this.supportedMediaTypes);
    }

    abstract boolean supports(Class<?> clazz);

    public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
        this.supportedMediaTypes = new ArrayList<>(supportedMediaTypes);
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return supports(clazz) && canRead(mediaType);
    }


    private boolean canRead(MediaType mediaType) {
        if (mediaType == null) {
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.equals(mediaType)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return supports(clazz) && canWrite(mediaType);
    }

    protected boolean canWrite(MediaType mediaType) {
        if (mediaType == null) {
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.equals(mediaType)) {
                return true;
            }
        }
        return false;
    }

}
