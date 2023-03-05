package Threadpool;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        var threadPool = new ThreadPool(8);
        var workAmount = 120;
        final var futures = new ArrayList<CompletableFuture<Integer>>();

        int[] funnyVar = new int[] {0};

        threadPool.start();

        for (var i = 0; i < workAmount; i++) {
            futures.add(threadPool.queueWork(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return ++funnyVar[0]; // heavy workload
                }
            }));
        }

        readFutures(futures);

        // work done, load up more without starting/stopping threads
        System.out.println("something witty");

        futures.clear();

        for (var i = 0; i <= 15; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            futures.add(threadPool.queueWork(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return ++funnyVar[0]; // heavy workload
                }
            }));
        }

        readFutures(futures);

        threadPool.stop();

        try {
            threadPool.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void readFutures(ArrayList<CompletableFuture<Integer>> futures) {
        for (var future : futures) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
