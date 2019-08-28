package game.valueobjects;

public class Weight {

    private final String key;
    private final double[] weights;

    public Weight(String key, double[] weights) {
        this.key = key;
        this.weights = weights;
    }

    public String getKey() {
        return key;
    }

    public double[] getWeights() {
        return weights;
    }
}
