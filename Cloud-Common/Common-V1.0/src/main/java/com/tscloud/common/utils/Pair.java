package com.tscloud.common.utils;

/**
 *
 * @author he.liu
 * @date 2017/7/27
 */
public class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public K _1(){
        return key;
    }

    public V getValue() {
        return value;
    }

    public V _2(){
        return value;
    }

    @Override
    public String toString() {
        return key.toString() + ":" + value.toString();
    }
}
