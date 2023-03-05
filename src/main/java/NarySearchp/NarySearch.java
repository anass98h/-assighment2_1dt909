package NarySearchp;

import java.util.concurrent.*;

public class NarySearch {

    public static int nary(int[] A, int lo, int hi, int key, int intv, int numThreads) throws InterruptedException, ExecutionException {
        int[] mid = new int[intv + 1];
        char[] locate = new char[intv + 2];
        int pos = Integer.MIN_VALUE;
        locate[0] = 'R';
        locate[intv + 1] = 'L';

        int chunkSize = (hi - lo + 1) / numThreads;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Future<Integer>[] futures = new Future[numThreads];

        for (int t = 0; t < numThreads; t++) {
            int startIndex = lo + t * chunkSize;
            int endIndex = (t == numThreads - 1) ? hi + 1 : startIndex + chunkSize;
            futures[t] = executor.submit(new MarkLocTask(A, mid, locate, startIndex, endIndex, intv, key));
        }

        for (int t = 0; t < numThreads; t++) {
            pos = futures[t].get();
            if (pos != Integer.MIN_VALUE) {
                break;
            }
        }

        executor.shutdown();

        for (int i = 1; i <= intv; i++) {
            if (locate[i] != locate[i - 1]) {
                lo = mid[i - 1] + 1;
                hi = mid[i] - 1;
            }
        }
        if (locate[intv] != locate[intv + 1]) {
            lo = mid[intv] + 1;
        }

        return pos;
    }

    private static class MarkLocTask implements Callable<Integer> {
        private final int[] A;
        private final int[] mid;
        private final char[] locate;
        private final int startIndex;
        private final int endIndex;
        private final int intv;
        private final int key;

        public MarkLocTask(int[] A, int[] mid, char[] locate, int startIndex, int endIndex, int intv, int key) {
            this.A = A;
            this.mid = mid;
            this.locate = locate;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.intv = intv;
            this.key = key;
        }

        public Integer call() {
            int pos = Integer.MIN_VALUE;
            int step = (endIndex - startIndex) / (intv + 1);

            for (int i = 1; i <= intv; i++) {
                int offs = step * i + (i - 1);
                mid[i] = startIndex + offs;
                int lmid = startIndex + offs;
                if (lmid < endIndex) {
                    if (A[lmid] > key) {
                        locate[i] = 'L';
                    } else if (A[lmid] < key) {
                        locate[i] = 'R';
                    } else {
                        locate[i] = 'E';
                        pos = lmid;
                    }
                } else {
                    mid[i] = endIndex;
                    locate[i] = 'L';
                }
            }

            return pos;
        }
    }
}