package org.example.algorithms.sort;

import org.example.metrics.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void testMergeSortCorrectness() {
        int[] arr = {5, 2, 9, 1, 5, 6};
        ComparisonCounter comps = new ComparisonCounter();
        MoveCounter moves = new MoveCounter();
        RecursionDepthTracker depth = new RecursionDepthTracker();

        MergeSort sorter = new MergeSort(5, comps, moves, depth);
        sorter.sort(arr);

        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, arr);
        assertTrue(comps.get() > 0);
        assertTrue(moves.get() > 0);
        assertTrue(depth.getMax() > 0);
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        MergeSort sorter = new MergeSort(5,
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker());
        sorter.sort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSingleElementArray() {
        int[] arr = {42};
        MergeSort sorter = new MergeSort(5,
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker());
        sorter.sort(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testAllEqualElements() {
        int[] arr = {7, 7, 7, 7, 7};
        MergeSort sorter = new MergeSort(5,
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker());
        sorter.sort(arr);
        assertArrayEquals(new int[]{7, 7, 7, 7, 7}, arr);
    }

    @Test
    void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        MergeSort sorter = new MergeSort(5,
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker());
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testReverseSortedArray() {
        int[] arr = {9, 7, 5, 3, 1};
        MergeSort sorter = new MergeSort(5,
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker());
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 3, 5, 7, 9}, arr);
    }
}
