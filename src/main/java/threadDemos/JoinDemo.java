package threadDemos;

/**
 * join 方法 demo
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {

        Thread a = new Thread(new MyThread());
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(100);
            System.out.println("main print ---"+i);
            if (i == 12) {
                a.start();
            }
            if (i == 42) {
                a.join();
            }
        }
    }

    static class MyThread implements Runnable {

        public void run() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " p = " + Thread.currentThread().getPriority() + "  当前 i =" + i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
