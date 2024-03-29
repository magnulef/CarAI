package game.renderables.car;

import game.Handler;
import game.renderables.GameObject;
import game.renderables.Track;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import utils.Vec3;
import utils.VisionUtils;

public class CarRenderer {

    private final boolean shouldRender;
    private final boolean renderLines;

    private GameObject FRONT = null;
    private GameObject RIGHTFRONT = null;
    private GameObject RIGHT = null;
    private GameObject LEFTFRONT = null;
    private GameObject LEFT = null;

    private GameObject LEFT_CENTER_BACK = null;
    private GameObject RIGHT_CENTER_BACK = null;
    private GameObject LEFTBACK = null;
    private GameObject RIGHTBACK = null;

    public CarRenderer(boolean shouldRender, boolean renderLines) {
        this.shouldRender = shouldRender;
        this.renderLines = renderLines;
    }

    public void renderCar(Graphics graphics, Vec3 direction, Vec3 position, int width, int height, Color color) {
        if (!shouldRender) {
            return;
        }

        Graphics2D graphics2D = (Graphics2D) graphics.create();

        double angle = -Math.atan2(direction.x, direction.y);

        graphics2D.rotate(angle, position.x, position.y + 0);
        graphics2D.setColor(color);

        graphics2D.fillRect((int) (position.x-(width/2)), (int) (position.y-(height/2)), width, height);
    }

    public void addDrift(boolean isDrifting, Handler handler, Vec3 position, int width) {
        if (!shouldRender) {
            return;
        }

        if (isDrifting) {
            handler.addGameObject(new Drift(handler, (int) position.x, (int) position.y, width/3, width/6, width/6));
        }
    }

    public void addVisionLines(Handler handler, Vec3 direction, Vec3 position) {
        if (!shouldRender || !renderLines) {
            return;
        }

        double angle = Math.atan2(direction.x, direction.y);
        Point start = new Point((int) position.x, (int) position.y);

        FRONT = addVisionLine(handler, angle, 0, start, 200, FRONT);
        handler.addGameObject(FRONT);
        RIGHTFRONT = addVisionLine(handler, angle, 0.55, start, 200, RIGHTFRONT);
        handler.addGameObject(RIGHTFRONT);
        RIGHT = addVisionLine(handler, angle, 1.35, start, 200, RIGHT);
        handler.addGameObject(RIGHT);
        LEFTFRONT = addVisionLine(handler, angle, -0.55, start, 200, LEFTFRONT);
        handler.addGameObject(LEFTFRONT);
        LEFT = addVisionLine(handler, angle, -1.35, start, 200, LEFT);
        handler.addGameObject(LEFT);

        LEFT_CENTER_BACK = addVisionLine(handler, angle, -2.15, start, 200, LEFT_CENTER_BACK);
        handler.addGameObject(LEFT_CENTER_BACK);

        RIGHT_CENTER_BACK = addVisionLine(handler, angle, 2.15, start, 200, RIGHT_CENTER_BACK);
        handler.addGameObject(RIGHT_CENTER_BACK);

        RIGHTBACK = addVisionLine(handler, angle, 2.75, start, 200, RIGHTBACK);
        handler.addGameObject(RIGHTBACK);

        LEFTBACK = addVisionLine(handler, angle, -2.75, start, 200, LEFTBACK);
        handler.addGameObject(LEFTBACK);
    }

    private GameObject addVisionLine(Handler handler, double angle, double angleOffset, Point start, int length, GameObject gameObject) {
        if (gameObject != null) {
            handler.removeGameObject(gameObject);
        }

        Point rotatedEndPoint = VisionUtils.rotate(angle + angleOffset, length, start);

        Point intersectionPoint = VisionUtils.doIntersect(start, rotatedEndPoint, Track.getLines());
        if (intersectionPoint != null) {
            return new VisionLine(start, null, intersectionPoint);
        }

        return new VisionLine(start, rotatedEndPoint, null);
    }
}
