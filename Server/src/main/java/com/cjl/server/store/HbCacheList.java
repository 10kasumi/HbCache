package com.cjl.server.store;

public interface HbCacheList {

    void add(CacheNode node);

    void delete(String name);

    CacheNode search(String name);
}
