package org.example.metrics;

public class RecursionDepthTracker {
    private int current = 0;
    private int max = 0;

    public void enter() {
        current++;
        if (current > max) max = current;
    }

    public void exit() {
        current--;
    }

    public int getMax() {
        return max;
    }

    public void reset() {
        current = 0;
        max = 0;
    }
}