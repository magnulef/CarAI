package game.renderables;

import game.valueobjects.Line;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class RewardGates extends GameObject {

    private static final List<Line> LINES = new ArrayList<>();

    static {
        LINES.add(new Line(0,15, 150, 175, 150));
        LINES.add(new Line(0,15, 300, 175, 300));
        LINES.add(new Line(0,15, 500, 175, 500));

        LINES.add(new Line(0,185, 500, 345, 500));
        LINES.add(new Line(0,185, 400, 345, 400));

        LINES.add(new Line(0,355, 400, 495, 400));
        LINES.add(new Line(0,355, 525, 495, 525));
        LINES.add(new Line(0,355, 650, 495, 650));

        LINES.add(new Line(0,350, 655, 350, 795));
        LINES.add(new Line(0,180, 655, 180, 795));

        LINES.add(new Line(0,500, 805, 500, 965));
        LINES.add(new Line(0,350, 805, 350, 965));
        LINES.add(new Line(0,180, 805, 180, 965));

        LINES.add(new Line(0,505, 800, 695, 800));
        LINES.add(new Line(0,505, 650, 695, 650));
        LINES.add(new Line(0,505, 525, 695, 525));
        LINES.add(new Line(0,505, 400, 695, 400));

        LINES.add(new Line(0,705, 800, 845, 800));
        LINES.add(new Line(0,705, 650, 845, 650));
        LINES.add(new Line(0,705, 525, 845, 525));
        LINES.add(new Line(0,705, 400, 845, 400));

        LINES.add(new Line(0,855, 800, 985, 800));
        LINES.add(new Line(0,855, 650, 985, 650));
        LINES.add(new Line(0,855, 525, 985, 525));
        LINES.add(new Line(0,855, 400, 985, 400));
        LINES.add(new Line(0,855, 275, 985, 275));
        LINES.add(new Line(0,855, 150, 985, 150));

        LINES.add(new Line(0,180, 15, 180, 145));
        LINES.add(new Line(0,350, 15, 350, 245));
        LINES.add(new Line(0,500, 185, 500, 245));
        LINES.add(new Line(0,675, 15, 675, 245));
    }

    private boolean shouldRender;

    public RewardGates(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        if (!shouldRender || LINES.isEmpty()) {
            return;
        }

        graphics.setColor(Color.GREEN);
        for (Line line : LINES) {
            graphics.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
    }

    public static List<Line> getLines() {
        return LINES;
    }
}
