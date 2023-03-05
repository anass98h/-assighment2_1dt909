package Threadpool;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class ThreadPool {
    static final int max_tries = 10;
    int threads;
    LinkedBlockingQueue<Callable<?>> queue;
    Thread[] pool;

    public ThreadPool(int threads) {
        this.threads = threads;
        queue = new LinkedBlockingQueue<>();
        pool = new Thread[threads];
    }

    public <T> Boolean queueWork(Callable<T> cb) {
        var iter = 0;
        Optional<InterruptedException> oiex = null;
        while (iter < max_tries) {
            try {
                queue.put(cb);
                return true; // successfully queue:d work
            } catch (InterruptedException e) {
                oiex = Optional.of(e);
            }
        }
        oiex.ifPresent(InterruptedException::printStackTrace); // 😎
        return false; // work was unable to be queue:d
    }

    protected void finalize() throws Throwable {
        System.out.println("blue was not the impostor");
        
    }
}
