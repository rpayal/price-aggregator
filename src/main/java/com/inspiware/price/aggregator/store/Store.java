package com.inspiware.price.aggregator.store;

import java.util.Set;

public interface Store<K, V> {
    V get(K key);

    Set<V> getAll();

    void addOrUpdatePrice(K key, V value);

    void delete(K key);
}
