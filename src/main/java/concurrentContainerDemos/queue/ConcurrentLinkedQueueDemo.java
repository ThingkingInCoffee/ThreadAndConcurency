package concurrentContainerDemos.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 底层是队列--链表实现的
 *  peek 只查看最先入队列的数据   不取出（即队列内容不变）
 *  poll 查看最先入队列的数据     并取出（即队列内容变化）
 */
public class ConcurrentLinkedQueueDemo {

    public static void main(String[] args) {
        Queue<String> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.offer("value is "+ i);
        }

        System.out.println(queue);
        System.out.println(queue.size());

        System.out.println(queue.peek());
        System.out.println(queue.size());
        System.out.println(queue);

        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue);

    }

}
