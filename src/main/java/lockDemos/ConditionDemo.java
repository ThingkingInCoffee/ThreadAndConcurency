package lockDemos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁绑定多个条件，线程唤醒时可以精确指定
 */
public class ConditionDemo {

    public static void main(String[] args) {
        ShareData data = new ShareData();

        new Thread( () -> {
            for (int i = 0; i < 3; i++) {
                data.print5();
            }
        },"AAA").start();

        new Thread( () -> {
            for (int i = 0; i < 3; i++) {
                data.print10();
            }
        },"BBB").start();

        new Thread( () -> {
            for (int i = 0; i < 3; i++) {
                data.print15();
            }
        },"CCC").start();
    }

}

class ShareData{
    private int flag = 1;
    Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (flag != 1){
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"线程打印"+i);
            }
            flag = 2;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (flag != 2){
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"线程打印"+i);
            }
            flag = 3;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (flag != 3){
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName()+"线程打印"+i);
            }
            flag = 1;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
