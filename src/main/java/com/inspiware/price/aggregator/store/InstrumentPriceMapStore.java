package com.inspiware.price.aggregator.store;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InstrumentPriceMapStore<K, V> implements Store<K, V> {

    private final String name;

    private ConcurrentMap<K, V> internalStore;
    private K instrumentPriceKey;

    public InstrumentPriceMapStore(String name) {
        this.name = name;
        this.internalStore = new ConcurrentHashMap<>(256);
    }

    public final String getName() {
        return this.name;
    }

    @Override
    public V get(K key) {
        return this.internalStore.get(key);
    }

    @Override
    public Set<V> getAll() {
        return new HashSet<V>(internalStore.values());
    }

    @Override
    public void addOrUpdatePrice(K key, V entity) {
        internalStore.put(key, entity);
    }

    @Override
    public void delete(K key) {
        internalStore.remove(key);
    }
}
