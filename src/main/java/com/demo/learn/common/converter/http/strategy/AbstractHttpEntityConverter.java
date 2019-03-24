package com.demo.learn.common.converter.http.strategy;

import com.demo.learn.common.converter.http.HttpEntityConverter;

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
public abstract class AbstractHttpEntityConverter<T> implements HttpEntityConverter<T> {
    protected List<String> supportedMediaTypes = Collections.emptyList();


    public AbstractHttpEntityConverter() {
    }

    public AbstractHttpEntityConverter(List<String> supportedMediaTypes) {
        this.supportedMediaTypes = supportedMediaTypes;
    }

    public List<String> getSupportedMediaTypes() {
        return Collections.unmodifiableList(this.supportedMediaTypes);
    }

    abstract boolean supports(Class<?> clazz);

    public void setSupportedMediaTypes(List<String> supportedMediaTypes) {
        this.supportedMediaTypes = new ArrayList<>(supportedMediaTypes);
    }

    @Override
    public boolean canRead(Class<?> clazz, String mediaType) {
        return supports(clazz) && canRead(mediaType);
    }


    private boolean canRead(String mediaType) {
        if (mediaType == null) {
            return true;
        }
        for (String supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.contains(mediaType)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean canWrite(Class<?> clazz, String mediaType) {
        return supports(clazz) && canWrite(mediaType);
    }

    protected boolean canWrite(String mediaType) {
        if (mediaType == null) {
            return true;
        }
        for (String supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.equals(mediaType)) {
                return true;
            }
        }
        return false;
    }

}
