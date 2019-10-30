package lockDemos;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 手写一个自旋锁
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come into mylock");
        while(!atomicReference.compareAndSet(null,thread)) {
            System.out.println(Thread.currentThread().getName()+ "try to get");
        }
    }

    public void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoked unlock");
    }

    public static void main(String[] args) {
        SpinLockDemo demo = new SpinLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.myLock();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.myUnLock();
            }
        },"aaa").start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.myLock();
                demo.myUnLock();
            }
        },"bbb").start();

    }


}
