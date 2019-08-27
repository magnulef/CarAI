package game.valueobjects;

import java.awt.Point;

public class Line {

    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;

    public Line(
        int startX,
        int startY,
        int endX,
        int endY
    ) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public Point getStart() {
        return new Point(startX, startY);
    }

    public Point getEnd() {
        return new Point(endX, endY);
    }
}
