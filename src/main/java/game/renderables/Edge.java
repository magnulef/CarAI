package game.renderables;

import java.awt.*;

public class Edge extends GameObject {

    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;

    public Edge(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.drawLine(startX, startY, endX, endY);
    }
}
