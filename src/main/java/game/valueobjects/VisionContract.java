package game.valueobjects;

public class VisionContract {

    private final double frontVision;
    private final double frontRightVision;
    private final double frontLeftVision;
    private final double rightVision;
    private final double leftVision;

    public VisionContract(
        double frontVision,
        double frontRightVision,
        double frontLeftVision,
        double rightVision,
        double leftVision
    ) {
        this.frontVision = frontVision;
        this.frontRightVision = frontRightVision;
        this.frontLeftVision = frontLeftVision;
        this.rightVision = rightVision;
        this.leftVision = leftVision;
    }

    public double getFrontVision() {
        return frontVision;
    }

    public double getFrontRightVision() {
        return frontRightVision;
    }

    public double getFrontLeftVision() {
        return frontLeftVision;
    }

    public double getRightVision() {
        return rightVision;
    }

    public double getLeftVision() {
        return leftVision;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private double frontVision;
        private double frontRightVision;
        private double frontLeftVision;
        private double rightVision;
        private double leftVision;

        public Builder withFrontVision(double frontVision) {
            this.frontVision = frontVision;
            return this;
        }

        public Builder withFrontRightVision(double frontRightVision) {
            this.frontRightVision = frontRightVision;
            return this;
        }

        public Builder withFrontLeftVision(double frontLeftVision) {
            this.frontLeftVision = frontLeftVision;
            return this;
        }

        public Builder withRightVision(double rightVision) {
            this.rightVision = rightVision;
            return this;
        }

        public Builder withLeftVision(double leftVision) {
            this.leftVision = leftVision;
            return this;
        }

        public VisionContract build() {
            return new VisionContract(frontVision, frontRightVision, frontLeftVision, rightVision, leftVision);
        }
    }
}
