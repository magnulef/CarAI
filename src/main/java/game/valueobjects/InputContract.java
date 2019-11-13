package game.valueobjects;

public class InputContract {

    private final VisionContract visionEdges;
    private final VisionContract visionRewards;
    private final double directionX;
    private final double directionY;
    private final double directionZ;

    private final double velocityX;
    private final double velocityY;
    private final double velocityZ;

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
        double directionZ,
        double velocityX,
        double velocityY,
        double velocityZ,
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
        this.directionZ = directionZ;

        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;

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

    public double[] getData() {
        double[] data = new double[21];
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
        data[11] = directionZ;
        data[12] = velocityX;
        data[13] = velocityY;
        data[14] = velocityZ;

        data[15] = accelerationX;
        data[16] = accelerationY;
        data[17] = accelerationZ;
        data[18] = tractionX;
        data[19] = tractionY;
        data[20] = tractionZ;
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
        private double directionZ;
        private double velocityX;
        private double velocityY;
        private double velocityZ;

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

        public Builder withDirectionZ(double directionZ) {
            this.directionZ = directionZ;
            return this;
        }

        public Builder withVelocityX(double velocityX) {
            this.velocityX = velocityX;
            return this;
        }

        public Builder withVelocityY(double velocityY) {
            this.velocityY = velocityY;
            return this;
        }

        public Builder withVelocityZ(double velocityZ) {
            this.velocityZ = velocityZ;
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
                directionZ,
                velocityX,
                velocityY,
                velocityZ,
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
