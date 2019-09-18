package concurrentContainerDemos.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 底层数组实现的有界队列，自动阻塞，根据调用的API（add/put/offer）不同有不同特性
 * 当容量不足时，有阻塞能力
 */
public class ArrayBlockingQueueDemo {

    final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);

    public static void main(String[] args) {

        final ArrayBlockingQueueDemo demo = new ArrayBlockingQueueDemo();

        //add方法  当队列满时，会抛出异常 IllegalStateException
//        for (int i = 0; i < 5; i++) {
//            System.out.println("add method :" + demo.queue.add("value"+i));
//        }

        //put方法   当队列满时，会阻塞当前线程
//        for (int i = 0; i < 5; i++) {
//            try {
//                demo.queue.put("put"+i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("put" + i);
//        }

        //offer方法 队列满时，会放弃剩余数据，不会进行阻塞，不再往队列写入
//        for (int i = 0; i < 5; i++) {
//            System.out.println("offer method " + demo.queue.offer("value"+i));
//        }

        //offer方法指定阻塞时间和单位，超过指定时间之后放弃数据
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println("offer method : " + demo.queue.offer("value "+ i,1, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(demo.queue);

    }

}
