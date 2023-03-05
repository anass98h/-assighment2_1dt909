package NarySearchp;


import java.util.Random;

public class Main {
    public static void main(String[] args) {
        runExperiment(10000000,10100000,2,6);
    }
    public static void runExperiment(int minimumListZise, int MaximumListSize, int MinmumNumberofThreads, int MaximumNumberOfThreads) {
        Random rand = new Random();

        for (int listSize = minimumListZise; listSize <= MaximumListSize; listSize += 1000000) {


            int[] A = new int[listSize];
            for (int i = 0; i < listSize; i++) {
                A[i] = rand.nextInt(listSize*2);
            }





                for (int numThreads = MinmumNumberofThreads; numThreads <= MaximumNumberOfThreads; numThreads++) {



                    long BSstartTime = System.currentTimeMillis();

                    BinarySearch bs = new BinarySearch();


                    int result = bs.binarySearch(A,0,A.length-1,A[rand.nextInt(A.length)]);

                    long BSendTime = System.currentTimeMillis();
                    long startTime = System.currentTimeMillis();
                    int pos = NarySearch.nary(A, 0, A.length-1, A[rand.nextInt(A.length)], 1, numThreads);
                    long endTime = System.currentTimeMillis();


                    System.out.println("List size: " + listSize  + " | Threads: " + numThreads + " | Time (ms): " + (endTime - startTime));
                    System.out.println("List size: " + listSize  + " | Time (ms): " + (BSendTime - BSstartTime));
                }

        }
    }
}