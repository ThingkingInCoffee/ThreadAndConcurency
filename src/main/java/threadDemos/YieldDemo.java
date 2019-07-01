package threadDemos;

/**
 * yield() 方法 demo
 */
public class YieldDemo {

    public static void main(String[] args) {

        MyThread a = new MyThread("aaa");
        MyThread b = new MyThread("bbb");

//        MyThread2 a = new MyThread2("aaa");
//        MyThread2 b = new MyThread2("bbb");

//        MyThread3 run = new MyThread3();
//        Thread a = new Thread(run);
//        Thread b = new Thread(run);
        a.start();
        b.start();

    }


    static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        public void run() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 10; i++) {
//                        Thread.sleep(100);
                        System.out.println(this.getName() + "  " + this.getPriority() + "  当前 i =" + i);
                        if (1 % 4 == 0) {
                            this.yield();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyThread2 extends Thread {
        public MyThread2(String name) {
            super(name);
        }

        public synchronized void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println(this.getName() + " p = " + this.getPriority() + "  当前 i =" + i);
                    if (1 % 4 == 0) {
                        Thread.yield();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class MyThread3 implements Runnable {

        public synchronized void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " p = " + Thread.currentThread().getPriority() + "  当前 i =" + i);
                    if (1 % 4 == 0) {
                        Thread.yield();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
