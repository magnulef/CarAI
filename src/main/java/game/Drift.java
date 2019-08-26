package game;

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

    @Override
    public void tick() {
        if (alpha > life) {
            alpha -= (life - 0.0001);
        } else {
            handler.removeGameObject(this);
        }
    }

    @Override
    public void render(Graphics graphics) {
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
