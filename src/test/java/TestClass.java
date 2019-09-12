import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestClass {

    private final Double number;

    @JsonCreator
    public TestClass(
        @JsonProperty(value = "number") Double number
    ) {
        this.number = number;
    }

    public Double getNumber() {
        return number;
    }
}
