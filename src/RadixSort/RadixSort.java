package RadixSort;

// https://www.geeksforgeeks.org/radix-sort/
import java.util.Arrays;

public class RadixSort {

    // Main method
    public static void radixSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }

        // Mendapatkan nilai maksimum dari array menggunakan fungsi getMax
        int max = getMax(arr, arr.length);

        // Melakukan counting sort untuk setiap digit, dimulai dari digit paling tidak signifikan
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }

    // Fungsi untuk mendapatkan nilai maksimum dari array
    private static int getMax(int arr[], int n) {
        int max_val = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max_val) {
                max_val = arr[i];
            }
        }
        return max_val;
    }

    // Metode untuk melakukan counting sort berdasarkan digit yang ditentukan
    private static void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        Arrays.fill(count, 0);

        // Menghitung jumlah kemunculan setiap digit
        for (int value : arr) {
            count[(value / exp) % 10]++;
        }

        // Menghitung posisi akhir setiap digit di output array
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Memindahkan elemen ke array output berdasarkan digit yang ditentukan
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // Menyalin output array ke array asli
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
}
