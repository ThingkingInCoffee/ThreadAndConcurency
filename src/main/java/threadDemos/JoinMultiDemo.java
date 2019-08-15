package threadDemos;

import java.util.ArrayList;
import java.util.List;

public class JoinMultiDemo {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("main start");
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 5; i++) {
            Thread a = new Thread(new MyThread("thread0"+i));
            threads.add(a);
        }
        for (Thread thread : threads) {
            thread.start();
            System.out.println(thread.getName()+" for begin");
        }
        for (Thread thread : threads) {
            try {
                System.out.println(Thread.currentThread().getName() + " join "+ thread.getName() +" start");
                thread.join();
                System.out.println(Thread.currentThread().getName() + " join "+ thread.getName() +" end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("main end");
    }

    static class MyThread extends Thread {

        public MyThread(String name){
            super(name);
        }

        public void run() {
            synchronized (this) {
                try {
                    System.out.println(Thread.currentThread().getName() + " run start");
                    int rate = Integer.valueOf(Thread.currentThread().getName().substring(7));
                    for (int i = 0; i < 3; i++) {
                        Thread.sleep(1000*rate);
                        System.out.println(Thread.currentThread().getName() + " p = " + Thread.currentThread().getPriority() + "  当前 i =" + i);
                    }
                    System.out.println(Thread.currentThread().getName() + " run end");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
