import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lib.json.JSON;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.buffer.DoubleBuffer;
import org.nd4j.linalg.api.buffer.FloatBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.NDArrayUtil;
import org.testng.annotations.Test;
import utils.PrintedWeight;
import utils.PrintedWeights;

public class TestBrain {

    @Test
    public void brainTest() {
        MultiLayerNetwork network = getNetwork();

        //Init weights
        Map<String, INDArray> paramTable = network.paramTable();
        Set<String> keys = paramTable.keySet();
        for (String key : keys) {
            INDArray values = paramTable.get(key);
            network.setParam(key, Nd4j.rand(values.shape()));
        }

        //Print init weights
        print(network);

        setWeights(network, network.paramTable());

        //Print unmodefied weights
        print(network);

        //Alter and print
        Map<String, INDArray> oldWeights = network.paramTable();
        Map<String, INDArray> alteredWeights = alterWeights(oldWeights);
        setWeights(network, alteredWeights);
        print(network);
    }

    @Test//(enabled = false)
    public void alterTest() {
        MultiLayerNetwork network = getNetwork();

        //Init weights
        Map<String, INDArray> paramTable = network.paramTable();
        Set<String> keys = paramTable.keySet();
        for (String key : keys) {
            INDArray values = paramTable.get(key);
            network.setParam(key, Nd4j.rand(values.shape()));
        }

        //Alter
        Map<String, INDArray> oldWeights = network.paramTable();
        print(oldWeights);
        Map<String, INDArray> alteredWeights = alterWeights(oldWeights);
        print(alteredWeights);
    }

    @Test//(enabled = false)
    public void alterTestNetwork() {
        MultiLayerNetwork network = getNetwork();

        //Init weights
        Map<String, INDArray> paramTable = network.paramTable();
        Set<String> keys = paramTable.keySet();
        for (String key : keys) {
            INDArray values = paramTable.get(key);
            network.setParam(key, Nd4j.rand(values.shape()));
        }

        //Alter
        Map<String, INDArray> oldWeights = network.paramTable();
        //setWeights(network, oldWeights);
        //print(oldWeights);

        Map<String, INDArray> alteredWeights = alterWeights(oldWeights);
        setWeights(network, alteredWeights);
        print(network);
    }

    private void print(MultiLayerNetwork network) {
        print(network.paramTable());
    }

    /*private void print(Map<String, INDArray> weights) {
        System.out.println("New Print");
        Set<String> keys = weights.keySet();
        for (String key : keys) {
            System.out.println(JSON.toJson(weights.get(key).data().asDouble()));
        }
    }*/

    private void print(Map<String, INDArray> weights) {
        System.out.println(weights);
        List<PrintedWeight> printed = new ArrayList<>();
        Set<String> keys = weights.keySet();
        for (String key : keys) {
            printed.add(new PrintedWeight(key, weights.get(key).data().asFloat()));
        }

        PrintedWeights printedWeights = new PrintedWeights(0.0, printed);
        System.out.println(JSON.toJson(printedWeights));
        convert(printedWeights);
    }

    private Map<String, INDArray> convert(PrintedWeights weights) {
        Map<String, INDArray> map = new HashMap<>();
        for (PrintedWeight weight : weights.getWeights()) {
            map.put(weight.getKey(), Nd4j.create(new FloatBuffer(weight.getValues())));
        }

        System.out.println(map);
        return map;
    }

    /*private void print(Map<String, INDArray> weights) {
        System.out.println(JSON.toJson(weights));
    }*/

    private MultiLayerNetwork getNetwork() {
        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
            .list()
            .layer(0, new DenseLayer.Builder()
                .nIn(5)
                .nOut(8)
                .activation(Activation.IDENTITY)
                .build())
            .layer(1, new DenseLayer.Builder()
                .nIn(8)
                .nOut(8)
                .activation(Activation.ELU)
                .build())
            .layer(2, new OutputLayer.Builder()
                .nIn(8)
                .nOut(5)
                .activation(Activation.RELU)
                .build())
            .pretrain(false)
            .backprop(false)
            .build();

        MultiLayerNetwork network = new MultiLayerNetwork(configuration);
        network.init();
        return network;
    }

    private Map<String, INDArray> alterWeights(Map<String, INDArray> weights) {
        Map<String, INDArray> alteredWeights = new HashMap<>();
        Set<String> keys = weights.keySet();

        for (String key : keys) {
            INDArray indArray = weights.get(key);
            //indArray.data().
            float[] weight = indArray.data().asFloat();
            for (int i = 0; i < weight.length; i++) {
                if (Math.random() < 0.1) {
                    //System.out.println("Triggered");
                    weight[i] = weight[i] * (float)Math.random();
                    //weight[i] = (float) (weight[i] * Math.random());
                }
            }

            FloatBuffer doubleBuffer = new FloatBuffer(weight);
            INDArray array = indArray.dup();
            array.setData(doubleBuffer);
            //INDArray array = Nd4j.create(doubleBuffer);
            //indArray.setData(doubleBuffer);
            //INDArray alteredWeight = Nd4j.create(weight);
            alteredWeights.put(key, array);
        }

        return alteredWeights;
    }

    private void setWeights(MultiLayerNetwork network, Map<String, INDArray> oldWeights) {
        Map<String, INDArray> paramTable = network.paramTable();
        Set<String> keys = paramTable.keySet();

        for (String key : keys) {
            //INDArray values = paramTable.get(key);
            //network.setParam(key, Nd4j.rand(values.shape()));
            network.setParam(key, oldWeights.get(key));

            //System.out.println(JSON.toJson(paramTable.get(key).data().asDouble()));
        }
    }
}
