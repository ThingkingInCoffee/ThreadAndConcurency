package concurrencyDemo;

public class ThreadLocalDemo {

    volatile static String name = "zhangsan";
    static ThreadLocal<String> tl = new ThreadLocal<String>();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name);
                System.out.println("thread1 -- "+tl.get());
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                name = "lisi";
                tl.set("newName");
                System.out.println("thread2 -- "+tl.get());
            }
        }).start();
    }

}
