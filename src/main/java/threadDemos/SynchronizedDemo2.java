package threadDemos;

/**
 * 测试 synchronized 实例锁 和 全局锁
 * 实例锁--锁存在于某一个对象实例上，如果该实例是单例的，那么该锁也具有全局锁的概念。
 * 实例锁对应的是 synchronized  关键字
 * 全局锁--该锁针对的是某一个类，无论多少个实例对象，所有线程都共享该锁。
 * 全局锁对应的是 static synchronized （或者是锁在该类的 class 或者 classloader对象上）
 * <p>
 * class Something {
 * public synchronized void isSyncA(){}
 * public synchronized void isSyncB(){}
 * public static synchronized void cSyncA(){}
 * public static synchronized void cSyncB(){}
 * }
 * 假设，Something有两个实例 x 和 y。分析下面4组表达式获取的锁的情况。
 * x.isSyncA()与x.isSyncB() ；不能同时访问，调用的是两个不同实例方法，锁是某个实例对象，调用的方法对象相同，因此是同一个锁对象
 * x.isSyncA()与y.isSyncA()；可以同时访问，调用的是实例方法，实例方法的锁是某个实例对象，但调用方法的对象不同 x和y，因此是两个不同锁对象
 * x.cSyncA()与y.cSyncB()；不能同时访问，两个都是静态方法，锁的对象是 类对象，因此两个实例对象xy竞争的是同一个锁对象
 * x.isSyncA()与Something.cSyncA()  可以同时访问，一个是实例方法，锁对象是某个实例对象，一个是静态方法，锁对象是类。因此是两个不同锁对象
 */
public class SynchronizedDemo2 {

    public static void main(String[] args) {
        Something x = new Something();
        Something y = new Something();
        //同一对象调用不同synchronized方法
//        test01(x);
        //不同对象调用相同synchronized方法
//        test02(x,y);
        //不同对象调用不同的 static synchronized方法
//        test03(x,y);
        //对象调用 synchronized方法 和 类调用 static synchronized方法
        test04(x);
    }

    static class Something {
        public synchronized void isSyncA() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(500); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " : isSyncA");
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        public synchronized void isSyncB() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(600); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " : isSyncB");
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        public static synchronized void cSyncA() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(700); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " : cSyncA");
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        public static synchronized void cSyncB() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(800); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " : cSyncB");
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public static void test01 (final Something something){
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                something.isSyncA();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                something.isSyncB();
            }
        });
        t1.start();
        t2.start();
    }

    public static void test02 (final Something something1,final Something something2){
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                something1.isSyncA();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                something2.isSyncA();
            }
        });
        t1.start();
        t2.start();
    }

    public static void test03 (final Something something1,final Something something2){
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                something1.cSyncA();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                something2.cSyncB();
            }
        });
        t1.start();
        t2.start();
    }

    public static void test04 (final Something something){
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                something.isSyncA();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                Something.cSyncA();
            }
        });
        t1.start();
        t2.start();
    }


}
