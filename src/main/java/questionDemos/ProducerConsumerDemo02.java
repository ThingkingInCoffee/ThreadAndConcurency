package questionDemos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerDemo02 {

    public static void main(String[] args) {
        ShareData data = new ShareData();

        new Thread( () -> {
            for (int i = 0; i < 5; i++) {
                try {
                    data.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"AAA").start();

        new Thread( () -> {
            for (int i = 0; i < 5; i++) {
                try {
                    data.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"BBB").start();
    }

}


//资源类 共享争抢对象
class ShareData{
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition con = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            while (count != 0) {
                con.await();
            }
            count++;
            System.out.println(Thread.currentThread().getName()+"==="+count);
            con.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            while (count == 0) {
                con.await();
            }
            count--;
            System.out.println(Thread.currentThread().getName()+"==="+count);
            con.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}