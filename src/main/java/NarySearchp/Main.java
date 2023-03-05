package NarySearchp;



public class Main {
    public static void main(String[] args) {
        int[] A = {1, 2, 3, 4, 5, 8, 10, 21, 26, 29, 35, 38, 39};
        int lo = 0;
        int hi = A.length - 1;
        int key = 4;
        int intv = 2;
        int numThreads = 4;

        int pos = NarySearch.nary(A, lo, hi, key, intv,numThreads);
        System.out.println("Position of key: " + pos);
    }
}