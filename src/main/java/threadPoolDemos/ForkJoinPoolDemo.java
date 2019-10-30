package threadPoolDemos;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 分支合并线程池，可以递归完成复杂任务，要求可以进行分支合并的任务必须是forkJoinTask接口的实现类
 * ForkJoinTask类型提供了两个抽象子类型，RecursiveTask和RecursiveAction
 * RecursiveTask 有返回结果
 * RecursiveAction 无返回结果
 * ForkJoinPool没有所谓的容量，默认都是一个线程，根据任务自动的创建分支子线程，当子线程任务结束后，自动合并（通过fork，join实现），
 * 应用场景：
 *      主要用于科学计算或者天文计算、数据分析等，类似于map reduce 涉及思想
 */
public class ForkJoinPoolDemo {

    final static int[] numbers = new int[1000000];
    final static int MAX_SIZE = 50000;
    final static Random r = new Random();

    static int time;

    //初始化数组
    static {
        for (int i = 0; i < numbers.length; i++) {
//            numbers[i] = r.nextInt(1000);
            numbers[i] = 10;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AddTask task = new AddTask(numbers,0,1000000);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Future<Integer> future = pool.submit(task);
        System.out.println(future.get());
        pool.shutdown();
    }

    static class AddTask extends RecursiveTask<Integer>{

        private int min;
        private int max;
        private int[] arr;


        public AddTask(int[] array,int start, int end) {
            super();
            this.arr = array;
            this.min = start;
            this.max = end;
        }

        @Override
        protected Integer compute() {
            int result = 0;
            if (this.max -this.min < MAX_SIZE) {
                for (int i = this.min; i < this.max; i++) {
                    result += this.arr[i];
//                    System.out.println(Thread.currentThread().getName()+"--"+i);
                }
                return result;
            } else {
                System.out.println(++time);;
                int middle = (this.max+this.min)/2;
                AddTask left = new AddTask(this.arr,this.min,middle);
                AddTask right = new AddTask(this.arr,middle,this.max);
                left.fork();
                right.fork();
                return left.join()+right.join();
            }
        }
    }

}
