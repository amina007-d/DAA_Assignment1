package org.example.algorithms.util;

import org.example.metrics.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilTest {

    @Test
    void testSwap() {
        int[] arr = {1, 2, 3};
        MoveCounter moves = new MoveCounter();
        ArrayUtil.swap(arr, 0, 2, moves);
        assertArrayEquals(new int[]{3, 2, 1}, arr);
        assertEquals(3, moves.get());
    }

    @Test
    void testPartition() {
        int[] arr = {8, 9, 5, 4, 3, 1};
        ComparisonCounter comps = new ComparisonCounter();
        MoveCounter moves = new MoveCounter();
        int pivot = ArrayUtil.partition(arr, 0, arr.length - 1, 2, comps, moves);
        assertTrue(pivot >= 0 && pivot < arr.length);
        assertTrue(isSortedPivot(arr, pivot));
    }

    private boolean isSortedPivot(int[] arr, int pivot) {
        for (int i = 0; i < pivot; i++) {
            if (arr[i] > arr[pivot]) return false;
        }
        for (int i = pivot + 1; i < arr.length; i++) {
            if (arr[i] < arr[pivot]) return false;
        }
        return true;
    }

    @Test
    void testShuffle() {
        int[] arr = {1, 2, 3, 4, 5};
        ArrayUtil.shuffle(arr);
        assertEquals(5, arr.length);
        assertTrue(java.util.Arrays.stream(arr).allMatch(x -> x >= 1 && x <= 5));
    }

    @Test
    void testCheckBoundsThrows() {
        int[] arr = {1, 2, 3};
        assertThrows(IllegalArgumentException.class,
                () -> ArrayUtil.checkBounds(arr, -1, 2));
        assertThrows(IllegalArgumentException.class,
                () -> ArrayUtil.checkBounds(arr, 0, 5));
    }
}
