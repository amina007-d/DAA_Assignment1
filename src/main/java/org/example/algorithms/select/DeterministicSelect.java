package org.example.algorithms.select;

import org.example.metrics.*;
import org.example.algorithms.util.ArrayUtil;

import java.util.Arrays;

public class DeterministicSelect {
    private final ComparisonCounter comps;
    private final MoveCounter moves;
    private final RecursionDepthTracker depth;

    public DeterministicSelect(ComparisonCounter comps,
                               MoveCounter moves,
                               RecursionDepthTracker depth) {
        this.comps = comps;
        this.moves = moves;
        this.depth = depth;
    }

    public int select(int[] arr, int k) {
        if (k < 0 || k >= arr.length)
            throw new IllegalArgumentException("k is out of bounds");
        depth.enter();
        int result = select(arr, 0, arr.length - 1, k);
        depth.exit();
        return result;
    }

    private int select(int[] arr, int lo, int hi, int k) {
        if (lo == hi) return arr[lo];

        int pivotIndex = median(arr, lo, hi);
        int pivotNewIndex = ArrayUtil.partition(arr, lo, hi, pivotIndex, comps, moves);

        if (k == pivotNewIndex) {
            return arr[k];
        } else if (k < pivotNewIndex) {
            return select(arr, lo, pivotNewIndex - 1, k);
        } else {
            return select(arr, pivotNewIndex + 1, hi, k);
        }
    }

    private int median(int[] arr, int lo, int hi) {
        int n = hi - lo + 1;
        if (n <= 5) {
            Arrays.sort(arr, lo, hi + 1);
            moves.add(n * (n - 1)); // грубая оценка
            return lo + n / 2;
        }

        int numMedians = (int) Math.ceil((double) n / 5);
        for (int i = 0; i < numMedians; i++) {
            int subLo = lo + i * 5;
            int subHi = Math.min(subLo + 4, hi);
            Arrays.sort(arr, subLo, subHi + 1);
            int medianIndex = subLo + (subHi - subLo) / 2;
            ArrayUtil.swap(arr, lo + i, medianIndex, moves);
        }
        return median(arr, lo, lo + numMedians - 1);
    }
}
