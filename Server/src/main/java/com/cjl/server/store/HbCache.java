package com.cjl.server.store;

public class HbCache {
    private static int size;

    private static final int TRANS_SIZE = 500;

    private static HbLinkedList linkedList = new HbLinkedList();

    private static HbSkipList skipList = new HbSkipList();

    public static HbCacheList hbCacheList;

    static {
        hbCacheList = linkedList;
        size = linkedList.getSize() - 1;
    }

    public static void add(CacheNode node) {
        hbCacheList.add(node);
        if (hbCacheList instanceof HbLinkedList) {
            size = ((HbLinkedList) hbCacheList).getSize();
            if (size >= TRANS_SIZE) {
                transLinkedListToSkipList();
                hbCacheList = skipList;
            }
        }
    }

    public static void delete(String name) {
        hbCacheList.delete(name);
        if(hbCacheList instanceof HbLinkedList){
            size = ((HbLinkedList) hbCacheList).getSize() - 1;
        } else{
            size = ((HbSkipList) hbCacheList).getSize() - 1;
        }
    }

    public static CacheNode search(String name) {
        CacheNode cacheNode = hbCacheList.search(name);
        return cacheNode;
    }

    public static void transLinkedListToSkipList() {
        CacheNode linkedListHead = linkedList.getHead();
        while (linkedListHead.next != null) {
            skipList.add(linkedListHead.next);
            linkedListHead = linkedListHead.next;
        }
        skipList.setLruCache(linkedList.getLruCache());
    }
}
