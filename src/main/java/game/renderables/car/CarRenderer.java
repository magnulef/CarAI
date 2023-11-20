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
    private GameObject FRONTADD20 = null;
    private GameObject FRONTMINUS20 = null;
    private GameObject FRONTADD40 = null;
    private GameObject FRONTMINUS40 = null;

    private GameObject FRONTADD60 = null;
    private GameObject FRONTMINUS60 = null;
    private GameObject FRONTADD80 = null;
    private GameObject FRONTMINUS80 = null;

    private GameObject FRONTADD100 = null;
    private GameObject FRONTMINUS100 = null;
    private GameObject FRONTADD120 = null;
    private GameObject FRONTMINUS120 = null;

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
        FRONTADD20 = addVisionLine(handler, angle, 0.20, start, 200, FRONTADD20);
        handler.addGameObject(FRONTADD20);
        FRONTMINUS20 = addVisionLine(handler, angle, -0.20, start, 200, FRONTMINUS20);
        handler.addGameObject(FRONTMINUS20);
        FRONTADD40 = addVisionLine(handler, angle, 0.40, start, 200, FRONTADD40);
        handler.addGameObject(FRONTADD40);
        FRONTMINUS40 = addVisionLine(handler, angle, -0.40, start, 200, FRONTMINUS40);
        handler.addGameObject(FRONTMINUS40);

        FRONTADD60 = addVisionLine(handler, angle, -0.60, start, 200, FRONTADD60);
        handler.addGameObject(FRONTADD60);

        FRONTMINUS60 = addVisionLine(handler, angle, 0.60, start, 200, FRONTMINUS60);
        handler.addGameObject(FRONTMINUS60);

        FRONTADD80 = addVisionLine(handler, angle, 0.80, start, 200, FRONTADD80);
        handler.addGameObject(FRONTADD80);

        FRONTMINUS80 = addVisionLine(handler, angle, -0.80, start, 200, FRONTMINUS80);
        handler.addGameObject(FRONTMINUS80);

        FRONTADD100 = addVisionLine(handler, angle, -1.0, start, 200, FRONTADD100);
        handler.addGameObject(FRONTADD100);

        FRONTMINUS100 = addVisionLine(handler, angle, 1.0, start, 200, FRONTMINUS100);
        handler.addGameObject(FRONTMINUS100);

        FRONTADD120 = addVisionLine(handler, angle, 1.20, start, 200, FRONTADD120);
        handler.addGameObject(FRONTADD120);

        FRONTMINUS120 = addVisionLine(handler, angle, -1.20, start, 200, FRONTMINUS120);
        handler.addGameObject(FRONTMINUS120);
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
