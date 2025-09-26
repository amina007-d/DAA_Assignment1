package org.example.metrics;

public class MoveCounter implements Counter {
    private long count = 0;

    @Override
    public void increment() { count++; }

    @Override
    public void add(long value) { count += value; }

    @Override
    public long get() { return count; }

    @Override
    public void reset() { count = 0; }
}
