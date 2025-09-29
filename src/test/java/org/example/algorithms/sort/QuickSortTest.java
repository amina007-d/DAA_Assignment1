package org.example.algorithms.sort;

import org.example.metrics.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class QuickSortTest {

    @Test
    void testQuickSortSmallArray() {
        int[] arr = {5, 2, 9, 1, 5, 6};
        ComparisonCounter comps = new ComparisonCounter();
        MoveCounter moves = new MoveCounter();
        RecursionDepthTracker depth = new RecursionDepthTracker();

        QuickSort sorter = new QuickSort(comps, moves, depth);
        sorter.sort(arr);

        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, arr);
        assertTrue(comps.get() > 0);
        assertTrue(moves.get() > 0);
        assertTrue(depth.getMax() > 0);
    }

    @Test
    void testQuickSortRandomLargeArray() {
        int n = 10_000;
        int[] arr = new Random().ints(n, 0, 100_000).toArray();
        int[] expected = Arrays.copyOf(arr, n);
        Arrays.sort(expected);

        ComparisonCounter comps = new ComparisonCounter();
        MoveCounter moves = new MoveCounter();
        RecursionDepthTracker depth = new RecursionDepthTracker();

        QuickSort sorter = new QuickSort(comps, moves, depth);
        sorter.sort(arr);

        assertArrayEquals(expected, arr);
        assertTrue(depth.getMax() < n / 2, "Recursion depth too high");
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        QuickSort sorter = new QuickSort(
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker());
        sorter.sort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSingleElementArray() {
        int[] arr = {42};
        QuickSort sorter = new QuickSort(
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker());
        sorter.sort(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testAllEqualElements() {
        int[] arr = {7, 7, 7, 7, 7};
        QuickSort sorter = new QuickSort(
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker());
        sorter.sort(arr);
        assertArrayEquals(new int[]{7, 7, 7, 7, 7}, arr);
    }

    @Test
    void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        QuickSort sorter = new QuickSort(
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker());
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testReverseSortedArray() {
        int[] arr = {9, 7, 5, 3, 1};
        QuickSort sorter = new QuickSort(
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker());
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 3, 5, 7, 9}, arr);
    }
}
