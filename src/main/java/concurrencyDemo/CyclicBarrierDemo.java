package concurrencyDemo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 字面意义：循环屏障
 * 让一组线程到达一个屏障（也可以见同步点）时，被阻塞，直到最后一个线程到达屏障时，
 * 屏障才会开门，所有被屏障拦截的线程才会继续干活，线程进入屏障通过 await()方法
 *
 * 人到齐，才开会
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(7,() -> {
            System.out.println("人到齐了，开始开会");
        });

        for (int i = 0; i < 7; i++) {
           new Thread(() -> {
               System.out.println(Thread.currentThread().getName()+"号人员到位");
               try {
                   barrier.await();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               } catch (BrokenBarrierException e) {
                   e.printStackTrace();
               }
           },"thread==="+i).start();
        }
    }

}
