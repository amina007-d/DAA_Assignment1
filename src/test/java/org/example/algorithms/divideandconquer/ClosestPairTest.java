package org.example.algorithms.divideandconquer;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClosestPairTest {

    private static double bruteForce(ClosestPair.Point[] points) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                min = Math.min(min, ClosestPair.distance(points[i], points[j]));
            }
        }
        return min;
    }

    @Test
    void testSmallArray() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(3, 4),
                new ClosestPair.Point(7, 7),
                new ClosestPair.Point(1, 1)
        };
        double result = ClosestPair.closestPair(points);
        assertEquals(Math.sqrt(2), result, 1e-6);
    }

    @Test
    void testBruteForceValidation() {
        Random rand = new Random(42);
        for (int n = 50; n <= 500; n += 50) {
            ClosestPair.Point[] points = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new ClosestPair.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
            }
            double fast = ClosestPair.closestPair(points);
            double brute = bruteForce(points);
            assertEquals(brute, fast, 1e-6);
        }
    }
}
