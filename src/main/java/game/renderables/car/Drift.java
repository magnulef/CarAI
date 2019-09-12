package game.renderables.car;

import game.Handler;

import game.renderables.GameObject;
import java.awt.*;

public class Drift extends GameObject {

    private final int x;
    private final int y;
    private float alpha = 0.8f;
    private float life = 0.003f;
    private final Handler handler;
    private final int offset;
    private final int width;
    private final int height;

    public Drift(Handler handler, int x, int y, int offset, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.offset = offset;
        this.width = width;
        this.height = height;
    }

    private int timeToLive = 5000;

    private void reduce() {
        if (alpha < 0.0008f) {
            alpha = 0;
            return;
        }

        alpha = alpha - 0.0005f;
    }

    @Override
    public void render(Graphics graphics) {
        if (timeToLive > 0 && alpha != 0) {
            timeToLive = timeToLive - 1;
            reduce();
        } else {
            handler.removeGameObject(this);
            return;
        }

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setComposite(makeTransparent(alpha));

        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(x + offset - (width/2), y, width, height);
        graphics2D.fillRect(x - offset - (width/2), y, width, height);

        graphics2D.setComposite(makeTransparent(1));
    }

    private AlphaComposite makeTransparent(float alpha) {
        return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    }
}
