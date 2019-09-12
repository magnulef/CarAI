package game.renderables.car;

import game.renderables.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class VisionLine extends GameObject {

    private final Point start;
    private final Point end;
    private final Point interception;

    public VisionLine(Point start, Point end, Point interception) {
        this.start = start;
        this.end = end;
        this.interception = interception;
    }

    @Override
    public void render(Graphics graphics) {
        if (interception != null) {
            graphics.setColor(Color.RED);
            graphics.drawLine(start.x, start.y, interception.x, interception.y);
        } else {
            graphics.setColor(Color.WHITE);
            graphics.drawLine(start.x, start.y, end.x, end.y);
        }
    }
}
