package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import library.json.JSON;
import org.bytedeco.javacpp.Loader;
import org.nd4j.linalg.api.buffer.DataBuffer;
//import org.nd4j.linalg.api.buffer.FloatBuffer;
import org.nd4j.linalg.api.buffer.util.AllocUtil;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class PrintUtils {

    public static PrintedWeights print(double fitness, Map<String, INDArray> weights) {
        List<PrintedWeight> printed = new ArrayList<>();
        Set<String> keys = weights.keySet();
        for (String key : keys) {
            printed.add(new PrintedWeight(key, weights.get(key).data().asFloat()));
        }

        return new PrintedWeights(fitness, printed);
    }

    public static Map<String, INDArray> convert(PrintedWeights weights) {
        Map<String, INDArray> map = new HashMap<>();

        for (PrintedWeight weight : weights.getWeights()) {
            //FloatBuffer floatBuffer = new FloatBuffer();
            //FloatBuffer floatBuffer = new FloatBuffer(weight.getValues());
            map.put(weight.getKey(), Nd4j.create(weight.getValues()));
        }

        return map;
    }
}
