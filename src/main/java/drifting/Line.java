package drifting;

import java.awt.*;

public class Line {

    private final Point start;
    private final Point end;

    public Line(
        Point start,
        Point end
    ) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public double getStartX() {
        return start.x;
    }

    public double getStartY() {
        return start.y;
    }

    public Point getEnd() {
        return end;
    }

    public double getEndX() {
        return end.x;
    }

    public double getEndY() {
        return end.y;
    }
}
