package game.renderables;

import java.awt.*;

public abstract class GameObject {

    public abstract void tick();
    public abstract void render(Graphics graphics);
}
