package org.example;

import org.example.cli.CliRunner;

public class Main {
    public static void main(String[] args) throws Exception {
        String algo = null;
        int size = 1000;
        int trials = 1;
        String output = "results.csv";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--algo":
                    algo = args[++i];
                    break;
                case "--size":
                    size = Integer.parseInt(args[++i]);
                    break;
                case "--trials":
                    trials = Integer.parseInt(args[++i]);
                    break;
                case "--output":
                    output = args[++i];
                    break;
            }
        }

        if (algo == null) {
            System.err.println("Usage: --algo <mergesort|quicksort|select|closest> [--size N] [--trials T] [--output file.csv]");
            System.exit(1);
        }

        CliRunner runner = new CliRunner(algo, size, trials, output);
        runner.run();
    }
}
