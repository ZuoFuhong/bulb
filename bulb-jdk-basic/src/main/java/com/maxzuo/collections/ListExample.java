package com.maxzuo.collections;

import org.junit.Test;

import java.util.*;

/**
 * 集合框架-List
 * <p>
 * Created by zfh on 2019/10/19
 */
public class ListExample {

    /**
     * 基于数组实现，随机访问数组元素效率高，时间复杂度O(1)；删除数据开销很大，需要重排数组中所有的数据。根据最坏打算，时间复杂度是O(n)。
     * <pre>
     *   1.ArrayList 实现了 RandomAccess 接口，实现了这个接口的 List，那么使用for循环的方式获取数据会优于用迭代器获取数据。
     *   2.扩容策略：int newCapacity = oldCapacity + (oldCapacity >> 1);  扩大一半。
     * </pre>
     */
    @Test
    public void testArrayList () {
        ArrayList<String> list = new ArrayList<>(10);
        list.add("one");
        list.add("two");
        list.set(1, "three");
        list.add(2, "four");
        list.remove(0);

        System.out.println(list.get(0));

        list.clear();
    }

    /**
     * LinkedList中插入或删除的时间复杂度仅为O(1)，不支持高效的随机元素访问。
     */
    @Test
    public void testLinkedList () {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("hello");
        linkedList.addFirst("first");
        linkedList.addLast("last");

        linkedList.poll();
        linkedList.pop();

        System.out.println(linkedList.get(0));
    }

    /**
     * 迭代器（iterator）有时又称游标（cursor）是程序设计的软件设计模式，可在容器（container，例如链表或阵列）上遍访的接口，
     * 设计人员无需关心容器的内容。
     * <pre>
     *   Java要求集合必须实现Iterable接口，才能使用for-each语法糖遍历该集合的实例。
     * </pre>
     */
    @Test
    public void testIterator() {
        ArrayList<String> list = new ArrayList<>(10);
        list.add("one");
        list.add("two");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            System.out.println(item);
            if (item.equals("one")) {
                iterator.remove();
            }
        }
    }
}
