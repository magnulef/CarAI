package utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PrintedWeights {

    private final List<PrintedWeight> weights;

    @JsonCreator
    public PrintedWeights(
        @JsonProperty(value = "weights") List<PrintedWeight> weights
    ) {
        this.weights = weights;
    }

    public List<PrintedWeight> getWeights() {
        return weights;
    }
}
