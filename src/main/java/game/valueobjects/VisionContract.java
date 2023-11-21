package game.valueobjects;

public class VisionContract {

    private final double frontVision;
    private final double frontAdd20;
    private final double frontMinus20;
    private final double frontAdd40;
    private final double frontMinus40;

    private final double frontAdd60;
    private final double frontMinus60;
    private final double frontAdd80;
    private final double frontMinus80;

    private final double frontAdd100;
    private final double frontMinus100;

    private final double frontAdd120;
    private final double frontMinus120;

    private final double frontAdd140;
    private final double frontMinus140;

    private final double frontAdd160;
    private final double frontMinus160;

    public VisionContract(
        double frontVision,
        double frontAdd20,
        double frontMinus20,
        double frontAdd40,
        double frontMinus40,
        double frontAdd60,
        double frontMinus60,
        double frontAdd80,
        double frontMinus80,
        double frontAdd100,
        double frontMinus100,
        double frontAdd120,
        double frontMinus120,
        double frontAdd140,
        double frontMinus140,
        double frontAdd160,
        double frontMinus160
    ) {
        this.frontVision = frontVision;
        this.frontAdd20 = frontAdd20;
        this.frontMinus20 = frontMinus20;
        this.frontAdd40 = frontAdd40;
        this.frontMinus40 = frontMinus40;

        this.frontAdd60 = frontAdd60;
        this.frontMinus60 = frontMinus60;
        this.frontAdd80 = frontAdd80;
        this.frontMinus80 = frontMinus80;

        this.frontAdd100 = frontAdd100;
        this.frontMinus100 = frontMinus100;

        this.frontAdd120 = frontAdd120;
        this.frontMinus120 = frontMinus120;

        this.frontAdd140 = frontAdd140;
        this.frontMinus140 = frontMinus140;

        this.frontAdd160 = frontAdd160;
        this.frontMinus160 = frontMinus160;
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

    public double getFrontAdd80() {
        return frontAdd80;
    }

    public double getFrontMinus80() {
        return frontMinus80;
    }

    public double getFrontAdd100() {
        return frontAdd100;
    }

    public double getFrontMinus100() {
        return frontMinus100;
    }

    public double getFrontAdd120() {
        return frontAdd120;
    }

    public double getFrontMinus120() {
        return frontMinus120;
    }

    public double getFrontAdd140() {
        return frontAdd140;
    }

    public double getFrontMinus140() {
        return frontMinus140;
    }

    public double getFrontAdd160() {
        return frontAdd160;
    }

    public double getFrontMinus160() {
        return frontMinus160;
    }

    public static class Builder {
        private double frontVision;
        private double frontAdd20;
        private double frontMinus20;
        private double frontAdd40;
        private double frontMinus40;

        private double frontAdd60;
        private double frontMinus60;
        private double frontAdd80;
        private double frontMinus80;

        private double frontAdd100;
        private double frontMinus100;

        private double frontAdd120;
        private double frontMinus120;

        private double frontAdd140;
        private double frontMinus140;

        private double frontAdd160;
        private double frontMinus160;

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

        public Builder withFrontAdd80(double frontAdd80) {
            this.frontAdd80 = frontAdd80;
            return this;
        }

        public Builder withFrontMinus80(double frontMinus80) {
            this.frontMinus80 = frontMinus80;
            return this;
        }

        public Builder withFrontAdd100(double frontAdd100) {
            this.frontAdd100 = frontAdd100;
            return this;
        }

        public Builder withFrontMinus100(double frontMinus100) {
            this.frontMinus100 = frontMinus100;
            return this;
        }

        public Builder withFrontAdd120(double frontAdd120) {
            this.frontAdd120 = frontAdd120;
            return this;
        }

        public Builder withFrontMinus120(double frontMinus120) {
            this.frontMinus120 = frontMinus120;
            return this;
        }

        public Builder withFrontAdd140(double frontAdd140) {
            this.frontAdd140 = frontAdd140;
            return this;
        }

        public Builder withFrontMinus140(double frontMinus140) {
            this.frontMinus140 = frontMinus140;
            return this;
        }

        public Builder withFrontAdd160(double frontAdd160) {
            this.frontAdd160 = frontAdd160;
            return this;
        }

        public Builder withFrontMinus160(double frontMinus160) {
            this.frontMinus160 = frontMinus160;
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
                frontAdd80,
                frontMinus80,
                frontAdd100,
                frontMinus100,
                frontAdd120,
                frontMinus120,
                frontAdd140,
                frontMinus140,
                frontAdd160,
                frontMinus160
            );
        }
    }
}
