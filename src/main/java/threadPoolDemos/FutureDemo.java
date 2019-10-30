package threadPoolDemos;

import java.util.concurrent.*;

public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);

        Future<String> future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("aaaaaaaaaa");
                return Thread.currentThread().getName()+"--test executor";
            }
        });

        System.out.println(future);
        System.out.println(future.isDone());

        System.out.println(future.get());
        System.out.println(future.isDone());
    }


}
