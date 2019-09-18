package concurrentContainerDemos;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

public class ConcurrentMapDemo {

    public static void main(String[] args) {
//        final Map<String,String> map = new Hashtable<String, String>();  //467
        final Map<String,String> map = new ConcurrentHashMap<String, String>(); //350
//        final Map<String,String> map = new ConcurrentSkipListMap<String, String>(); //508

        final Random r = new Random();
        Thread[] threads = new Thread[100];
        final CountDownLatch latch = new CountDownLatch(threads.length);

        long begin = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        map.put("key"+r.nextInt(100000),"value"+r.nextInt(100000));
                    }
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println("线程"+ Thread.currentThread().getName() + "==   执行");
                    latch.countDown();
                }
            },"thread--"+i);
        }
        for (Thread t : threads) {
            t.start();
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("线程"+ Thread.currentThread().getName() + "======"+t.getName()+"===============   启动");
        }
        try {
            latch.await(); //上面加了门闩，线程跑起来之后，等待门闩释放之后再往下执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("========="+(end - begin)+"=========");
    }

}
