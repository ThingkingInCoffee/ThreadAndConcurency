package threadDemos;

/**
 * 测试 synchronized 同步锁的三种规则
 * 第一条：线程A 访问 某对象A 的synchronized 方法/代码块 时，线程B（其他线程）对该对象（对象A）的该synchronized方法/代码块 的访问将被阻塞
 * 第二条：线程A 访问 某对象A 的synchronized 方法/代码块 时，线程B（其他线程）对该对象（对象A）的非synchronized方法/代码块 的访问不被阻塞
 * 第三条：线程A 访问 某对象A 的synchronized 方法/代码块 时，线程B（其他线程）对该对象（对象A）的其他synchronized方法/代码块 的访问将被阻塞
 */
public class SynchronizedDemo {

    public static void main(String[] args) {
        //================ 规则一 =================================
        //1、执行时存在同步代码块，代码块中同步锁 锁的是 两个线程共享的 myRun 对象，t2 需要等待 t1 释放同步锁
//        MyRunnable myRun = new MyRunnable();
//        Thread t1 = new Thread(myRun);
//        Thread t2 = new Thread(myRun);
//        t1.start();
//        t2.start();

        //2、执行时，虽然存在同步块，但是锁的对象分别是 t1 t2两个不同的thread对象，不存在互斥，因此是 t1 t2穿插进行的
//        MyThread t1 = new MyThread("myThread01");
//        MyThread t2 = new MyThread("myThread02");
//        t1.start();
//        t2.start();

        //=================== 规则二 ==============================
//        final Rule02 rule02 = new Rule02();
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                rule02.synMethod();
//            }
//        });
//        Thread t2 = new Thread(new Runnable() {
//            public void run() {
//                rule02.noSynMethod();
//            }
//        });
//        t1.start();
//        t2.start();

        //=================== 规则三 ==============================
        final Rule02 rule03 = new Rule02();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                rule03.synMethod();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                rule03.synMethod2();
            }
        });
        t1.start();
        t2.start();

    }



    //设置同步代码块 锁的对象 this 是 Runnable 的实例对象
    static class MyRunnable implements Runnable {
        public void run() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName() + " loop " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        public void run() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName() + " =loop= " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 测试 规则 二   当调用某对象的同步方法/代码块 时，非同步方法/代码块 的调用不受影响
     */
    static class Rule02 {
        public void synMethod() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(800);
                        System.out.println(Thread.currentThread().getName() + " _loop_ " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void noSynMethod() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(300);
                    System.out.println(Thread.currentThread().getName() + " `loop` " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void synMethod2() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(400);
                        System.out.println(Thread.currentThread().getName() + " -loop- " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
