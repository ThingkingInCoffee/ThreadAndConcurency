package concurrentContainerDemos.queue;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 * put 和 take 方法--自动阻塞
 * 队列满后，自动阻塞  put
 * 队列空后，自动阻塞  take
 */
public class LinkedBlockingQueueDemo {

    final BlockingDeque<String> queue = new LinkedBlockingDeque<>();
    final Random r = new Random();

    public static void main(String[] args) {

        final LinkedBlockingQueueDemo demo = new LinkedBlockingQueueDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        demo.queue.put("value"+demo.r.nextInt(1000));
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"producer").start();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            System.out.println(Thread.currentThread().getName()+ "--" + demo.queue.take());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            },"consumer"+i).start();
        }

    }

}
