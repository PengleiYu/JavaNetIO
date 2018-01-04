package ch0302;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步块
 * Created by yupenglei on 18/1/4.
 */
public class SynchronizePrint implements Runnable {

    @Override
    public void run() {
        try {
            synchronized (System.out) {
                System.out.println("Hello");
                Thread.sleep(10);
                System.out.println("world");
                Thread.sleep(10);
                System.out.println("!!!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(new SynchronizePrint());
        }
    }
}
