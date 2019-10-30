package threadDemos;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现多线程的第三种方式 实现 callable 接口
 *
 *      Thread 构造方法只提供了接收实现 runnable 接口的类
 *
 *      futureTask 类实现了 RunnableFuture 接口，RunnableFuture 接口继承了 Runnable 和 Future 类
 *      futureTask 提供了传入实现callable接口实现类的构造方法
 *      callable --构造()--> futureTask --构造()--> Thread
 */
public class CallableDemo {

    public static void main(String[] args) {

        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyCallable());
        Thread t2 = new Thread(futureTask,"aaa");
        t2.start();
        int sum = 0;
        try {
            sum = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("result sum ======" + sum);

    }

}

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("======进入callable实现类======");
        return 1024;
    }
}


class MyRun implements Runnable{

    @Override
    public void run() {
        System.out.println("========进入runnable实现类========");
    }
}





















