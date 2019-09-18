package utils;

import game.valueobjects.Line;
import java.awt.*;
import java.util.List;

public class VisionUtils {

    /*public static Point doIntersect(Point start, Point end, List<Line> lines) {
        for (Line line : lines) {

            Point lineStart = line.getStart();
            Point lineEnd = line.getEnd();
            boolean doesIntersect = VisionUtils.doIntersect(
                start,
                end,
                line.getStart(),
                line.getEnd()
            );

            if (doesIntersect) {
                return VisionUtils.findIntersection(start, end, lineStart, lineEnd);
            }
        }

        return null;
    }
*/
    public static Point doIntersect(Point start, Point end, List<Line> lines) {
        for (Line line : lines) {

            Point intersection = doIntersect(start, end, line);
            if (intersection != null) {
                return intersection;
            }
        }

        return null;
    }

    public static Point doIntersect(Point start, Point end, Line line) {
        Point lineStart = line.getStart();
        Point lineEnd = line.getEnd();
        boolean doesIntersect = VisionUtils.doIntersect(
            start,
            end,
            line.getStart(),
            line.getEnd()
        );

        if (doesIntersect) {
            return VisionUtils.findIntersection(start, end, lineStart, lineEnd);
        }

        return null;
    }

    public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }

    public static Point rotate(double angle, int length, Point start) {
        return rotate(angle, length, start.x, start.y);
    }

    public static Point rotate(double angle, int length, Vec3 start) {
        return rotate(angle, length, start.x, start.y);
    }

    public static Point rotate(double angle, int length, double x, double y) {
        int frontEndX = (int) (x + length * Math.sin(angle));
        int frontEndY = (int) (y + length * Math.cos(angle));
        return new Point(frontEndX, frontEndY);
    }

    public static Point findIntersection(Point line1Start, Point line1End, Point line2Start, Point line2End) {
        double a1 = line1End.y - line1Start.y;
        double b1 = line1Start.x - line1End.x;
        double c1 = a1 * line1Start.x + b1 * line1Start.y;

        double a2 = line2End.y - line2Start.y;
        double b2 = line2Start.x - line2End.x;
        double c2 = a2 * line2Start.x + b2 * line2Start.y;

        double delta = a1 * b2 - a2 * b1;
        return new Point((int)((b2 * c1 - b1 * c2) / delta), (int) ((a1 * c2 - a2 * c1) / delta));
    }

    public static Point calculateIntersectionPoint(
            double m1,
            double b1,
            double m2,
            double b2
    ) {
        if (m1 == m2) {
            return null;
        }

        double x = (b2 - b1) / (m1 - m2);
        double y = m1 * x + b1;

        Point point = new Point();
        point.setLocation(x, y);
        return point;
    }

    public static double calculateDistanceBetweenPoints(
        Point start,
        Point end
    ) {
        return calculateDistanceBetweenPoints(start.x, start.y, end.x, end.y);
    }

    public static double calculateDistanceBetweenPoints(
            double x1,
            double y1,
            double x2,
            double y2
    ) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    private static int orientation(Point p, Point q, Point r) {
        int val = (int)((q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY()));

        if (val == 0) return 0;

        return (val > 0)? 1: 2;
    }

    private static boolean onSegment(Point p, Point q, Point r) {
        if (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY()))
            return true;

        return false;
    }
}
