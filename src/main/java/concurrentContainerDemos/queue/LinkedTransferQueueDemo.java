package concurrentContainerDemos.queue;


import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * 转移队列  transfer 方法是阻塞的，当有线程来队列take数据时，才会执行数据的传递  不会往queue中存放数据 一般用于处理即时消息
 * add 方法不会阻塞，但是会在queue中存放数据
 */
public class LinkedTransferQueueDemo {

    TransferQueue<String> queue = new LinkedTransferQueue<>();

    public static void main(String[] args) {
        final LinkedTransferQueueDemo demo = new LinkedTransferQueueDemo();

        //启动一个线程获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "thread begin ");
                try {
                    System.out.println(Thread.currentThread().getName() + "-" + demo.queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"transfer Output").start();

        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("sleep 3 seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("begin trans data");
            demo.queue.transfer("trans something");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
