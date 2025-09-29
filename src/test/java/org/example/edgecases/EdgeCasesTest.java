package org.example.edgecases;

import org.example.algorithms.sort.MergeSort;
import org.example.algorithms.sort.QuickSort;
import org.example.algorithms.select.DeterministicSelect;
import org.example.algorithms.divideandconquer.ClosestPair;
import org.example.metrics.ComparisonCounter;
import org.example.metrics.MoveCounter;
import org.example.metrics.RecursionDepthTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EdgeCasesTest {

    @Test
    void testEmptyArray_MergeSort() {
        int[] arr = {};
        new MergeSort(5, null, null, null).sort(arr);
        assertEquals(0, arr.length);
    }

    @Test
    void testSingleElement_QuickSort() {
        int[] arr = {42};
        new QuickSort(null, null, null).sort(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testEmptyArray_Select() {
        DeterministicSelect select = new DeterministicSelect(null, null, null);
        assertThrows(IllegalArgumentException.class, () -> select.select(new int[]{}, 0));
    }

    @Test
    void testSingleElement_Select() {
        DeterministicSelect select = new DeterministicSelect(
                new ComparisonCounter(),
                new MoveCounter(),
                new RecursionDepthTracker()
        );
        int[] arr = {99};
        assertEquals(99, select.select(arr, 0));
    }


    @Test
    void testNotEnoughPoints_ClosestPair() {
        assertThrows(IllegalArgumentException.class,
                () -> ClosestPair.closestPair(new ClosestPair.Point[]{ new ClosestPair.Point(1, 1) }));
    }
}
