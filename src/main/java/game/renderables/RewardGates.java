package game.renderables;

import game.valueobjects.Line;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class RewardGates extends GameObject {

    private static final List<Line> LINES = new ArrayList<>();

    static {
        LINES.add(new Line(0,15, 280, 175, 280));
        LINES.add(new Line(1,15, 410, 175, 410));
        LINES.add(new Line(2,15, 500, 175, 500));

        LINES.add(new Line(3,180, 505, 180, 645));

        LINES.add(new Line(4,185, 500, 345, 500));
        LINES.add(new Line(5,185, 400, 345, 400));

        LINES.add(new Line(6,350, 395, 350, 255));

        LINES.add(new Line(7,355, 400, 495, 400));
        LINES.add(new Line(8,355, 525, 495, 525));
        LINES.add(new Line(9,355, 650, 495, 650));

        LINES.add(new Line(10,350, 655, 350, 795));
        LINES.add(new Line(11,180, 655, 180, 795));

        LINES.add(new Line(12,15, 800, 175, 800));

        LINES.add(new Line(13,180, 805, 180, 965));
        LINES.add(new Line(14,350, 805, 350, 965));
        LINES.add(new Line(15,500, 805, 500, 965));

        LINES.add(new Line(16,505, 800, 695, 800));
        LINES.add(new Line(17,505, 650, 695, 650));
        LINES.add(new Line(18,505, 525, 695, 525));
        LINES.add(new Line(19,505, 400, 695, 400));

        LINES.add(new Line(20,705, 400, 845, 400));
        LINES.add(new Line(21,705, 525, 845, 525));
        LINES.add(new Line(22,705, 650, 845, 650));
        LINES.add(new Line(23,705, 800, 845, 800));

        LINES.add(new Line(24,855, 800, 985, 800));
        LINES.add(new Line(25,855, 650, 985, 650));
        LINES.add(new Line(26,855, 525, 985, 525));
        LINES.add(new Line(27,855, 400, 985, 400));
        LINES.add(new Line(28,855, 275, 985, 275));
        LINES.add(new Line(29,855, 150, 985, 150));

        LINES.add(new Line(30,675, 15, 675, 245));
        LINES.add(new Line(31,500, 185, 500, 245));
        LINES.add(new Line(32,350, 15, 350, 245));
        LINES.add(new Line(33,180, 15, 180, 145));

        LINES.add(new Line(34,15, 150, 175, 150));
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
