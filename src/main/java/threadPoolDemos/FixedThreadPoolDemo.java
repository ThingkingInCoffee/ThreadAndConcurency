package threadPoolDemos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 定长容量的线程池
 * ExecutorService  线程池服务类型，所有的线程池类都实现了这个接口，
 * 实现此接口代表具有提供线程池的能力
 *
 * Executors  Executor的工具类，类似于 Collection和Collections的关系
 * 可以更简单的创建若干种线程池
 *
 * shutDown方法  优雅关闭， 不是强行关闭线程池，回收线程池中的资源。而是不在处理新的任务，将已有任务处理完毕后再关闭
 *
 * 线程池的状态
 * running
 * shutting down
 * terminated
 *
 */
public class FixedThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 7; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"-- test executor");
                }
            });
        }
        System.out.println(service);

        service.shutdown();
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());

        System.out.println(service);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());

        System.out.println(service);

    }

}
