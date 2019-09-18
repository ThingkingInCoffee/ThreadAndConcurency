package concurrentContainerDemos.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * delayQueue 中 存放的 对象必须为 实现了 Delayed 接口的类，Delayed接口继承了comparable接口
 * 常用于定时任务
 */
public class DelayQueueDemo {

    static BlockingQueue<MyTask> queue = new DelayQueue();

    public static void main(String[] args) throws InterruptedException {
        long value = System.currentTimeMillis();
        MyTask task1 = new MyTask(value + 2000);
        MyTask task2 = new MyTask(value + 1000);
        MyTask task3 = new MyTask(value + 3000);
        MyTask task4 = new MyTask(value + 5000);
        MyTask task5 = new MyTask(value + 4400);

        queue.put(task1);
        queue.put(task2);
        queue.put(task3);
        queue.put(task4);
        queue.put(task5);

        System.out.println(queue);

        for (int i = 0; i < 5; i++) {
            System.out.println(queue.take());
        }
    }

    static class MyTask implements Delayed{

        private long compareValue;

        public MyTask (Long value) {
            this.compareValue = value;
        }

        /**
         * 获取计划时长
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(compareValue - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        }

        /**
         * 比较大小，实现自动升序
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            return (int)(this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "MyTask{" +
                    "compareValue=" + compareValue +
                    '}';
        }
    }

}
