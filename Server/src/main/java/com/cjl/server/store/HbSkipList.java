package com.cjl.server.store;

import java.util.Random;
import java.util.Stack;

public class HbSkipList implements HbCacheList{
    private static final double FACTOR = 0.5d;

    private static final int MAX_LEVEL = 16;

    private int highLevel;

    private CacheNode headNode;

    Random random;

    private LruCache lruCache;

    private Integer size;

    public Integer getSize() {
        return size;
    }

    public CacheNode getHeadNode() {
        return headNode;
    }

    public void setLruCache(LruCache lruCache) {
        this.lruCache = lruCache;
    }

    public LruCache getLruCache() {
        return lruCache;
    }

    public HbSkipList() {
        size = 0;
        random = new Random();
        headNode = new CacheNode(null, null);
        highLevel = 0;
        lruCache = new LruCache(200);
    }

    public CacheNode search(String name) {
        CacheNode team = headNode;
        while (team != null) {
            if (team.getName().equals(name)) {
                lruCache.put(name, team);
                return team;
            } else if (team.next == null)//右侧没有了，只能下降
            {
                team = team.down;
            } else if (compareStr(team.next.getName(), name))//需要下降去寻找
            {
                team = team.down;
            } else //右侧比较小向右
            {
                team = team.next;
            }
        }
        return null;
    }

    public void delete(String name)//删除不需要考虑层数
    {
        CacheNode team = headNode;
        while (team != null) {
            if (team.next == null) {//右侧没有了，说明这一层找到，没有只能下降
                team = team.down;
            } else if (team.next.getName().equals(name)) {   //找到节点，右侧即为待删除节点
                team.next = team.next.next;//删除右侧节点
                team = team.down;//向下继续查找删除
                lruCache.delete(name);
                size--;
            } else if (compareStr(team.next.getName(), name)) {  //右侧已经不可能了，向下
                team = team.down;
            } else { //节点还在右侧
                team = team.next;
            }
        }
    }

    public void add(CacheNode node) {

        String name = node.getName();
        CacheNode findNode = search(name);
        if (findNode != null)//如果存在这个key的节点
        {
            findNode.setData(node.getData());
            return;
        }

        Stack<CacheNode> stack = new Stack<CacheNode>();//存储向下的节点，这些节点可能在右侧插入节点
        CacheNode team = headNode;//查找待插入的节点   找到最底层的哪个节点。
        while (team != null) {//进行查找操作
            if (team.next == null)//右侧没有了，只能下降
            {
                stack.add(team);//将曾经向下的节点记录一下
                team = team.down;
            } else if (compareStr(team.next.getName(), name))//需要下降去寻找
            {
                stack.add(team);//将曾经向下的节点记录一下
                team = team.down;
            } else //向右
            {
                team = team.next;
            }
        }

        int level = 1;//当前层数，从第一层添加(第一层必须添加，先添加再判断)
        CacheNode downNode = null;//保持前驱节点(即down的指向，初始为null)
        while (!stack.isEmpty()) {
            //在该层插入node
            team = stack.pop();//抛出待插入的左侧节点
            CacheNode nodeTeam = new CacheNode(node.getName(), node.getData());//节点需要重新创建
            nodeTeam.down = downNode;//处理竖方向
            downNode = nodeTeam;//标记新的节点下次使用
            if (team.next == null) {//右侧为null 说明插入在末尾
                team.next = nodeTeam;
            }
            //水平方向处理
            else {//右侧还有节点，插入在两者之间
                nodeTeam.next = team.next;
                team.next = nodeTeam;
            }
            size++;
            //考虑是否需要向上
            if (level > MAX_LEVEL)//已经到达最高级的节点啦
                break;
            double num = random.nextDouble();//[0-1]随机数
            if (num > 0.5)//运气不好结束
                break;
            level++;
            if (level > highLevel)//比当前最大高度要高但是依然在允许范围内 需要改变head节点
            {
                highLevel = level;
                //需要创建一个新的节点
                CacheNode highHeadNode = new CacheNode(null, null);
                highHeadNode.down = headNode;
                headNode = highHeadNode;//改变head
                stack.add(headNode);//下次抛出head
            }
        }

    }

    public void printList() {
        CacheNode teamNode = headNode;
        int index = 1;
        CacheNode last = teamNode;
        while (last.down != null) {
            last = last.down;
        }
        while (teamNode != null) {
            CacheNode enumNode = teamNode.next;
            CacheNode enumLast = last.next;
            System.out.printf("%-8s", "head->");
            while (enumLast != null && enumNode != null) {
                if (enumLast.getName().equals(enumNode.getName())) {
                    System.out.printf("%-5s", enumLast.getName() + "->");
                    enumLast = enumLast.next;
                    enumNode = enumNode.next;
                } else {
                    enumLast = enumLast.next;
                    System.out.printf("%-5s", "");
                }

            }
            teamNode = teamNode.down;
            index++;
            System.out.println();
        }
    }

    private boolean compareStr(String big, String small) {
        int idxA = 0, idxB = 0;
        while (idxA < big.length() && idxB < small.length()) {
            if (big.charAt(idxA) > small.charAt(idxB)) {
                return true;
            } else if (big.charAt(idxA) < small.charAt(idxB)) {
                return false;
            } else {
                idxA++;
                idxB++;
            }
        }
        if (idxA < big.length()) {
            return true;
        } else {
            return false;
        }
    }

}
