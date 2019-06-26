package threadDemos;

/**
 * 线程的 start 方法 和 run 方法
 * start 会新开线程执行
 * run 当前线程执行run 方法
 */
public class StartAndRunDemo {

    public static void main(String[] args) {
        Thread myThread = new MyThread("==myThread==");

        System.out.println(Thread.currentThread().getName() + " ==== call run() ===");
        myThread.run();

        System.out.println(Thread.currentThread().getName() + " ==== call start() ===");
        myThread.start();
    }

    static class MyThread extends Thread{
        public MyThread(String threadName){
            super(threadName);
        }

        public void run(){
            System.out.println(Thread.currentThread().getName() + " is running");
        }
    }

}
