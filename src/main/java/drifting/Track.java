package drifting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Track {

    private final List<Line> lines;

    public Track() {
        lines = new ArrayList<>();
        lines.add(new Line(new Point(-900, -600), new Point(-900, 100)));
        lines.add(new Line(new Point(-900, 100), new Point(-800, 200)));
        lines.add(new Line(new Point(-800, 200), new Point(-900, 300)));
        lines.add(new Line(new Point(-900, 300), new Point(-900, 600)));
        lines.add(new Line(new Point(-900, 600), new Point(900, 600)));
        lines.add(new Line(new Point(900, 600), new Point(900, -600)));
        lines.add(new Line(new Point(900, -600), new Point(-900, -600)));
    }

    public List<Line> getLines() {
        return lines;
    }

    public void draw(Graphics2D background) {
        for (Line line : lines) {
            background.drawLine((int)line.getStartX(), (int)line.getStartY(), (int)line.getEndX(), (int)line.getEndY());
        }
    }
}
