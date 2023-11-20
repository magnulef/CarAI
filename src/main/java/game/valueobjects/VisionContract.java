package game.valueobjects;

public class VisionContract {

    private final double frontVision;
    private final double frontAdd20;
    private final double frontMinus20;
    private final double frontAdd40;
    private final double frontMinus40;

    private final double frontAdd60;
    private final double frontMinus60;
    private final double frontAdd90;
    private final double frontMinus90;

    public VisionContract(
        double frontVision,
        double frontAdd20,
        double frontMinus20,
        double frontAdd40,
        double frontMinus40,
        double frontAdd60,
        double frontMinus60,
        double frontAdd90,
        double frontMinus90
    ) {
        this.frontVision = frontVision;
        this.frontAdd20 = frontAdd20;
        this.frontMinus20 = frontMinus20;
        this.frontAdd40 = frontAdd40;
        this.frontMinus40 = frontMinus40;

        this.frontAdd60 = frontAdd60;
        this.frontMinus60 = frontMinus60;
        this.frontAdd90 = frontAdd90;
        this.frontMinus90 = frontMinus90;
    }

    public double getFrontVision() {
        return frontVision;
    }

    public static Builder builder() {
        return new Builder();
    }

    public double getFrontAdd20() {
        return frontAdd20;
    }

    public double getFrontMinus20() {
        return frontMinus20;
    }

    public double getFrontAdd40() {
        return frontAdd40;
    }

    public double getFrontMinus40() {
        return frontMinus40;
    }

    public double getFrontAdd60() {
        return frontAdd60;
    }

    public double getFrontMinus60() {
        return frontMinus60;
    }

    public double getFrontAdd90() {
        return frontAdd90;
    }

    public double getFrontMinus90() {
        return frontMinus90;
    }

    public static class Builder {
        private double frontVision;
        private double frontAdd20;
        private double frontMinus20;
        private double frontAdd40;
        private double frontMinus40;

        private double frontAdd60;
        private double frontMinus60;
        private double frontAdd90;
        private double frontMinus90;

        public Builder withFrontVision(double frontVision) {
            this.frontVision = frontVision;
            return this;
        }

        public Builder withFrontAdd20(double frontAdd20) {
            this.frontAdd20 = frontAdd20;
            return this;
        }

        public Builder withFrontMinus20(double frontMinus20) {
            this.frontMinus20 = frontMinus20;
            return this;
        }

        public Builder withFrontAdd40(double frontAdd40) {
            this.frontAdd40 = frontAdd40;
            return this;
        }

        public Builder withFrontMinus40(double frontMinus40) {
            this.frontMinus40 = frontMinus40;
            return this;
        }

        //
        public Builder withFrontAdd60(double frontAdd60) {
            this.frontAdd60 = frontAdd60;
            return this;
        }

        public Builder withFrontMinus60(double frontMinus60) {
            this.frontMinus60 = frontMinus60;
            return this;
        }

        public Builder withFrontAdd90(double frontAdd90) {
            this.frontAdd90 = frontAdd90;
            return this;
        }

        public Builder withFrontMinus90(double frontMinus90) {
            this.frontMinus90 = frontMinus90;
            return this;
        }

        public VisionContract build() {
            return new VisionContract(
                frontVision,
                frontAdd20,
                frontMinus20,
                frontAdd40,
                frontMinus40,
                frontAdd60,
                frontMinus60,
                frontAdd90,
                frontMinus90
            );
        }
    }
}
