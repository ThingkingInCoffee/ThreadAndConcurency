package concurrencyDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile关键字
 * 只能保证可见性，不能保证原子性，它不是加锁，只是内存数据可见
 * 加锁 对比  atomicDemos.AtomicIntegerDemo
 */
public class VolatileDemo01 {

    volatile int count = 0;

    void m1() {
        for (int i = 0; i < 100000; i++) {
            count++;
        }
    }
    synchronized void m2() {
        for (int i = 0; i < 100000 ; i++) {
            count++;
        }
    }


    public static void main(String[] args) {
        final VolatileDemo01 demo01 = new VolatileDemo01();
        List<Thread> threadList = new ArrayList<Thread>();
        for (int i = 0; i < 5; i++) {
            threadList.add(new Thread(new Runnable() {
                public void run() {
                    demo01.m1();
                    //调用 m1  和   m2 效果差异
                }
            }));
        }
        for (Thread thread : threadList) {
            thread.start();
        }
        //加入join保证所有线程执行后打印总数
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(demo01.count);
    }


}
