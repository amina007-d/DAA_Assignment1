package org.example.algorithms.sort;

import org.example.metrics.ComparisonCounter;
import org.example.metrics.MoveCounter;
import org.example.metrics.RecursionDepthTracker;
import org.example.algorithms.util.ArrayUtil;

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
            int pivotNewIndex = ArrayUtil.partition(arr, lo, hi, pivotIndex, comps, moves);

            if (pivotNewIndex - lo < hi - pivotNewIndex) {
                quicksort(arr, lo, pivotNewIndex - 1);
                lo = pivotNewIndex + 1;
            } else {
                quicksort(arr, pivotNewIndex + 1, hi);
                hi = pivotNewIndex - 1;
            }

            depth.exit();
        }
    }
}
