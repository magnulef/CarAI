package game.valueobjects;

public class VisionContract {

    private final double frontVision;
    private final double frontRightVision;
    private final double frontLeftVision;
    private final double rightVision;
    private final double leftVision;

    private final double rightCenterBackVision;
    private final double leftCenterBackVision;
    private final double rightBackVision;
    private final double leftBackVision;

    public VisionContract(
        double frontVision,
        double frontRightVision,
        double frontLeftVision,
        double rightVision,
        double leftVision,
        double rightCenterBackVision,
        double leftCenterBackVision,
        double rightBackVision,
        double leftBackVision
    ) {
        this.frontVision = frontVision;
        this.frontRightVision = frontRightVision;
        this.frontLeftVision = frontLeftVision;
        this.rightVision = rightVision;
        this.leftVision = leftVision;

        this.rightCenterBackVision = rightCenterBackVision;
        this.leftCenterBackVision = leftCenterBackVision;
        this.rightBackVision = rightBackVision;
        this.leftBackVision = leftBackVision;
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

    public double getRightCenterBackVision() {
        return rightCenterBackVision;
    }

    public double getLeftCenterBackVision() {
        return leftCenterBackVision;
    }

    public double getRightBackVision() {
        return rightBackVision;
    }

    public double getLeftBackVision() {
        return leftBackVision;
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

        private double rightCenterBackVision;
        private double leftCenterBackVision;
        private double rightBackVision;
        private double leftBackVision;

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

        //
        public Builder withRightCenterBackVision(double rightCenterBackVision) {
            this.rightCenterBackVision = rightCenterBackVision;
            return this;
        }

        public Builder withLeftCenterBackVision(double leftCenterBackVision) {
            this.leftCenterBackVision = leftCenterBackVision;
            return this;
        }

        public Builder withRightBackVision(double rightBackVision) {
            this.rightBackVision = rightBackVision;
            return this;
        }

        public Builder withLeftBackVision(double leftBackVision) {
            this.leftBackVision = leftBackVision;
            return this;
        }

        public VisionContract build() {
            return new VisionContract(
                frontVision,
                frontRightVision,
                frontLeftVision,
                rightVision,
                leftVision,
                rightCenterBackVision,
                leftCenterBackVision,
                rightBackVision,
                leftBackVision
            );
        }
    }
}
