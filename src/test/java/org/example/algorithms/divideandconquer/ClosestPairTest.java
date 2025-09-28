package org.example.algorithms.divideandconquer;

import org.example.metrics.ComparisonCounter;
import org.example.metrics.MoveCounter;
import org.example.metrics.RecursionDepthTracker;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClosestPairTest {

    private double bruteForce(ClosestPair.Point[] points) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dx = points[i].x - points[j].x;
                double dy = points[i].y - points[j].y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                min = Math.min(min, dist);
            }
        }
        return min;
    }

    @Test
    void testSmallRandomInstances() {
        Random rnd = new Random(42);
        for (int n = 2; n <= 200; n += 20) {
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rnd.nextDouble(), rnd.nextDouble());
            }

            ClosestPair algo = new ClosestPair(
                    new ComparisonCounter(),
                    new MoveCounter(),
                    new RecursionDepthTracker()
            );

            double fast = algo.find(pts);
            double slow = bruteForce(pts);

            assertEquals(slow, fast, 1e-9);
        }
    }

    @Test
    void testLargerInstanceRuns() {
        Random rnd = new Random(123);
        int n = 5000;
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rnd.nextDouble(), rnd.nextDouble());
        }

        ClosestPair algo = new ClosestPair(
                new ComparisonCounter(),
                new MoveCounter(),
                new RecursionDepthTracker()
        );

        double d = algo.find(pts);
        System.out.println("Closest distance for n=" + n + " : " + d);
    }
}
