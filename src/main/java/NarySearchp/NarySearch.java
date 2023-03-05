package NarySearchp;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Math.floor;

public class NarySearch {

    public static int nary(int[] A, int lo, int hi, int key, int intv, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Integer>> futures = new ArrayList<>();
        int step = (hi - lo + 1) / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int start = lo + i * step;
            int end = (i == numThreads - 1) ? hi : start + step - 1;
            futures.add(executor.submit(() -> nary(A, start, end, key,intv)));
        }
        try {
            for (Future<Integer> future : futures) {
                Integer result = future.get();
                if (result != -1) {
                    executor.shutdown();
                    return result;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return -1;
    }

    public static int nary(int[] A, int lo, int hi, int key, int intv) {
        int[] mid = new int[intv + 1];
        char[] locate = new char[intv + 2];
        int pos = -1;
        locate[0] = 'R';
        locate[intv + 1] = 'L';
        while (lo <= hi && pos == -1) {
            mid[0] = lo -1;
            int step = (int) Math.floor( (hi - lo + 1)/(intv + 1));
            pos = markLoc(A, mid, locate, lo, hi, step, intv, key,pos);
            for (int i = 1; i <= intv; i++) {
                if (locate[i] != locate[i - 1]) {
                    lo = mid[i - 1] + 1;
                    hi = mid[i] - 1;
                }
            }
            if (locate[intv] != locate[intv + 1]) {
                lo = mid[intv] + 1;
            }
        }
        return pos;
    }

    private static int markLoc(int[] A, int[] mid, char[] locate, int lo, int hi, int step, int intv, int key,int pos) {
        for (int i = 1; i <= intv; i++) {
            int offs = step * i + (i - 1);
            mid[i] = lo + offs;
            int lmid = lo + offs;
            if (lmid <= hi) {
                if (A[lmid] > key) {
                    locate[i] = 'L';
                } else if (A[lmid] < key) {
                    locate[i] = 'R';
                } else {
                    locate[i] = 'E';
                    pos = lmid;
                }
            } else {
                mid[i] = hi + 1;
                locate[i] = 'L';
            }
        }
        return pos;
    }
}