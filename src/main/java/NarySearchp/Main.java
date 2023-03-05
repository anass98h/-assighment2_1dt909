package NarySearchp;


import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25};
        int key = 9;
        int numThreads = 2;
        int parallelValues = 3;

        int index = NarySearch.nary(arr, 0, arr.length - 1, key, numThreads, parallelValues);
        System.out.println("Index of " + key + " is " + index);

        // Test with different number of threads and parallel values
        int[] numThreadsArr = {1, 2, 4};
        int[] parallelValuesArr = {1, 2, 3};
        for (int i = 0; i < numThreadsArr.length; i++) {
            for (int j = 0; j < parallelValuesArr.length; j++) {
                int nt = numThreadsArr[i];
                int pv = parallelValuesArr[j];
                int idx = NarySearch.nary(arr, 0, arr.length - 1, key, nt, pv);
                System.out.println("With " + nt + " threads and " + pv + " parallel values: " + idx);
            }
        }
    }

}
