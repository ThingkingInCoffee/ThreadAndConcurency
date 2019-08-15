package atomicDemos;

import concurrencyDemo.VolatileDemo01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger 保证原子性
 * 原子类的单个方法是原子性的    底层monitor 而非synchronized
 *
 * 相当于加锁  对比 concurrencyDemo.VolatileDemo01 测试类
 */
public class AtomicIntegerDemo {

    AtomicInteger count = new AtomicInteger(0);

    void m1() {
        for (int i = 0; i < 100000; i++) {
            count.getAndIncrement();
        }
    }

    public static void main(String[] args) {
        final AtomicIntegerDemo demo01 = new AtomicIntegerDemo();
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
