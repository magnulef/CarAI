package utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PrintedWeights {

    private final Double fitness;
    private final List<PrintedWeight> weights;

    @JsonCreator
    public PrintedWeights(
        @JsonProperty(value = "fitness") Double fitness,
        @JsonProperty(value = "weights") List<PrintedWeight> weights
    ) {
        this.fitness = fitness;
        this.weights = weights;
    }

    public List<PrintedWeight> getWeights() {
        return weights;
    }

    public Double getFitness() {
        return fitness;
    }
}
