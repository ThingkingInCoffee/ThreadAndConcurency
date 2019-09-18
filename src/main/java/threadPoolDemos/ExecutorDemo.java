package threadPoolDemos;

import java.util.concurrent.Executor;

/**
 * 线程池
 * 实现 executor 接口 实现 execute 方法
 * Executor 是线程池的一个底层实现，严格来说不能成为线程池
 * 调用线程池的时候，底层通过execute 方法调用线程中的逻辑
 */
public class ExecutorDemo implements Executor{

    public static void main(String[] args) {
        new ExecutorDemo().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "-- test executor");
            }
        });
    }

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
