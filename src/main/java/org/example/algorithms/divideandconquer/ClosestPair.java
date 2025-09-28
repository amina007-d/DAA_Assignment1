package org.example.algorithms.divideandconquer;

import org.example.metrics.ComparisonCounter;
import org.example.metrics.MoveCounter;
import org.example.metrics.RecursionDepthTracker;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public static class Point {
        public final double x, y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private final ComparisonCounter comps;
    private final MoveCounter moves;
    private final RecursionDepthTracker depth;

    public ClosestPair(ComparisonCounter comps,
                       MoveCounter moves,
                       RecursionDepthTracker depth) {
        this.comps = comps;
        this.moves = moves;
        this.depth = depth;
    }

    public double find(Point[] points) {
        Point[] pts = Arrays.copyOf(points, points.length);
        Arrays.sort(pts, Comparator.comparingDouble(p -> p.x));
        depth.enter();
        double result = closestRecursive(pts);
        depth.exit();
        return result;
    }

    private double closestRecursive(Point[] pts) {
        int n = pts.length;
        if (n <= 3) {
            double min = Double.POSITIVE_INFINITY;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    comps.increment();
                    min = Math.min(min, distance(pts[i], pts[j]));
                }
            }
            return min;
        }

        int mid = n / 2;
        Point midPoint = pts[mid];

        depth.enter();
        double dLeft = closestRecursive(Arrays.copyOfRange(pts, 0, mid));
        double dRight = closestRecursive(Arrays.copyOfRange(pts, mid, n));
        depth.exit();

        double d = Math.min(dLeft, dRight);

        // вместо lambda используем обычный цикл
        Point[] strip = new Point[pts.length];
        int count = 0;
        for (Point p : pts) {
            comps.increment();
            if (Math.abs(p.x - midPoint.x) < d) {
                strip[count++] = p;
            }
        }
        strip = Arrays.copyOf(strip, count);
        Arrays.sort(strip, Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < d; j++) {
                comps.increment();
                d = Math.min(d, distance(strip[i], strip[j]));
            }
        }

        return d;
    }

    private double distance(Point p1, Point p2) {
        moves.add(2);
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
