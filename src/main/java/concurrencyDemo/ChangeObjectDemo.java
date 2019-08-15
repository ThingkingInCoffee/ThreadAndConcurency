package concurrencyDemo;

/**
 * synchronized 锁的是对象，不是对象的引用，所以在对象更换之后 并不影响线程2的执行
 * 同步代码一旦上锁之后，会有一个临时的锁的引用指向锁对象，和真实的引用无直接关联
 * 锁未释放之前，修改引用指向的对象，不会影响同步代码块的执行
 */
public class ChangeObjectDemo {

    Object o = new Object();

    void m() {
        System.out.println(Thread.currentThread().getName() + " start");
        synchronized (o){
            while (true) {
                try {
                    Thread.sleep(100);
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " executed" + o);
            }
        }
    }

    public static void main(String[] args) {

        final ChangeObjectDemo d = new ChangeObjectDemo();
        new Thread(new Runnable() {
            public void run() {
                d.m();
            }
        },"thread-1").start();

        try {
            Thread.sleep(300);
            //此处睡眠值应大于 方法中的睡眠值 保证线程1正常打印至少一次，才能体现出对象变更
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                d.m();
            }
        },"thread-2");
        d.o = new Object();
        thread2.start();
        System.out.println("main end");
    }

}
