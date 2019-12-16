package lockDemos;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {

    public static void main(String[] args) {

        String arg01 = "资源AAA";
        String arg02 = "资源BBB";

        new Thread(new MySource(arg01,arg02),"AAA").start();

        new Thread(new MySource(arg02,arg01),"BBB").start();
    }

}

class MySource implements Runnable{
    private String arg01;
    private String arg02;

    public MySource(String arg01, String arg02) {
        this.arg01 = arg01;
        this.arg02 = arg02;
    }


    @Override
    public void run() {
        synchronized (arg01) {
            System.out.println(Thread.currentThread().getName()+"已经持有=="+arg01+"==尝试获得=="+arg02);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (arg02) {
                System.out.println(Thread.currentThread().getName()+"已经持有=="+arg02+"==尝试获得=="+arg01);
            }
        }
    }
}
