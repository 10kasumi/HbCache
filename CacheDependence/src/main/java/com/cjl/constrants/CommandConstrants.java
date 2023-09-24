package com.cjl.constrants;

import java.util.ArrayList;
import java.util.List;

public class CommandConstrants {
    public static List<String> list = new ArrayList<>();

    static {
        list.add("help");
        list.add("delete");
        list.add("exist");
        list.add("expire");
        list.add("ttl");

        list.add("set");
        list.add("get");
        list.add("mset");
        list.add("mget");
        list.add("incr");
        list.add("incrby");
        list.add("setnx");
        list.add("setex");

        list.add("hset");
        list.add("hget");
        list.add("hmset");
        list.add("hmget");
        list.add("hgetall");
        list.add("hkeys");
        list.add("hvals");
        list.add("hincrby");
        list.add("hsetnx");

        list.add("lpush");
        list.add("lpop");
        list.add("rpush");
        list.add("rpop");
        list.add("lrange");
        list.add("blpop");
        list.add("brpop");

        list.add("sadd");
        list.add("srem");
        list.add("scard");
        list.add("sismember");
        list.add("smembers");
    }
}
