package game.renderables.car;

import game.Handler;
import game.ai.Actions;
import game.ai.NeuralNetwork;
import game.renderables.GameObject;
import game.renderables.RewardGates;
import game.renderables.Track;
import game.valueobjects.InputContract;
import game.valueobjects.Line;
import game.valueobjects.VisionContract;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import org.nd4j.linalg.api.ndarray.INDArray;
import utils.Keyboard;
import utils.Vec3;
import utils.VisionUtils;

public class Car extends GameObject {

    private static final double MASS = 800;
    private static final double BREAKING_FORCE = 500; //500
    private static final double DRAG = 0.4257;
    private static final double ROLLING_RESISTANCE = 12.8;
    private static final double TYRE = 0.6; //0.6
    private static final int WIDTH = 20;
    private static final int HEIGHT = 40;

    private final Handler handler;
    private final NeuralNetwork neuralNetwork;
    private final boolean keyBoardEnabled;
    private final CarRenderer carRenderer;

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

    private boolean keyDown;
    private boolean keyUp;
    private boolean keyRight;
    private boolean keyLeft;
    private boolean keyBreak;

    private boolean isDead;
    private final boolean isDeathEnabled;

    private double fitness;
    private boolean oneTimeUpdate = false;
    private int previousGate = -1;
    private final boolean shouldRender;
    private final boolean renderVisionLines;

    public Car(
        Handler handler,
        Map<String, INDArray> previousWeights,
        boolean keyBoardEnabled,
        boolean shouldRender,
        boolean renderVisionLines,
        boolean isDeathEnabled
    ) {
        this.position = new Vec3(100, 200, 0);
        this.direction = new Vec3(0, 1, 0);
        this.velocity = new Vec3(1, 1, 1);
        this.acceleration = new Vec3();
        this.breakingForce = new Vec3();
        this.engineForce = 0;
        this.traction = new Vec3();
        this.dragForce = new Vec3();
        this.rollingResistanceForce = new Vec3();
        this.longitudinalForce = new Vec3();
        this.handler = handler;
        this.neuralNetwork = new NeuralNetwork(previousWeights);
        this.keyBoardEnabled = keyBoardEnabled;
        this.carRenderer = new CarRenderer(shouldRender, renderVisionLines);
        this.shouldRender = shouldRender;
        this.renderVisionLines = renderVisionLines;
        this.isDead = false;
        this.isDeathEnabled = isDeathEnabled;
    }

    public Car clone(Handler handler) {
        return new Car(
            handler,
            this.neuralNetwork.getClone(),
            this.keyBoardEnabled,
            this.shouldRender,
            this.renderVisionLines,
            this.isDeathEnabled
        );
    }

    public Map<String, INDArray> getWeight() {
        return neuralNetwork.getClone();
    }

    public boolean isDead() {
        //System.out.println("Is dead");
        return isDead;
    }

    private boolean outOfBounds() {
        return position.x < 0 || position.y < 0 || position.x > 1000 || position.y > 1000;
    }

    private boolean shouldDie() {
        if (overlappingEdge() || intersectLine() || outOfBounds()) {
            return true;
        }

        return false;
    }

    private boolean overlappingEdge() {
        return position.x - WIDTH/2 < 10 || position.x + WIDTH/2 > 990 || position.y - HEIGHT/2 < 10 || position.y + HEIGHT/2 > 970;
    }

    private boolean intersectLine() {
        Rectangle car = new Rectangle(
            (int)(position.x - WIDTH/2),
            (int) (position.y - HEIGHT/2),
            WIDTH,
            HEIGHT
        );

        for (Line line : Track.getLines()) {
            boolean intersection = car.intersectsLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            if (intersection) {
                return true;
            }
        }

        return false;
    }

    public double getFitness() {
        if (!oneTimeUpdate) {
            this.fitness = fitness + (position.y - 200);
            oneTimeUpdate = true;
        }
        return fitness;
        //return fitness;
    }

    private void updateFitness() {
        Rectangle car = new Rectangle(
            (int)(position.x - WIDTH/2),
            (int) (position.y - HEIGHT/2),
            WIDTH,
            HEIGHT
        );

        if (previousGate == 30) {
            previousGate = -1;
        }

        for (Line line : RewardGates.getLines()) {
            boolean intersection = car.intersectsLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

            /*if (intersection) {
                System.out.println("check");
            }*/

            if (intersection && line.getNumber() == previousGate + 1) {
                fitness = fitness + 100;
                previousGate = line.getNumber();
            }
        }

        //fitness = fitness + (position.x - 100) + (position.y - 200);
    }

    private long updateTime = System.currentTimeMillis();

    public void tick() {
        //System.out.println(position.x + " : " + position.y);
        /*if (shouldDie()) {
            position = new Vec3(100, 100, 0);
        }*/

        if (isDeathEnabled && (isDead || shouldDie())) {
            isDead = true;
            engineForce = 0;

            return;
        }

        /*long currentTime = System.currentTimeMillis();
        if (updateTime + 100 > currentTime) {
            return;
        }

        updateTime = currentTime;*/

        updateFitness();
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

        carRenderer.addDrift(isDrifting, handler, position, WIDTH);
        carRenderer.addVisionLines(handler, direction, position);
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
        if (Double.isNaN(velocity.getSize())) {
            return;
        }

        velocity.scale(deltaTime);
        position.add(velocity);
    }

    //private long previousAction = System.currentTimeMillis();

    private void setActions() {
        if (keyBoardEnabled) {
            keyDown = Keyboard.keydown[40];
            keyUp = Keyboard.keydown[38];
            keyRight = Keyboard.keydown[39];
            keyLeft = Keyboard.keydown[37];
            keyBreak = Keyboard.keydown[66];
            return;
        }

        /*long currentTime = System.currentTimeMillis();
        if (previousAction + 100 > currentTime) {
            return;
        }

        previousAction = currentTime;*/

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

        //System.out.println(action);
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
    public void render(Graphics graphics) {
        carRenderer.renderCar(graphics, direction, position, WIDTH, HEIGHT, isDead ? Color.RED : Color.BLACK);
    }
}
