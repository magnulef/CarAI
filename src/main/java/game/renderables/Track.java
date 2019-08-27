package game.renderables;

import game.valueobjects.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Track extends GameObject {

    private static final List<Line> LINES = new ArrayList<>();

    public Track() {
        //Edge box
        LINES.add(new Line(10, 10, 990, 10));
        LINES.add(new Line(990, 10, 990, 970));
        LINES.add(new Line(10, 970, 990, 970));
        LINES.add(new Line(10, 10, 10, 970));
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        if (LINES.isEmpty()) {
            return;
        }

        graphics.setColor(Color.WHITE);
        for (Line line : LINES) {
            graphics.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
    }

    public static List<Line> getLines() {
        return LINES;
    }
}
