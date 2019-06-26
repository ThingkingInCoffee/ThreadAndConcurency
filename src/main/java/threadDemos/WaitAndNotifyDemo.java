package threadDemos;

/**
 *  wait 和 notify 的方法测试
 */
public class WaitAndNotifyDemo {

    public static void main(String[] args) {
        MyThread myThread = new MyThread("subThread");  //main 线程 创建 myThread 对象

        synchronized (myThread) { //main 线程获取 myThread 对象的同步锁
            try {
                System.out.println(Thread.currentThread().getName() + "=== call start() ==="); //main 线程打印 start
                myThread.start(); //main 线程启动 一个 名为 subThread 子线程执行子线程的 run 方法，
                                 // 但是同步代码块需要获取 调用run方法的对象(即myThread)的同步锁, 此时同步锁在main线程中，因此等待获取同步锁

                System.out.println(Thread.currentThread().getName() + "=== call wait() ==="); //main 线程打印 wait
                myThread.wait(); //main 线程中，调用myThread对象的wait方法将myThread对象的同步锁释放掉，main 线程进入阻塞状态，
                                 //同时，一直等待获取 myThread 对象同步锁的 subThread 子线程获得 myThread对象 的同步锁，
                                 //执行方法中的 打印和notify() 唤醒 myThread 对象上阻塞的其他线程(即main线程)进入就绪状态，然后子线程结束释放同步锁

                System.out.println(Thread.currentThread().getName() + "=== go continue ===");  //子线程结束，main 线程重新拿到 对象锁 进行打印
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyThread extends Thread {
        public MyThread(String name){
            super(name);
        }

        public void run(){
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + "=== call notify() ===");
                notify();
            }
        }
    }

}
