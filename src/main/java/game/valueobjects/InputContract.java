package game.valueobjects;

public class InputContract {

    private final VisionContract visionEdges;
    private final VisionContract visionRewards;
    private final double directionX;
    private final double directionY;
    private final double velocity;

    private final double accelerationX;
    private final double accelerationY;
    private final double accelerationZ;
    private final double tractionX;
    private final double tractionY;
    private final double tractionZ;


    public InputContract(
        VisionContract visionEdges,
        VisionContract visionRewards,
        double directionX,
        double directionY,
        double velocity,

        double accelerationX,
        double accelerationY,
        double accelerationZ,
        double tractionX,
        double tractionY,
        double tractionZ
    ) {
        this.visionEdges = visionEdges;
        this.visionRewards = visionRewards;
        this.directionX = directionX;
        this.directionY = directionY;
        this.velocity = velocity;

        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
        this.accelerationZ = accelerationZ;
        this.tractionX = tractionX;
        this.tractionY = tractionY;
        this.tractionZ = tractionZ;
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
        double[] data = new double[18];
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

        data[12] = accelerationX;
        data[13] = accelerationY;
        data[14] = accelerationZ;
        data[15] = tractionX;
        data[16] = tractionY;
        data[17] = tractionZ;
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

        private double accelerationX;
        private double accelerationY;
        private double accelerationZ;
        private double tractionX;
        private double tractionY;
        private double tractionZ;

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

        public Builder withAccelerationX(double accelerationX) {
            this.accelerationX = accelerationX;
            return this;
        }

        public Builder withAccelerationY(double accelerationY) {
            this.accelerationY = accelerationY;
            return this;
        }

        public Builder withAccelerationZ(double accelerationZ) {
            this.accelerationZ = accelerationZ;
            return this;
        }

        public Builder withTractionX(double tractionX) {
            this.tractionX = tractionX;
            return this;
        }

        public Builder withTractionY(double tractionY) {
            this.tractionY = tractionY;
            return this;
        }

        public Builder withTractionZ(double tractionZ) {
            this.tractionZ = tractionZ;
            return this;
        }

        public InputContract build() {
            return new InputContract(
                visionEdges,
                visionRewards,
                directionX,
                directionY,
                velocity,
                accelerationX,
                accelerationY,
                accelerationZ,
                tractionX,
                tractionY,
                tractionZ
            );
        }
    }
}
