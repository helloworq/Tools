package com.picserver.server.PicSaveService;

public interface PicService<K, V> {

    String get(K key);

    String set(K key, V value);
}
