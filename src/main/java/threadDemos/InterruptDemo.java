package threadDemos;


/**
 * 线程的 中断 和 终止方法
 * 分为两类：1、终止处于 阻塞 的线程   2终止处于 运行 的线程
 */
public class InterruptDemo {

    public static void main(String[] args) {

        try{
            Thread t1 = new MyThread("aaa");
            t1.start();
            System.out.println(t1.getName() + t1.getState() + " is started");


            Thread.sleep(300);
            t1.interrupt();
            System.out.println(t1.getName() + t1.getState() + " call interrupted");


            Thread.sleep(300);
            System.out.println(t1.getName() + t1.getState() + " is interrupted now");


        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        public void run() {
            try {
                int i = 0;
                while (!isInterrupted()) {
                    Thread.sleep(100);
                    i++;
                    System.out.println(Thread.currentThread().getName() + " = loop = " +i);
                }
            } catch (InterruptedException e) {
//                e.printStackTrace();
                System.out.println("catch interrupt exception");
            }
        }
    }

}
