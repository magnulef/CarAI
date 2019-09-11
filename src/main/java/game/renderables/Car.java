package game.renderables;

import game.Handler;
import game.ai.Actions;
import game.ai.NeuralNetwork;
import game.valueobjects.InputContract;
import game.valueobjects.Line;
import game.valueobjects.VisionContract;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import java.util.Map;
import org.nd4j.linalg.api.ndarray.INDArray;
import utils.Keyboard;
import utils.Vec3;
import utils.VisionInput;
import utils.VisionUtils;

public class Car extends GameObject {

    private static final double MASS = 800;
    private static final double BREAKING_FORCE = 500; //500
    private static final double DRAG = 0.4257;
    private static final double ROLLING_RESISTANCE = 12.8;
    private static final double TYRE = 0.6; //0.6
    private static final int WIDTH = 20;
    private static final int HEIGHT = 40;

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

    private final Handler handler;
    private final NeuralNetwork neuralNetwork;
    private final boolean keyBoardEnabled;

    public Car(Handler handler, Map<String, INDArray> previousWeights, boolean keyBoardEnabled) {
        position = new Vec3(100, 200, 0);
        direction = new Vec3(0, 1, 0);
        velocity = new Vec3();
        acceleration = new Vec3();
        breakingForce = new Vec3();
        engineForce = 0;
        traction = new Vec3();
        dragForce = new Vec3();
        rollingResistanceForce = new Vec3();
        longitudinalForce = new Vec3();
        this.handler = handler;
        neuralNetwork = new NeuralNetwork(previousWeights);
        this.keyBoardEnabled = keyBoardEnabled;
    }

    private boolean keyDown;
    private boolean keyUp;
    private boolean keyRight;
    private boolean keyLeft;
    private boolean keyBreak;

    private void setActions() {
        if (keyBoardEnabled) {
            keyDown = Keyboard.keydown[40];
            keyUp = Keyboard.keydown[38];
            keyRight = Keyboard.keydown[39];
            keyLeft = Keyboard.keydown[37];
            keyBreak = Keyboard.keydown[66];
            return;
        }

        keyDown = false;
        keyUp = false;
        keyRight = false;
        keyLeft = false;
        keyBreak = false;

        Actions action = neuralNetwork.getAction(getInputs());

        switch (action) {
            case FORWARD:
                keyUp = true;
            case FORWARD_RIGHT:
                keyUp = true;
                keyRight = true;
            case FORWARD_LEFT:
                keyUp = true;
                keyLeft = true;
            case RIGHT:
                keyRight = true;
            case LEFT:
                keyLeft = true;
            case BREAK:
                keyBreak = true;
            case NOTHING:
        }
    }

    private InputContract getInputs() {
        double angle = Math.atan2(direction.x, direction.y);
        Point start = new Point((int) position.x, (int) position.y);

        return InputContract.builder()
            .withVisionContract(
                VisionContract.builder()
                    .withFrontVision(vision(angle, 0, start, 200))
                    .withRightVision(vision(angle, 1.35, start, 200))
                    .withLeftVision(vision(angle, -1.35, start, 200))
                    .withFrontRightVision(vision(angle, 0.55, start, 200))
                    .withFrontLeftVision(vision(angle, -1.35, start, 200))
                    .build()
            ).withDirectionX(
                this.direction.x
            ).withDirectionY(
                this.direction.y
            ).withVelocity(
                this.velocity.getSize()
            ).build();
    }

    private double vision(double angle, double angleOffset, Point start, int length) {
        Point rotatedEndPoint = VisionUtils.rotate(angle + angleOffset, length, start);
        Point intersectionPoint = VisionUtils.doIntersect(start, rotatedEndPoint);
        if (intersectionPoint != null) {
            return VisionUtils.calculateDistanceBetweenPoints(start, intersectionPoint);
        }

        return 500.00;
    }

    @Override
    public void tick() {
        setActions();
        double diff = TYRE * (velocity.getSize() / 30.0);

        if (keyRight) {
            direction.rotateZ(Math.toRadians((TYRE) * velocity.getSize()));
        } else if (keyLeft) {
            direction.rotateZ(Math.toRadians((-TYRE) * velocity.getSize()));
        }

        double diffAngle = velocity.getRelativeAngleBetween(direction);
        if (!Double.isNaN(diffAngle)) {
            double random = Math.random() * 50;
            velocity.rotateZ(diffAngle/((50 + random) * (5 * diff)));

            isDrifting = Math.abs(Math.toDegrees(diffAngle)) > 30;
        }

        if (keyUp) {
            engineForce = 300;
        }
        else if (keyDown) {
            engineForce = -100;
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

        addDrift();
        //addVisionLines();
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
        boolean isBraking = keyBreak;
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

    @Override
    public void render(Graphics graphics) {
        Graphics2D carGraphics = (Graphics2D) graphics.create();
        renderCar(carGraphics);
    }

    private void renderCar(Graphics2D graphics2D) {
        double angle = -Math.atan2(direction.x, direction.y);

        graphics2D.rotate(angle, position.x, position.y + 0);
        graphics2D.setColor(Color.BLACK);

        graphics2D.fillRect((int) (position.x-(WIDTH/2)), (int) (position.y-(HEIGHT/2)), WIDTH, HEIGHT);
    }

    private void addDrift() {
        if (isDrifting) {
            handler.addGameObject(new Drift(handler, (int) position.x, (int) position.y, WIDTH/3, WIDTH/6, WIDTH/6));
        }
    }

    //TODO just for show
    /*private void addVisionLines() {
        double angle = Math.atan2(direction.x, direction.y);
        Point start = new Point((int) position.x, (int) position.y);

        FRONT = addVisionLine(angle, 0, start, 200, FRONT, VisionInput.VISION_DIRECTION.FRONT);
        handler.addGameObject(FRONT);
        RIGHTFRONT = addVisionLine(angle, 0.55, start, 200, RIGHTFRONT, VisionInput.VISION_DIRECTION.FRONT_RIGHT);
        handler.addGameObject(RIGHTFRONT);
        RIGHT = addVisionLine(angle, 1.35, start, 200, RIGHT, VisionInput.VISION_DIRECTION.RIGHT);
        handler.addGameObject(RIGHT);
        LEFTFRONT = addVisionLine(angle, -0.55, start, 200, LEFTFRONT, VisionInput.VISION_DIRECTION.FRONT_LEFT);
        handler.addGameObject(LEFTFRONT);
        LEFT = addVisionLine(angle, -1.35, start, 200, LEFT, VisionInput.VISION_DIRECTION.LEFT);
        handler.addGameObject(LEFT);
    }

    private GameObject FRONT = null;
    private GameObject RIGHTFRONT = null;
    private GameObject RIGHT = null;
    private GameObject LEFTFRONT = null;
    private GameObject LEFT = null;

    private GameObject addVisionLine(double angle, double angleOffset, Point start, int length, GameObject gameObject, VisionInput.VISION_DIRECTION direction) {
        if (gameObject != null) {
            handler.removeGameObject(gameObject);
        }

        Point rotatedEndPoint = VisionUtils.rotate(angle + angleOffset, length, start);

        Point intersectionPoint = VisionUtils.doIntersect(start, rotatedEndPoint);
        if (intersectionPoint != null) {
            setVisionInput(VisionUtils.calculateDistanceBetweenPoints(start, intersectionPoint), direction);
            return new VisionLine(start, null, intersectionPoint);
        }

        setVisionInput(0, direction);
        return new VisionLine(start, rotatedEndPoint, null);
    }
    */

    /*private void setVisionInput(double distance, VisionInput.VISION_DIRECTION direction) {
        switch (direction) {
            case FRONT: VisionInput.front = distance;
            case FRONT_RIGHT: VisionInput.front_right = distance;
            case FRONT_LEFT: VisionInput.front_left = distance;
            case LEFT: VisionInput.left = distance;
            case RIGHT: VisionInput.right = distance;
        }
    }*/
}
