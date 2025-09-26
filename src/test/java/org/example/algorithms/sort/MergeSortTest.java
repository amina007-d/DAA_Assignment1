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
}
