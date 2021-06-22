package com.zlutil.tools.Share;

import java.io.IOException;

public interface PicService<K, V> {

    String get(K key) throws IOException;

    String set(K key, V value) throws IOException;
}
