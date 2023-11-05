package PeekSort;

// https://github.com/sebawild/nearly-optimal-mergesort-code/
public class MergesAndRuns {
    /**
     * Merges runs A[l..m-1] and A[m..r] in-place into A[l..r]
     * with Sedgewick's bitonic merge (Program 8.2 in Algorithms in C++)
     * using B as temporary storage.
     * B.length must be at least r+1.
     */
    public static void mergeRuns(int[] A, int l, int m, int r, int[] B) {
        --m;// mismatch in convention with Sedgewick
        int i, j;
        assert B.length >= r+1;
        for (i = m+1; i > l; --i) 
            B[i-1] = A[i-1];
        for (j = m; j < r; ++j) 
            B[r+m-j] = A[j+1];
        for (int k = l; k <= r; ++k)
            A[k] = B[j] < B[i] ? B[j--] : B[i++];
    }

    /**
     * Reverse the specified range of the specified array.
     *
     * @param a  the array in which a range is to be reversed
     * @param lo the index of the first element in the range to be
     *           reversed
     * @param hi the index of the last element in the range to be
     *           reversed
     */
    public static void reverseRange(int[] a, int lo, int hi) {
        while (lo < hi) {
            int t = a[lo]; 
            a[lo++] = a[hi]; 
            a[hi--] = t;
        }
    }


    public static int extendWeaklyIncreasingRunLeft(final int[] A, int i, final int left) {
        while (i > left && A[i-1] <= A[i]) --i;
        return i;
    }

    public static int extendWeaklyIncreasingRunRight(final int[] A, int i, final int right) {
        while (i < right && A[i+1] >= A[i]) ++i;
        return i;
    }

    public static int extendStrictlyDecreasingRunLeft(final int[] A, int i, final int left) {
        while (i > left && A[i-1] > A[i]) --i;
        return i;
    }

    public static int extendStrictlyDecreasingRunRight(final int[] A, int i, final int right) {
        while (i < right && A[i+1] < A[i]) ++i;
        return i;
    }
}