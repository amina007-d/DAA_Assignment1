package org.example.algorithms.select;

import org.example.metrics.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class DeterministicSelectTest {
    @Test
    void testSmallArray() {
        int[] arr = {7, 2, 9, 4, 1};
        ComparisonCounter comps = new ComparisonCounter();
        MoveCounter moves = new MoveCounter();
        RecursionDepthTracker depth = new RecursionDepthTracker();

        DeterministicSelect select = new DeterministicSelect(comps, moves, depth);
        int result = select.select(arr, 2); // 3-й элемент по порядку (0-based)
        assertEquals(4, result);
    }

    @Test
    void testLargeArray() {
        int n = 1000;
        int[] arr = new Random().ints(n, 0, 10000).toArray();
        int[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);

        ComparisonCounter comps = new ComparisonCounter();
        MoveCounter moves = new MoveCounter();
        RecursionDepthTracker depth = new RecursionDepthTracker();

        DeterministicSelect select = new DeterministicSelect(comps, moves, depth);

        for (int k = 0; k < 10; k++) {
            int result = select.select(Arrays.copyOf(arr, arr.length), k);
            assertEquals(sorted[k], result);
        }
    }

    @Test
    void testOutOfBounds() {
        DeterministicSelect select = new DeterministicSelect(
                new ComparisonCounter(), new MoveCounter(), new RecursionDepthTracker()
        );
        int[] arr = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> select.select(arr, -1));
        assertThrows(IllegalArgumentException.class, () -> select.select(arr, 5));
    }
}
