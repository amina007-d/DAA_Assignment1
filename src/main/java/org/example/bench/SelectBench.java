package org.example.bench;

import org.example.algorithms.select.DeterministicSelect;
import org.example.metrics.ComparisonCounter;
import org.example.metrics.MoveCounter;
import org.example.metrics.RecursionDepthTracker;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SelectBench {
    private int[] arr;
    private Random random;

    @Param({"1000", "5000", "10000"})
    private int n;

    @Setup(Level.Iteration)
    public void setup() {
        random = new Random();
        arr = random.ints(n, 0, 1_000_000).toArray();
    }

    @Benchmark
    public int testDeterministicSelect() {
        DeterministicSelect select = new DeterministicSelect(
                new ComparisonCounter(),
                new MoveCounter(),
                new RecursionDepthTracker()
        );
        return select.select(Arrays.copyOf(arr, arr.length), n / 2);
    }

    @Benchmark
    public int testSortAndPick() {
        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);
        return copy[n / 2];
    }
}
