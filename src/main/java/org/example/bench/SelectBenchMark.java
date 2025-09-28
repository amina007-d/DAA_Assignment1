package org.example.bench;

import org.example.algorithms.select.DeterministicSelect;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SelectBenchMark {

    @Param({"1000", "10000", "100000"})
    private int size;

    @Param({"first", "middle", "last"})
    private String kType;

    private int[] data;
    private int k;

    @Setup(Level.Invocation)
    public void setup() {
        Random rand = new Random(42);
        data = rand.ints(size, 0, size * 10).toArray();

        switch (kType) {
            case "first" -> k = 0;
            case "middle" -> k = size / 2;
            case "last" -> k = size - 1;
        }
    }

    @Benchmark
    public int benchmarkDeterministicSelect() {
        return DeterministicSelect.pureSelect(Arrays.copyOf(data, data.length), k);
    }

    @Benchmark
    public int benchmarkArraysSort() {
        int[] copy = Arrays.copyOf(data, data.length);
        Arrays.sort(copy);
        return copy[k];
    }
}
