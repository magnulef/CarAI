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
        LINES.add(new Line(1,15, 410, 120, 410));
        LINES.add(new Line(2,15, 500, 80, 500));

        LINES.add(new Line(3,130, 580, 130, 645));
        LINES.add(new Line(4,180, 580, 180, 645));

        LINES.add(new Line(5,220, 500, 280, 500));
        LINES.add(new Line(6,200, 400, 280, 400));

        LINES.add(new Line(7,350, 320, 350, 255));

        LINES.add(new Line(8,430, 400, 495, 400));
        LINES.add(new Line(9,400, 525, 495, 525));
        LINES.add(new Line(10,380, 650, 450, 650));

        LINES.add(new Line(11,350, 660, 350, 720));
        LINES.add(new Line(12,180, 680, 180, 735));

        LINES.add(new Line(13,120, 750, 60, 700));

        LINES.add(new Line(14,15, 800, 100, 800));

        LINES.add(new Line(15,60, 930, 120, 870));

        LINES.add(new Line(16,180, 880, 180, 965));
        LINES.add(new Line(17,350, 900, 350, 965));
        LINES.add(new Line(18,500, 825, 500, 890)); //

        LINES.add(new Line(19,505, 800, 695, 800));
        LINES.add(new Line(20,505, 650, 695, 650));
        LINES.add(new Line(21,505, 525, 695, 525));
        LINES.add(new Line(22,505, 400, 695, 400));

        LINES.add(new Line(23, 700, 255, 700, 395));

        LINES.add(new Line(24,705, 400, 845, 400));
        LINES.add(new Line(25,705, 525, 845, 525));
        LINES.add(new Line(26,705, 650, 845, 650));
        LINES.add(new Line(27,705, 800, 845, 800));

        LINES.add(new Line(28, 850, 805, 850, 965));

        LINES.add(new Line(29,855, 800, 985, 800));
        LINES.add(new Line(30,855, 650, 985, 650));
        LINES.add(new Line(31,855, 525, 985, 525));
        LINES.add(new Line(32,855, 400, 985, 400));
        LINES.add(new Line(33,855, 275, 985, 275));
        LINES.add(new Line(34,855, 150, 985, 150));

        LINES.add(new Line(35, 850, 15, 850, 145));
        LINES.add(new Line(36,675, 15, 675, 245));
        LINES.add(new Line(37,500, 185, 500, 245));
        LINES.add(new Line(38,350, 15, 350, 245));
        LINES.add(new Line(39,180, 15, 180, 145));

        LINES.add(new Line(40,15, 150, 175, 150));
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
