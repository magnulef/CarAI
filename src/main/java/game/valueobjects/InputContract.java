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
        double[] data = new double[46];
        data[0] = visionEdges.getFrontVision();
        data[1] = visionEdges.getFrontAdd20();
        data[2] = visionEdges.getFrontMinus20();
        data[3] = visionEdges.getFrontAdd40();
        data[4] = visionEdges.getFrontMinus40();
        data[5] = visionEdges.getFrontAdd60();
        data[6] = visionEdges.getFrontMinus60();
        data[7] = visionEdges.getFrontAdd80();
        data[8] = visionEdges.getFrontMinus80();
        data[9] = visionEdges.getFrontAdd100();
        data[10] = visionEdges.getFrontMinus100();
        data[11] = visionEdges.getFrontAdd120();
        data[12] = visionEdges.getFrontMinus120();
        data[13] = visionEdges.getFrontAdd140();
        data[14] = visionEdges.getFrontMinus140();
        data[15] = visionEdges.getFrontAdd160();
        data[16] = visionEdges.getFrontMinus160();

        data[17] = visionRewards.getFrontVision();
        data[18] = visionRewards.getFrontAdd20();
        data[19] = visionRewards.getFrontMinus20();
        data[20] = visionRewards.getFrontAdd40();
        data[21] = visionRewards.getFrontMinus40();
        data[22] = visionRewards.getFrontAdd60();
        data[23] = visionRewards.getFrontMinus60();
        data[24] = visionRewards.getFrontAdd80();
        data[25] = visionRewards.getFrontMinus80();
        data[26] = visionRewards.getFrontAdd100();
        data[27] = visionRewards.getFrontMinus100();
        data[28] = visionRewards.getFrontAdd120();
        data[29] = visionRewards.getFrontMinus120();
        data[30] = visionRewards.getFrontAdd140();
        data[31] = visionRewards.getFrontMinus140();
        data[32] = visionRewards.getFrontAdd160();
        data[33] = visionRewards.getFrontMinus160();

        data[34] = directionX;
        data[35] = directionY;
        data[36] = directionZ;
        data[37] = velocityX;
        data[38] = velocityY;
        data[39] = velocityZ;

        data[40] = accelerationX;
        data[41] = accelerationY;
        data[42] = accelerationZ;
        data[43] = tractionX;
        data[44] = tractionY;
        data[45] = tractionZ;
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
