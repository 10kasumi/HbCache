package com.cjl.server.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LruCache {
    public final int capacity;

    private CacheNode dummy;

    private Map<String, CacheNode> cache;

    public LruCache(int capacity) {
        this.capacity = capacity;
        dummy = new CacheNode();
        cache = new ConcurrentHashMap<>();
    }

    public Object get(String name){
        CacheNode cacheNode = cache.get(name);
        if(cacheNode == null) return null;
        remove(cacheNode);
        add(cacheNode);
        return cacheNode.getData();
    }

    public void put(String name, Object data){
        CacheNode node = cache.get(name);
        if(node == null){
            if(cache.size() >= capacity){
                cache.remove(dummy.next.getName());
                remove(dummy.next);
            }
            node = new CacheNode(name, data);
            cache.put(name, node);
            add(node);
        } else{
            cache.remove(node.getName());
            remove(node);
            node = new CacheNode(name, data);
            cache.put(name, node);
            add(node);
        }
    }

    public void delete(String name){
        CacheNode node = cache.get(name);
        if(node == null){
            return;
        } else{
            cache.remove(node.getName());
            remove(node);
        }
    }

    private void add(CacheNode node){
        dummy.pre.next = node;
        node.pre = dummy.pre;
        node.next = dummy;
        dummy.pre = node;
    }

    private void remove(CacheNode node){
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }
}
