package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lib.json.JSON;
import org.nd4j.linalg.api.buffer.FloatBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class PrintUtils {

    public static PrintedWeights print(Map<String, INDArray> weights) {
        List<PrintedWeight> printed = new ArrayList<>();
        Set<String> keys = weights.keySet();
        for (String key : keys) {
            printed.add(new PrintedWeight(key, weights.get(key).data().asFloat()));
        }

        return new PrintedWeights(printed);
    }

    public static Map<String, INDArray> convert(PrintedWeights weights) {
        Map<String, INDArray> map = new HashMap<>();
        for (PrintedWeight weight : weights.getWeights()) {
            map.put(weight.getKey(), Nd4j.create(new FloatBuffer(weight.getValues())));
        }

        return map;
    }
}
