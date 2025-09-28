package org.example.cli;

import org.example.metrics.*;
import org.example.algorithms.sort.MergeSort;
import org.example.algorithms.sort.QuickSort;
import org.example.algorithms.select.DeterministicSelect;
import org.example.algorithms.divideandconquer.ClosestPair;

import java.io.IOException;
import java.util.Random;

public class CliRunner {

    private final String algo;
    private final int size;
    private final int trials;
    private final String output;

    public CliRunner(String algo, int size, int trials, String output) {
        this.algo = algo;
        this.size = size;
        this.trials = trials;
        this.output = output;
    }

    public void run() throws IOException {
        try (CSVWriter csv = new CSVWriter(output)) {
            Random rand = new Random();

            for (int t = 0; t < trials; t++) {
                ComparisonCounter comps = new ComparisonCounter();
                MoveCounter moves = new MoveCounter();
                RecursionDepthTracker depth = new RecursionDepthTracker();
                NanoTimer timer = new NanoTimer();

                long seed = rand.nextLong();
                rand.setSeed(seed);

                if (algo.equalsIgnoreCase("mergesort")) {
                    int[] arr = randomArray(size, rand);
                    MergeSort sorter = new MergeSort(size, comps, moves, depth);
                    timer.start();
                    sorter.sort(arr);
                    timer.stop();
                } else if (algo.equalsIgnoreCase("quicksort")) {
                    int[] arr = randomArray(size, rand);
                    QuickSort sorter = new QuickSort(comps, moves, depth);
                    timer.start();
                    sorter.sort(arr);
                    timer.stop();
                } else if (algo.equalsIgnoreCase("select")) {
                    int[] arr = randomArray(size, rand);
                    DeterministicSelect select = new DeterministicSelect(comps, moves, depth);
                    int k = size / 2;
                    timer.start();
                    select.select(arr, k);
                    timer.stop();
                } else if (algo.equalsIgnoreCase("closest")) {
                    ClosestPair.Point[] pts = randomPoints(size, rand);
                    timer.start();
                    ClosestPair.closestPair(pts);
                    timer.stop();
                } else {
                    throw new IllegalArgumentException("Unknown algorithm: " + algo);
                }

                csv.writeRow(
                        timer.getElapsed(),
                        size,
                        algo,
                        depth.getMax(),
                        comps.get(),
                        moves.get(),
                        seed,
                        ""
                );
            }
        }
    }

    private int[] randomArray(int n, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt();
        return arr;
    }

    private ClosestPair.Point[] randomPoints(int n, Random rand) {
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) pts[i] = new ClosestPair.Point(rand.nextDouble(), rand.nextDouble());
        return pts;
    }
}
