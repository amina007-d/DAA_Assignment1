package org.example.metrics;

public class NanoTimer {
    private long start;
    private long elapsed;

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        elapsed = System.nanoTime() - start;
    }

    public long getElapsed() {
        return elapsed;
    }
}