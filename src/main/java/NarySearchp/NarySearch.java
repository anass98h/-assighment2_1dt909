package NarySearchp;


import static java.lang.Math.floor;

public class NarySearch {

    public static int nary(int[] A, int lo, int hi, int key, int intv) {
        int[] mid = new int[intv + 1];
        char[] locate = new char[intv + 2];
        int pos = -1;
        locate[0] = 'R';
        locate[intv + 1] = 'L';
        while (lo <= hi && pos == Integer.MIN_VALUE) {
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