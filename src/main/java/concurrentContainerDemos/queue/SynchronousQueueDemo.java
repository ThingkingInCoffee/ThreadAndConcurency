package concurrentContainerDemos.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列 同步队列，容量为0  是一个特殊的 TransferQueue队列
 * 必须先有消费线程等待，才能使用队列
 */
public class SynchronousQueueDemo {

    BlockingQueue<String> queue = new SynchronousQueue<>();

    public static void main(String[] args) {
        final SynchronousQueueDemo demo = new SynchronousQueueDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"thread Begin");
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println(Thread.currentThread().getName()+"---" + demo.queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"out put ").start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //add 方法也可以添加数据，但是为非阻塞的，会直接向队列中插入数据，可能会抛出 Queue full 的 IllegalStateException异常
        demo.queue.add("add data");
        //put 方法为阻塞的，只有存在取值的操作才会放入数据
//        try {
//            demo.queue.put("put value");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println(Thread.currentThread().getName()+" size is : "+ demo.queue.size());

    }

}
