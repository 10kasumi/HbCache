package com.cjl.server.store;

public class HbLinkedList implements HbCacheList{
    private CacheNode head;

    private LruCache lruCache;

    private Integer size;

    public CacheNode getHead() {
        return head;
    }

    public Integer getSize() {
        return size;
    }

    public LruCache getLruCache() {
        return lruCache;
    }

    public HbLinkedList() {
        head = new CacheNode();
        lruCache = new LruCache(200);
        size = 0;
    }

    public void add(CacheNode node){
        CacheNode cur = head;
        while(true){
            if(cur.next == null){
                break;
            }
            if(node.getName().equals(cur.getName())){
                cur.setData(node.getData());
                return;
            }
            cur = cur.next;
        }
        cur.next = node;
        node.pre = cur;
        size++;
    }


    public void delete(String name){
        if(head.next == null){
            return;
        }
        CacheNode temp = head.next;
        while(true){
            if(name.equals(temp.getName())){
                temp.pre.next = temp.next;
                if(temp.next != null){
                    temp.next.pre = temp.pre;
                }
                size--;
            }
            if(temp.next == null){
                return;
            }
            temp = temp.next;
        }
    }

    public CacheNode search(String name){
        CacheNode temp = head;
        while(temp != null){
            if(name.equals(temp.getName())){
                if(temp.getExpire() != 0){
                    long curTime = System.currentTimeMillis();
                    if(curTime > temp.getExpire()){
                        delete(name);
                        return null;
                    }
                }
                return temp;
            } else{
                temp = temp.next;
            }
        }
        return null;
    }
}
