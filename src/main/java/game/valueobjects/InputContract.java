package game.valueobjects;

public class InputContract {

    private final VisionContract visionEdges;
    private final VisionContract visionRewards;
    private final double directionX;
    private final double directionY;
    private final double velocity;

    public InputContract(
        VisionContract visionEdges,
        VisionContract visionRewards,
        double directionX,
        double directionY,
        double velocity
    ) {
        this.visionEdges = visionEdges;
        this.visionRewards = visionRewards;
        this.directionX = directionX;
        this.directionY = directionY;
        this.velocity = velocity;
    }

    public VisionContract getVisionEdges() {
        return visionEdges;
    }

    public VisionContract getVisionRewards() {
        return visionRewards;
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
        double[] data = new double[12];
        data[0] = visionEdges.getFrontVision();
        data[1] = visionEdges.getFrontLeftVision();
        data[2] = visionEdges.getFrontRightVision();
        data[3] = visionEdges.getLeftVision();
        data[4] = visionEdges.getRightVision();
        data[5] = visionEdges.getRightCenterBackVision();
        data[6] = visionEdges.getLeftCenterBackVision();
        data[7] = visionEdges.getRightBackVision();
        data[8] = visionEdges.getLeftBackVision();
        /*data[9] = visionRewards.getFrontVision();
        data[10] = visionRewards.getFrontLeftVision();
        data[11] = visionRewards.getFrontRightVision();
        data[12] = visionRewards.getLeftVision();
        data[13] = visionRewards.getRightVision();
        data[14] = visionRewards.getRightCenterBackVision();
        data[15] = visionRewards.getLeftCenterBackVision();
        data[16] = visionRewards.getRightBackVision();
        data[17] = visionRewards.getLeftBackVision();*/
        data[9] = directionX;
        data[10] = directionY;
        data[11] = velocity;
        return data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private VisionContract visionEdges;
        private VisionContract visionRewards;
        private double directionX;
        private double directionY;
        private double velocity;

        public Builder withVisionEdges(VisionContract visionEdges) {
            this.visionEdges = visionEdges;
            return this;
        }

        public Builder withVisionRewards(VisionContract visionRewards) {
            this.visionRewards = visionRewards;
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
            return new InputContract(visionEdges, visionRewards, directionX, directionY, velocity);
        }
    }
}
