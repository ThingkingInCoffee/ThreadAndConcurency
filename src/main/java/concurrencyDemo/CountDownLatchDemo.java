package concurrencyDemo;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 门闩
 * 可以和锁混合使用，或者替代锁的功能
 * 在门闩未完全放开之前等待，当梦栓完全开放时执行
 * 避免锁的效率低下问题
 */
public class CountDownLatchDemo {

    //设置门闩 指定个数
    CountDownLatch countDownLatch = new CountDownLatch(5);

    void m1 () {
        try {
            countDownLatch.await(); //等待门闩开放   注意区分线程的wait方法
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1 executed");
    }

    void m2 () {
        for (int i = 0; i < 10; i++) {
            if (countDownLatch.getCount() != 0) {
                System.out.println("latch count: "+ countDownLatch.getCount());
                countDownLatch.countDown(); //减少锁
                System.out.println("latch down");
            }
            System.out.println("m2 executed");
        }
    }

    public static void main(String[] args) {
        final CountDownLatchDemo c = new CountDownLatchDemo();
        new Thread(new Runnable() {
            public void run() {
                c.m1();
            }
        },"thread-1").start();

        new Thread(new Runnable() {
            public void run() {
                c.m2();
            }
        },"thread-2").start();
    }

}
