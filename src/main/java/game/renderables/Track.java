package game.renderables;

import game.valueobjects.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Track extends GameObject {

    private static final List<Line> LINES = new ArrayList<>();

    static {
        //Edge box
        LINES.add(new Line(0,10, 10, 990, 10));
        LINES.add(new Line(0,990, 10, 990, 970));
        LINES.add(new Line(0,10, 970, 990, 970));
        LINES.add(new Line(0,10, 10, 10, 970));

        //Track
        LINES.add(new Line(0,180, 150, 180, 500));
        LINES.add(new Line(0,10, 650, 350, 650));
        LINES.add(new Line(0,350, 650, 350, 400));
        LINES.add(new Line(0,180, 250, 500, 250));
        LINES.add(new Line(0,500, 250, 500, 800));
        LINES.add(new Line(0,500, 800, 180, 800));
        LINES.add(new Line(0,700, 970, 700, 400));
        LINES.add(new Line(0,500, 250, 850, 250));
        LINES.add(new Line(0,850, 150, 850, 800));
        LINES.add(new Line(0,500, 10, 500, 180));
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
