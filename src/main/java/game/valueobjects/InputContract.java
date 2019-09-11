package game.valueobjects;

public class InputContract {

    private final VisionContract vision;
    private final double directionX;
    private final double directionY;
    private final double velocity;

    public InputContract(
        VisionContract vision,
        double directionX,
        double directionY,
        double velocity
    ) {
        this.vision = vision;
        this.directionX = directionX;
        this.directionY = directionY;
        this.velocity = velocity;
    }

    public VisionContract getVision() {
        return vision;
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public double getVelocity() {
        return velocity;
    }

    public double[] getData() {
        double[] data = new double[8];
        data[0] = vision.getFrontVision();
        data[1] = vision.getFrontLeftVision();
        data[2] = vision.getFrontRightVision();
        data[3] = vision.getLeftVision();
        data[4] = vision.getRightVision();
        data[5] = directionX;
        data[6] = directionY;
        data[7] = velocity;
        return data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private VisionContract vision;
        private double directionX;
        private double directionY;
        private double velocity;

        public Builder withVisionContract(VisionContract visionContract) {
            this.vision = visionContract;
            return this;
        }

        public Builder withDirectionX(double directionX) {
            this.directionX = directionX;
            return this;
        }

        public Builder withDirectionY(double directionY) {
            this.directionY = directionY;
            return this;
        }

        public Builder withVelocity(double velocity) {
            this.velocity = velocity;
            return this;
        }

        public InputContract build() {
            return new InputContract(vision, directionX, directionY, velocity);
        }
    }
}
