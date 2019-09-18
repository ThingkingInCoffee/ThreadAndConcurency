package concurrentContainerDemos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class ConcurrentListDemo {


    public static void main(String[] args) {
        final List<String> list = new ArrayList<>(); //
//        final List<String> list = new Vector<>(); //40
//        final List<String> list = new CopyOnWriteArrayList<>();  //3731

        final Random r = new Random();
        Thread[] threads = new Thread[100];
        final CountDownLatch latch = new CountDownLatch(threads.length);

        long begin = System.currentTimeMillis();
        //创建线程
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        try {
                            list.add("value" + r.nextInt(100000)); //ArrayList 因线程不安全，再扩容时，会出现异常
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("duo le " + j +" size "+list.size()+" "+Thread.currentThread().getName());
                        } finally {
                            latch.countDown();
                        }
                    }
                    latch.countDown();
                    System.out.println("执行了" + Thread.currentThread().getName());
                }
            },"Thread=="+i);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        try {
            System.out.println("await");
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("time is ====" + (end - begin));
        System.out.println(list.size());


    }




}
