package questionDemos;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerDemo03 {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        ShareProduct product = new ShareProduct(blockingQueue);

        new Thread( () -> {
            System.out.println(Thread.currentThread().getName()+"生产线程启动");
            product.produce();
        },"produce").start();

        new Thread( () -> {
            System.out.println(Thread.currentThread().getName()+"生产线程启动");
            product.consumer();
        },"consumer").start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        product.close();

    }

}

/*
    共享的资源
 */
class ShareProduct{
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue;

    public ShareProduct(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
    }

    public void close(){
        this.flag = false;
    }

    //生产
    public void produce(){
        String data = null;
        boolean setDataFlag;
        while (flag){
            data = atomicInteger.incrementAndGet()+"号-产品";
            try {
                setDataFlag = blockingQueue.offer(data,2L, TimeUnit.SECONDS);
                if (setDataFlag) {
                    System.out.println(Thread.currentThread().getName()+"将"+data+"==插入队列成功oooooo");
                } else {
                    System.out.println(Thread.currentThread().getName()+"将"+data+"==插入队列失败xxxxxx");
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"停止营业了，flag="+flag+" 生产结束");
    }

    //消费
    public void consumer(){
        String result = null;
        while (flag) {
            try {
                result = blockingQueue.poll(10L,TimeUnit.SECONDS);
                if (result == null || result.equalsIgnoreCase("")) {
                    flag = false;
                    System.out.println(Thread.currentThread().getName()+"超过十秒钟没有取到产品，消费退出");
                    System.out.println();
                    System.out.println();
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"消费"+result+"成功");
        }

    }

}