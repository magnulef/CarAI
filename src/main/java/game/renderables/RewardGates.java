package game.renderables;

import game.valueobjects.Line;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class RewardGates extends GameObject {

    private static final List<Line> LINES = new ArrayList<>();

    static {
        LINES.add(new Line(0,15, 300, 175, 300));
        LINES.add(new Line(1,15, 500, 175, 500));

        LINES.add(new Line(2,185, 500, 345, 500));
        LINES.add(new Line(3,185, 400, 345, 400));

        LINES.add(new Line(4,355, 400, 495, 400));
        LINES.add(new Line(5,355, 525, 495, 525));
        LINES.add(new Line(6,355, 650, 495, 650));

        LINES.add(new Line(7,350, 655, 350, 795));
        LINES.add(new Line(8,180, 655, 180, 795));

        LINES.add(new Line(9,180, 805, 180, 965));
        LINES.add(new Line(10,350, 805, 350, 965));
        LINES.add(new Line(11,500, 805, 500, 965));

        LINES.add(new Line(12,505, 800, 695, 800));
        LINES.add(new Line(13,505, 650, 695, 650));
        LINES.add(new Line(14,505, 525, 695, 525));
        LINES.add(new Line(15,505, 400, 695, 400));

        LINES.add(new Line(16,705, 400, 845, 400));
        LINES.add(new Line(17,705, 525, 845, 525));
        LINES.add(new Line(18,705, 650, 845, 650));
        LINES.add(new Line(19,705, 800, 845, 800));

        LINES.add(new Line(20,855, 800, 985, 800));
        LINES.add(new Line(21,855, 650, 985, 650));
        LINES.add(new Line(22,855, 525, 985, 525));
        LINES.add(new Line(23,855, 400, 985, 400));
        LINES.add(new Line(24,855, 275, 985, 275));
        LINES.add(new Line(25,855, 150, 985, 150));

        LINES.add(new Line(26,675, 15, 675, 245));
        LINES.add(new Line(27,500, 185, 500, 245));
        LINES.add(new Line(28,350, 15, 350, 245));
        LINES.add(new Line(29,180, 15, 180, 145));

        LINES.add(new Line(30,15, 150, 175, 150));
    }

    private boolean shouldRender;

    public RewardGates(boolean shouldRender) {
        this.shouldRender = shouldRender;
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
