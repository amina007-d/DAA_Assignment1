package org.example.metrics;

public interface Counter {
    void increment();
    void add(long value);
    long get();
    void reset();
}
