package org.example.metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CounterTest {

    @Test
    void testComparisonCounter() {
        ComparisonCounter c = new ComparisonCounter();
        c.increment();
        c.add(4);
        assertEquals(5, c.get());
        c.reset();
        assertEquals(0, c.get());
    }
}
