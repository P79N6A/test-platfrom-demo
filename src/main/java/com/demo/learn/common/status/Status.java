package com.demo.learn.common.status;

/**
 * Created by teemper on 2019/3/19, 21:36.
 *
 * @author Zed.
 * github:https://github.com/twentyworld/
 * <p>
 * copy as you like, but with these words.
 * please kindly write to teemper@163.com if anthing.
 * from win.
 */
public interface Status<K, V> {

    public K getStatusKey();

    public V getPhrase();
}
