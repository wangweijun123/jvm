package com.wangweijun.jvm.ch13;

import org.junit.Test;

import java.util.Vector;

/**
 * 线程安全的数据结构(集合,比如Vector)并非绝对的安全,在某种特定顺序操作
 * 比如for循坏，这种随机访问,一个线程做删除操作,由可能导致索引index失效
 * 抛出数组越界问题,必须在调用端同时保证同步
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestSecond {
    private static Vector<Integer> vector = new Vector<Integer>();

    @Test
    public void main() {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);// 可能java.lang.ArrayIndexOutOfBoundsException
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        System.out.println((vector.get(i)));
                    }
                }
            });

            removeThread.start();
            printThread.start();

            //不要同时产生过多的线程，否则会导致操作系统假死
            while (Thread.activeCount() > 20);
        }

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}