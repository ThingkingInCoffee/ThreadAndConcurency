package threadDemos;

/**
 * wait 和 notifyAll 方法测试
 */
public class WaitAndNotifyAllDemo {

    private static Object object = new Object();

    public static void main(String[] args) {

        Thread a = new MyThread("AAA");
        Thread b = new MyThread("BBB");
        Thread c = new MyThread("CCC");

        a.start();
        b.start();
        c.start();

        //分别启动三个子线程，执行打印并执行wait方法，依次需要获取 object 对象的 同步锁

        try{
            System.out.println(Thread.currentThread().getName() + "=== call sleep(3000) ==="); //主线程继续向下执行
            Thread.sleep(3000); //主线程休眠 3 秒 保证ABC线程都执行完了 wait 方法放弃了 object 的同步锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (object) {  //主线程尝试获取 同步锁 并执行打印 和 notifyAll方法
            System.out.println(Thread.currentThread().getName() + "=== call notifyAll() ===");
            object.notifyAll(); //notifyAll 执行后 abc 进入就绪状态 等待主线程执行完毕释放 同步锁 后，竞争得到 同步锁并打印
        }


    }



    static class MyThread extends Thread {
        public MyThread(String name){
            super(name);
        }

        public void run(){
            synchronized (object) {
                try {
                    System.out.println(Thread.currentThread().getName() + "=== call wait() ===");

                    object.wait();

                    System.out.println(Thread.currentThread().getName() + "=== call continue ===");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
