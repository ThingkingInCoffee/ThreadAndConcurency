package lockDemos;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    public static void main(String[] args) {
        WareHouse wareHouse = new WareHouse();
        Producer pro = new Producer(wareHouse);
        Consumer con = new Consumer(wareHouse);

        pro.produce(60);
        pro.produce(120);
        con.consume(90);
        con.consume(150);
        pro.produce(120);
    }

    /**
     * 仓库 版本一 不考虑仓库 容量 和 负数问题
     */
    static class WareHouse {
        private int size;
        private Lock lock;

        public WareHouse() {
            this.size = 0;
            this.lock = new ReentrantLock();
        }

        public void produce(int val) {
            lock.lock();
            try {
                size += val;
                System.out.printf("%s 生产 (%d) 个产品 --> 存储数 = %d\n", Thread.currentThread().getName(), val, size);
            } finally {
                lock.unlock();
            }
        }

        public void consume(int val) {
            lock.lock();
            try {
                size -= val;
                System.out.printf("%s 消费 (%d) 个产品 --> 存储数 = %d\n", Thread.currentThread().getName(), val, size);
            } finally {
                lock.unlock();
            }
        }
    }

    static class Producer {
        private WareHouse wareHouse;

        public Producer(WareHouse wareHouse) {
            this.wareHouse = wareHouse;
        }

        public void produce (final int val) {
            new Thread() {
                public void run () {
                    wareHouse.produce(val);
                }
            }.start();
        }
    }

    static class Consumer {
        private WareHouse wareHouse;

        public Consumer(WareHouse wareHouse) {
            this.wareHouse = wareHouse;
        }

        public void consume (final int val) {
            new Thread() {
                public void run () {
                    wareHouse.consume(val);
                }
            }.start();
        }
    }

}
