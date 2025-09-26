package org.example.algorithms.util;

import org.example.metrics.ComparisonCounter;
import org.example.metrics.MoveCounter;

import java.util.Random;

public class ArrayUtil {
    private static final Random random = new Random();

    public static void swap(int[] arr, int i, int j, MoveCounter moves) {
        if (i != j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            if (moves != null) moves.add(3);
        }
    }

    public static void shuffle(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    public static int partition(int[] arr, int lo, int hi, int pivotIndex,
                                ComparisonCounter comps, MoveCounter moves) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, hi, moves);

        int storeIndex = lo;
        for (int i = lo; i < hi; i++) {
            if (comps != null) comps.increment();
            if (arr[i] < pivotValue) {
                swap(arr, i, storeIndex, moves);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, hi, moves);
        return storeIndex;
    }

    public static void checkBounds(int[] arr, int lo, int hi) {
        if (arr == null) throw new IllegalArgumentException("Array cannot be null");
        if (lo < 0 || hi >= arr.length || lo > hi) {
            throw new IllegalArgumentException("Invalid bounds: lo=" + lo + ", hi=" + hi);
        }
    }
}
