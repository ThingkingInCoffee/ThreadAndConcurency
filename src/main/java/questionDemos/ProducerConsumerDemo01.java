package questionDemos;

import java.util.LinkedList;

public class ProducerConsumerDemo01<E> {

    private final LinkedList<E> list = new LinkedList<E>();
    private final  int MAX = 10;
    private int count = 0;

    public synchronized int getCount(){
        return count;
    }

    public synchronized void put(E e){
        while(list.size() == MAX) {
            try {
                this.wait();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        list.add(e);
        count++;
        this.notifyAll();
    }

    public synchronized E get() {
        E e = null;
        while (list.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        e = list.removeFirst();
        count--;
        this.notifyAll();
        return e;
    }

    public static void main(String[] args) {
        final ProducerConsumerDemo01<String> c = new ProducerConsumerDemo01<String>();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 5; j++) {
                        System.out.println(c.get());
                    }
                }
            },"consumer-"+i).start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 25; j++) {
                        c.put("contain value is " + j);
                    }
                }
            },"producer"+i).start();
        }
    }

}
