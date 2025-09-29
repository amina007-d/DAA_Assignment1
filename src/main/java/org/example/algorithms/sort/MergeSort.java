package org.example.algorithms.sort;

import org.example.metrics.ComparisonCounter;
import org.example.metrics.MoveCounter;
import org.example.metrics.RecursionDepthTracker;

public class MergeSort {
    private final int cutoff;
    private final ComparisonCounter comps;
    private final MoveCounter moves;
    private final RecursionDepthTracker depth;

    public MergeSort(int cutoff,
                     ComparisonCounter comps,
                     MoveCounter moves,
                     RecursionDepthTracker depth) {
        this.cutoff = cutoff;
        this.comps = comps;
        this.moves = moves;
        this.depth = depth;
    }


    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] aux = new int[arr.length]; // единый буфер
        sort(arr, aux, 0, arr.length - 1);
    }


    private void sort(int[] arr, int[] aux, int lo, int hi) {
        if (hi - lo + 1 <= cutoff) {
            insertionSort(arr, lo, hi);
            return;
        }

        depth.enter();

        int mid = lo + (hi - lo) / 2;
        sort(arr, aux, lo, mid);
        sort(arr, aux, mid + 1, hi);

        merge(arr, aux, lo, mid, hi);

        depth.exit();
    }

    private void merge(int[] arr, int[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = arr[k];
            moves.increment();
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                arr[k] = aux[j++];
                moves.increment();
            } else if (j > hi) {
                arr[k] = aux[i++];
                moves.increment();
            } else {
                comps.increment();
                if (aux[j] < aux[i]) {
                    arr[k] = aux[j++];
                    moves.increment();
                } else {
                    arr[k] = aux[i++];
                    moves.increment();
                }
            }
        }
    }

    private void insertionSort(int[] arr, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= lo) {
                comps.increment();
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    moves.increment();
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
            moves.increment();
        }
    }
}
