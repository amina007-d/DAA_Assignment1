package org.example.algorithms.sort;

import org.example.metrics.ComparisonCounter;
import org.example.metrics.MoveCounter;
import org.example.metrics.RecursionDepthTracker;

import java.util.Random;

public class QuickSort {
    private final ComparisonCounter comps;
    private final MoveCounter moves;
    private final RecursionDepthTracker depth;
    private final Random random = new Random();

    public QuickSort(ComparisonCounter comps,
                     MoveCounter moves,
                     RecursionDepthTracker depth) {
        this.comps = comps;
        this.moves = moves;
        this.depth = depth;
    }

    public void sort(int[] arr) {
        quicksort(arr, 0, arr.length - 1);
    }

    private void quicksort(int[] arr, int lo, int hi) {
        while (lo < hi) {
            depth.enter();

            int pivotIndex = lo + random.nextInt(hi - lo + 1);
            int pivotNewIndex = partition(arr, lo, hi, pivotIndex);

            // рекурсия идёт в меньшую часть
            if (pivotNewIndex - lo < hi - pivotNewIndex) {
                quicksort(arr, lo, pivotNewIndex - 1);
                lo = pivotNewIndex + 1; // хвост обрабатываем итеративно
            } else {
                quicksort(arr, pivotNewIndex + 1, hi);
                hi = pivotNewIndex - 1;
            }

            depth.exit();
        }
    }

    private int partition(int[] arr, int lo, int hi, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, hi);
        int storeIndex = lo;
        for (int i = lo; i < hi; i++) {
            comps.increment();
            if (arr[i] < pivotValue) {
                swap(arr, i, storeIndex);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, hi);
        return storeIndex;
    }

    private void swap(int[] arr, int i, int j) {
        if (i != j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            moves.add(3);
        }
    }
}
