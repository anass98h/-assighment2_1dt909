package NarySearchp;



public class Main {
    public static void main(String[] args) {
        try {
            int[] arr = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
            int key = 8;
            int lo = 0;
            int hi = arr.length - 1;
            int intv = 3;

            int result = NarySearch.nary(arr, lo, hi, key, intv);
            if (result == -1) {
                System.out.println("Key not found in the array.");
            } else {
                System.out.println("Key found at index " + result + " in the array.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
