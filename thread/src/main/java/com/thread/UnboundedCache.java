package com.thread;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用 WeakHashMap 存储键值对。WeakHashMap 中存储的对象是弱引用，JVM GC 时会自动清除没有被引用的弱引用对象。
 */
public class UnboundedCache<K, V> {
    
    private Map<K, V> weakHashMap = new WeakHashMap<>();
    
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    
    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            weakHashMap.put(key, value);
            String log = String.format("%s put data %s:%s", Thread.currentThread().getName(), key, value);
            System.out.println(log);
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public V get(K key) {
        lock.readLock().lock();
        V value;
        try {
            value =  weakHashMap.get(key);
            String log = String.format("%s get data %s:%s", Thread.currentThread().getName(), key, value);
            System.out.println(log);
        } finally {
            lock.readLock().unlock();
        }
        return value;
    }
    
    public void clear() {
        lock.writeLock().lock();
        try {
            weakHashMap.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public void remove(K key) {
        lock.writeLock().lock();
        try {
            weakHashMap.remove(key);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
