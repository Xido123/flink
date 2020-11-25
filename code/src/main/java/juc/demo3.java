package juc;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class demo3 {
    public static void main(String[] args) {
        ArrayList c;
        /*
        *
        * ArrayList list
        * 底层实际上new了一个数组类型
        * 这个数组的类型是object 自律方能自由
        * list。add可以
        * 连续的内存空间 8
        *
        * 默认初始值10的连续内存空间
        * 扩容原值的一半  第一次扩容扩到十五
        *
        * 第二次扩容从十五扩到多少，22
        *
        *错误类型 java.util.concurrentModificationException
        *
        *
        *
        * */

        List<String> list = new ArrayList<>();

        Lock l = new ReentrantLock();
        for (int i = 1; i < 4; i++) {

            new Thread(() -> {
                l.lock();
                list.add(UUID.randomUUID().toString().substring(0 , 8));
                System.out.println(list);
                l.unlock();
            } , String.valueOf(i)).start();

        }
    }
}
