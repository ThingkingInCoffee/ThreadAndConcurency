package concurrencyDemo;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 字面意义： 信号标、信号量
 * 主要用于 两个 目的：
 *      1、用于多个共享资源的互斥使用
 *      2、用于并发线程数的控制
 *
 *      不同于 CountDownLatch的只减 和cyclicBarrier 的只增， semaphore随着使用减少资源，用完回放增加资源
 *
 * 例如：停车位有5个，有8台车
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        //设置⑤台车的资源
        Semaphore semaphore = new Semaphore(5);

        //设置把台车争抢5个车位资源
        for (int i = 0; i < 8; i++) {
            new Thread( () -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"-申请车位使用");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(9));
                    System.out.println(Thread.currentThread().getName()+"-停车之后离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            },"car--"+i).start();
        }
    }

}
