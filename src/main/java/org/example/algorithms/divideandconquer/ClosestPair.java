package org.example.algorithms.divideandconquer;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double closestPair(Point[] points) {
        Point[] sortedByX = points.clone();
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));
        return closestRecursive(sortedByX);
    }

    private static double closestRecursive(Point[] points) {
        int n = points.length;
        if (n <= 3) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < n; i++)
                for (int j = i + 1; j < n; j++)
                    min = Math.min(min, distance(points[i], points[j]));
            return min;
        }

        int mid = n / 2;
        Point midPoint = points[mid];

        double dLeft = closestRecursive(Arrays.copyOfRange(points, 0, mid));
        double dRight = closestRecursive(Arrays.copyOfRange(points, mid, n));
        double d = Math.min(dLeft, dRight);

        final double delta = d;
        final double midX = midPoint.x;

        Point[] strip = Arrays.stream(points)
                .filter(p -> Math.abs(p.x - midX) < delta)
                .sorted(Comparator.comparingDouble(p -> p.y))
                .toArray(Point[]::new);

        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < d; j++) {
                d = Math.min(d, distance(strip[i], strip[j]));
            }
        }
        return d;
    }
}
