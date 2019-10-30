package threadPoolDemos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);

        for (int i = 0; i < 5; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"-- test executor");
                }
            });
        }
        System.out.println(service);

        //默认60秒空闲后线程池会回收无用的线程
        try {
            Thread.sleep(66000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(service);
    }

}
