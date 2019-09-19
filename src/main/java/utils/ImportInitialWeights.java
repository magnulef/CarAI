package utils;

import java.util.Map;
import lib.json.JSON;
import org.nd4j.linalg.api.ndarray.INDArray;

public class ImportInitialWeights {

    private static final String json = "";

    public static Map<String, INDArray> getImportedWeights() {
        if (json == null || json.length() < 5) {
            return null;
        }
        return PrintUtils.convert(JSON.fromJson(json, PrintedWeights.class));
    }
}
