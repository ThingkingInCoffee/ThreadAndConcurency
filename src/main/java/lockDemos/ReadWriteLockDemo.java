package lockDemos;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：
 *      多个线程同时读一个资源没有问题，所以为了满足并发量，读取共享资源应该可以同时进行
 *      但是
 *      如果有一个线程想去写共享资源，就不应该再有其他线程可以对该资源进行都或者写
 *
 *      读-读 可共存
 *      写-写 不可共存
 *
 *      写操作：原子性 和 独占性    整个过程必须是一个完整的，不可分割的过程
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache cache = new MyCache();
        //写入
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cache.put("key"+temp,temp);
                }
            },"i--"+i).start();
        }

        //读取
        for (int j = 0; j < 5; j++) {
            final int temp = j;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cache.get("key"+temp);
                }
            },"j--"+j).start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("==========================================");

        MyCache2 cache2 = new MyCache2();
        //写入
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cache2.put("key"+temp,temp);
                }
            },"ii--"+i).start();
        }

        //读取
        for (int j = 0; j < 5; j++) {
            final int temp = j;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cache2.get("key"+temp);
                }
            },"jj--"+j).start();
        }
    }

}


//资源类
class MyCache{

    private volatile Map<String,Object> map = new HashMap<>();

    public void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName()+"==正在写入");
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put(key, value);
        System.out.println(Thread.currentThread().getName()+"==写入完成");
    }

    public void get(String key) {
        System.out.println(Thread.currentThread().getName()+"==正在读取");
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object result = map.get(key);
        System.out.println(Thread.currentThread().getName()+"==读取结束:"+result);
    }

}

//资源类 2 引入读写锁
class MyCache2{

    private volatile Map<String,Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {

        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"==正在写入");
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"==写入完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }

    }

    public void get(String key) {
        System.out.println(Thread.currentThread().getName()+"==正在读取");
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object result = map.get(key);
        System.out.println(Thread.currentThread().getName()+"==读取结束:"+result);
    }

}