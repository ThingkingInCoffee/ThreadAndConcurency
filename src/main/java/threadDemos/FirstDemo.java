package threadDemos;

/**
 * 创建线程的两种方式，runnable 的形式会共享对象上的资源
 */
public class FirstDemo {

    static class MyThread extends Thread{
        private int tickets = 10;

        public void run(){
            for(int i = 0; i < 20; i++) {
                if(this.tickets > 0){
                    System.out.println(this.getName() + "卖票：ticket" + this.tickets--);
                }
            }
        }
    }

    static class MyRunnable implements Runnable{
        private int tickets = 10;

        public void run(){
            for(int i = 0; i < 20; i++) {
                if(this.tickets > 0){
                    System.out.println(Thread.currentThread().getName() + "卖票：ticket" + this.tickets--);
                }
            }
        }
    }

    public static void main(String[] args) {
//        MyThread m1 = new MyThread();
//        MyThread m2 = new MyThread();
//        MyThread m3 = new MyThread();
//
//        m1.start();
//        m2.start();
//        m3.start();

        MyRunnable r = new MyRunnable();

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(r);

        t1.start();
        t2.start();
        t3.start();

    }


}
