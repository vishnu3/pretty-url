package com.stylight.prettyurl.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class <p>BidirectionalMap</p>
 * A bidirectional lookup map
 *
 * @param <K> key of the map
 * @param <V> value of the map
 */
public class BidirectionalMap<K, V> extends ConcurrentHashMap<K,V> {
    private static final long serialVersionUID = 1L;

    public Map<V, K> valueToKeyMap = new ConcurrentHashMap<V, K>();

    public K getKey(V value) {
        return valueToKeyMap.get(value);
    }

    public K getKeyOrDefault(V value, K defaultValue) {
        return valueToKeyMap.getOrDefault(value, defaultValue);
    }

    public V remove(Object key) {
        V val = super.remove(key);
        valueToKeyMap.remove(val);
        return val;
    }

    public V put(K key, V value) {
        valueToKeyMap.put(value, key);
        return super.put(key, value);
    }

}
