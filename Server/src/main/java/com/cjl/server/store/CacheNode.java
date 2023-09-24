package com.cjl.server.store;

import lombok.Data;

@Data
public class CacheNode {
    private String name;

    private Object data;

    public CacheNode pre;

    public CacheNode next;

    public CacheNode down;

    //ms
    private Long expire;


    public CacheNode() {
        this.expire = 0L;
    }

    public CacheNode(String name, Object data) {
        this.name = name;
        this.data = data;
        this.expire = 0L;
    }

}
