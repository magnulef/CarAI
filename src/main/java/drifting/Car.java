package drifting;

import utils.Keyboard;
import utils.Vec3;
import utils.VisionUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Car {

    private static final double MASS = 800;
    private static final double BREAKING_FORCE = 500;
    private static final double DRAG = 0.4257;
    private static final double ROLLING_RESISTANCE = 12.8;
    private static final double TYRE = 0.6;

    private Vec3 position;
    private Vec3 direction;
    private Vec3 velocity;
    private Vec3 acceleration;

    private Vec3 breakingForce;
    private double engineForce;

    private Vec3 traction;
    private Vec3 dragForce;
    private Vec3 rollingResistanceForce;

    private Vec3 longitudinalForce;

    private boolean isDrifting;
    private Point2D driftLastPoint1;
    private Point2D driftLastPoint2;

    public Car() {
        position = new Vec3();
        direction = new Vec3(0, 1, 0);
        velocity = new Vec3();
        acceleration = new Vec3();
        breakingForce = new Vec3();
        engineForce = 0;
        traction = new Vec3();
        dragForce = new Vec3();
        rollingResistanceForce = new Vec3();
        longitudinalForce = new Vec3();
    }

    public void update() {
        double diff = TYRE * (velocity.getSize() / 30.0);

        if (Keyboard.keydown[37]) {
            direction.rotateZ(Math.toRadians((TYRE) * velocity.getSize()));
        } else if (Keyboard.keydown[39]) {
            direction.rotateZ(Math.toRadians((-TYRE) * velocity.getSize()));
        }

        double diffAngle = velocity.getRelativeAngleBetween(direction);
        if (!Double.isNaN(diffAngle)) {
            double random = Math.random() * 50;
            velocity.rotateZ(diffAngle/((50 + random) * (5 * diff)));

            isDrifting = Math.abs(Math.toDegrees(diffAngle)) > 30;
        }

        if (Keyboard.keydown[38]) {
            engineForce = 300;
        }
        else if (Keyboard.keydown[40]) {
            engineForce = -300;
        }
        else  {
            engineForce = 0;
        }

        if (isDrifting) {
            engineForce = engineForce / 5;
        }

        calculateBraking();
        calculateTraction();
        calculateDrag();
        calculateRollingResistance();
        calculateLongitudinalForce();
        calculateAcceleration();
        calculateVelocity(1);
        calculatePosition(1);
    }

    private void calculateBraking() {
        breakingForce.set(velocity);
        breakingForce.normalize();
        breakingForce.scale(-BREAKING_FORCE);
    }

    private void calculateTraction() {
        traction.set(direction);
        traction.normalize();
        traction.scale(engineForce);
    }

    private void calculateDrag() {
        double speed = velocity.getSize();
        dragForce.set(velocity);
        dragForce.scale(speed);
        dragForce.scale(-DRAG);
    }

    private void calculateRollingResistance() {
        rollingResistanceForce.set(velocity);
        rollingResistanceForce.scale(-ROLLING_RESISTANCE);
    }

    private void calculateLongitudinalForce() {
        boolean isBraking = Keyboard.keydown[66];
        if (isBraking) {
            longitudinalForce.set(breakingForce);
        }
        else {
            longitudinalForce.set(traction);
        }
        longitudinalForce.add(dragForce);
        longitudinalForce.add(rollingResistanceForce);
    }

    private void calculateAcceleration() {
        acceleration.set(longitudinalForce);
        acceleration.scale(1/MASS);
    }

    private void calculateVelocity(double deltaTime) {
        acceleration.scale(deltaTime);
        velocity.add(acceleration);
    }

    private void calculatePosition(double deltaTime) {
        velocity.scale(deltaTime);
        position.add(velocity);
    }

    private void line(Graphics2D g, Graphics2D background) {
        Point point = new Point(1, 1);
        //StaticLine
        background.drawLine(100, 0, 500, 0);

        Point staticStart = new Point(100, 0);
        Point staticEnd = new Point(500, 0);

        Point start = new Point((int)position.x, (int)position.y);

        double angle = Math.atan2(direction.x, direction.y);

        boolean doIntersectFront = VisionUtils.doIntersect(
                start,
                VisionUtils.rotate(angle, 200, position),
                staticStart,
                staticEnd
        );

        Point intersectionPoint = VisionUtils.findIntersection(start, VisionUtils.rotate(angle, 200, position), staticStart, staticEnd);
        double distance = VisionUtils.calculateDistanceBetweenPoints(position.x, position.y, intersectionPoint.x, intersectionPoint.y);

        if (doIntersectFront) {
            System.out.println("FRONT INTERSECTION");
            System.out.println("Distance: " + distance);
            //background.drawLine((int)position.x, (int)position.y, endX, endY);
        } else {
            System.out.println("Distance: " + distance);
        }

        /*boolean doIntersectRightFront = VisionUtils.doIntersect(
                start,
                VisionUtils.rotate(angle + 0.55, 200, position),
                staticStart,
                staticEnd
        );

        if (doIntersectRightFront) {
            System.out.println("RIGHT FRONT INTERSECTION");
            //background.drawLine((int)position.x, (int)position.y, rightFrontEndX, rightFrontEndY);
        }

        boolean doIntersectRight = VisionUtils.doIntersect(
                start,
                VisionUtils.rotate(angle + 1.35, 200, position),
                staticStart,
                staticEnd
        );

        if (doIntersectRight) {
            System.out.println("RIGHT INTERSECTION");
            //background.drawLine((int)position.x, (int)position.y, rightEndX, rightEndY);
        }

        boolean doIntersectLeftFront = VisionUtils.doIntersect(
                start,
                VisionUtils.rotate(angle - 0.55, 200, position),
                staticStart,
                staticEnd
        );

        if (doIntersectLeftFront) {
            System.out.println("LEFT FRONT INTERSECTION");
            //background.drawLine((int)position.x, (int)position.y, leftFrontEndX, leftFrontEndY);
        }

        boolean doIntersectLeft = VisionUtils.doIntersect(
                start,
                VisionUtils.rotate(angle - 1.35, 200, position),
                staticStart,
                staticEnd
        );

        if (doIntersectLeft) {
            System.out.println("LEFT INTERSECTION");
            //background.drawLine((int)position.x, (int)position.y, leftEndX, leftEndY);
        }*/

        //Front
        g.drawLine((int)position.x, (int)position.y, (int)position.x, (int)position.y + 200);
        //Right front
        //g.drawLine((int)position.x, (int)position.y, (int)position.x + 100, (int)position.y + 170);
        //Right
        //g.drawLine((int)position.x, (int)position.y, (int)position.x + 200, (int)position.y + 50);
        //Left front
        //g.drawLine((int)position.x, (int)position.y, (int)position.x - 100, (int)position.y + 170);
        //Left
        //g.drawLine((int)position.x, (int)position.y, (int)position.x - 200, (int)position.y + 50);
    }

    public void draw(Graphics2D g, Graphics2D background) {
        double angle = -Math.atan2(direction.x, direction.y);
        g.rotate(angle, position.x, position.y + 0);
        g.setColor(Color.BLACK);
        g.fillRect((int) (position.x-20), (int) (position.y-30), 40, 60);

        line(g, background);

        if (isDrifting) {
            AffineTransform transformation = new AffineTransform();
            transformation.rotate(angle, position.x, position.y + 0);

            background.setColor(new Color(0, 0, 0, 16));
            BasicStroke basicStroke = new BasicStroke(10);
            background.setStroke(basicStroke);

            if (driftLastPoint1!=null && driftLastPoint2!=null) {
                Point2D p1 = new Point2D.Double((int) (position.x-17), (int) (position.y-30));
                Point2D p2 = new Point2D.Double((int) (position.x+17), (int) (position.y-30));

                transformation.transform(p1, p1);
                transformation.transform(p2, p2);

                background.drawLine((int) p1.getX(), (int) p1.getY(), (int) driftLastPoint1.getX(), (int) driftLastPoint1.getY());
                background.drawLine((int) p2.getX(), (int) p2.getY(), (int) driftLastPoint2.getX(), (int) driftLastPoint2.getY());
            }

            driftLastPoint1 = new Point2D.Double((int) (position.x-17), (int) (position.y-30));
            driftLastPoint2 = new Point2D.Double((int) (position.x+17), (int) (position.y-30));
            transformation.transform(driftLastPoint1, driftLastPoint1);
            transformation.transform(driftLastPoint2, driftLastPoint2);
        }
        else {
            driftLastPoint1 = null;
            driftLastPoint2 = null;
        }
    }
}
