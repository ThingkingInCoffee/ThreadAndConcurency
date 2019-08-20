package questionDemos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 定义容器，
 * 提供增钾元素和获取元素的 size 方法
 * 启动两个线程，
 * 线程一项容器添加10个数据，
 * 线程二监听容器元素数量，当容量为5时，线程二输出信息并终止
 */
public class Thread01 {

    public static void main(String[] args) {
        //方法一   通过volatile
//        testVolatile();

        //
//        testSynchronized();

        testCountDownLatch();

    }


    public static void testVolatile() {
        final Container con = new Container();
        //启动线程一添加数据
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("add to container " + i);
                    con.add(new Object());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //启动线程二，监听容器大小
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (con.size() == 5) {
                        System.out.println("size is 5");
                        return;
                    }
                }
            }
        }).start();
    }

    public static void testSynchronized() {
        final Object lock = new Object();
        final Container01 container01 = new Container01();
        new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    if (container01.size() != 5) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.notify();
                    System.out.println("size is 5");
                }
                System.out.println("method 01 end");
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    for (int i = 0; i < 10; i++) {
                        container01.add(new Object());
                        System.out.println("add no is " + i);
                        if (container01.size() == 5) {
                            lock.notify();
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("method 02 end");
            }
        }).start();

    }

    public static void testCountDownLatch() {
        final Container01 con = new Container01();
        final CountDownLatch count = new CountDownLatch(5);
        new Thread(new Runnable() {
            public void run() {
                if (count.getCount() != 0) {
                    try {
                        count.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("count is 5");
                }
                System.out.println("method01 is  end");
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    con.add(new Object());
                    count.countDown();
                    System.out.println("add no is " + i);
                }
                System.out.println("method 02 end");
            }
        }).start();
    }

}

//volatile方式
class Container {
    volatile List<Object> container = new ArrayList<Object>();

    public void add(Object obj) {
        this.container.add(obj);
    }

    public int size() {
        return this.container.size();
    }
}

//synchronized 方式  和  countDownLatch 方式
class Container01 {
    volatile List<Object> container = new ArrayList<Object>();

    public void add(Object obj) {
        this.container.add(obj);
    }

    public int size() {
        return this.container.size();
    }
}
