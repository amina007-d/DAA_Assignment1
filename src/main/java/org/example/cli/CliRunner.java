package org.example.cli;

import org.example.algorithms.sort.MergeSort;
import org.example.algorithms.sort.QuickSort;
import org.example.algorithms.select.DeterministicSelect;
import org.example.algorithms.divideandconquer.ClosestPair;
import org.example.metrics.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class CliRunner {
    public static void main(String[] args) {
        String algo = null;
        int size = 0;
        int trials = 1;
        String output = "results.csv";
        long seed = System.currentTimeMillis();

        // --- разбор аргументов ---
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--algo" -> algo = args[++i];
                case "--size" -> size = Integer.parseInt(args[++i]);
                case "--trials" -> trials = Integer.parseInt(args[++i]);
                case "--output" -> output = args[++i];
                case "--seed" -> seed = Long.parseLong(args[++i]);
            }
        }

        if (algo == null || size <= 0) {
            System.out.println("Usage: --algo <mergesort|quicksort|select|closest> --size N [--trials T] [--output file.csv] [--seed S]");
            System.exit(1);
        }

        Random rnd = new Random(seed);

        try (CSVWriter writer = new CSVWriter(output)) {
            writer.writeHeader();

            for (int t = 0; t < trials; t++) {
                ComparisonCounter comps = new ComparisonCounter();
                MoveCounter moves = new MoveCounter();
                RecursionDepthTracker depth = new RecursionDepthTracker();
                NanoTimer timer = new NanoTimer();

                if (algo.equals("mergesort")) {
                    int[] arr = rnd.ints(size, 0, 1_000_000).toArray();
                    MergeSort sort = new MergeSort(16, comps, moves, depth);
                    sort.sort(arr);
                    timer.start();
                    sort.sort(arr);
                    timer.stop();

                } else if (algo.equals("quicksort")) {
                    int[] arr = rnd.ints(size, 0, 1_000_000).toArray();
                    QuickSort sort = new QuickSort(comps, moves, depth);
                    timer.start();
                    sort.sort(arr);
                    timer.stop();

                } else if (algo.equals("select")) {
                    int[] arr = rnd.ints(size, 0, 1_000_000).toArray();
                    int k = size / 2;
                    DeterministicSelect select = new DeterministicSelect(comps, moves, depth);
                    timer.start();
                    select.select(arr, k);
                    timer.stop();

                } else if (algo.equals("closest")) {
                    ClosestPair.Point[] pts = new ClosestPair.Point[size];
                    for (int i = 0; i < size; i++) {
                        pts[i] = new ClosestPair.Point(rnd.nextDouble(), rnd.nextDouble());
                    }
                    ClosestPair cp = new ClosestPair(comps, moves, depth);
                    timer.start();
                    cp.find(pts);
                    timer.stop();

                } else {
                    System.out.println("Unknown algorithm: " + algo);
                    System.exit(1);
                }

                writer.writeRow(
                        timer.getElapsed(),
                        size,
                        algo,
                        depth.getMax(),
                        comps.get(),
                        moves.get(),
                        seed,
                        "trial=" + (t + 1)
                );

                System.out.printf("Trial %d/%d finished: %s (n=%d)%n", t + 1, trials, algo, size);
            }

            System.out.println("Results saved to: " + Paths.get(output).toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
