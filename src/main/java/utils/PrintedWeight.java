package utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PrintedWeight {

    private final String key;
    private final float[] values;

    @JsonCreator
    public PrintedWeight(
        @JsonProperty(value = "key") String key,
        @JsonProperty(value = "values") float[] values
    ) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public float[] getValues() {
        return values;
    }
}
