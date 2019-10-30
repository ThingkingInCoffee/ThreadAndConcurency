package threadPoolDemos;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 计划任务线程池   阻塞式，效率较低   本质是delayQueue
 * 参数
 * runnable 要执行的任务
 * initialDelay 第一次任务执行的间隔
 * period 多次任务执行的间隔
 * timeUnit 多次任务执行的间隔的单位
 * 使用场景：
 *      计划任务时选用，
 */
public class ScheduleThreadPoolDemo {

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        System.out.println(service);

        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"--test");
            }
        },0,300,TimeUnit.MILLISECONDS);
    }

}
