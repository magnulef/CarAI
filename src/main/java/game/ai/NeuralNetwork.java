package game.ai;

import game.valueobjects.InputContract;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class NeuralNetwork {

    private final MultiLayerNetwork network;

    public NeuralNetwork(Map<String, INDArray> initialWeights) {
        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
            .list()
            .layer(0, new DenseLayer.Builder()
                .nIn(18)
                .nOut(36)
                .build())
            .layer(1, new DenseLayer.Builder()
                .nIn(36)
                .nOut(18)
                .activation(Activation.TANH)
                .build())
            .layer(2, new DenseLayer.Builder()
                .nIn(18)
                .nOut(16)
                .activation(Activation.RELU)
                .build())
            .layer(3, new OutputLayer.Builder()
                .nIn(16)
                .nOut(7)
                .activation(Activation.SOFTMAX)
                .build())
            .pretrain(false)
            .backprop(false)
            .build();

        network = new MultiLayerNetwork(configuration);
        network.init();

        if (initialWeights != null) {
            Map<String, INDArray> paramTable = network.paramTable();
            Set<String> keys = paramTable.keySet();

            for (String key : keys) {
                network.setParam(key, initialWeights.get(key));
            }

        } else {
            Map<String, INDArray> paramTable = network.paramTable();
            Set<String> keys = paramTable.keySet();
            for (String key : keys) {
                INDArray values = paramTable.get(key);
                network.setParam(key, Nd4j.rand(values.shape()));
            }
        }
    }

    public Actions getAction(InputContract inputContract) {
        INDArray input = Nd4j.create(inputContract.getData());
        INDArray output = network.output(input);

        int index = findLargestIndex(output.data().asDouble());

        switch (index) {
            case 0:
                return Actions.FORWARD;
            case 1:
                return Actions.FORWARD_RIGHT;
            case 2:
                return Actions.FORWARD_LEFT;
            case 3:
                return Actions.RIGHT;
            case 4:
                return Actions.LEFT;
            case 5:
                return Actions.BREAK;
            case 6:
                return Actions.NOTHING;
            default:
                return Actions.FORWARD;
        }
    }

    public Map<String, INDArray> getClone() {
        Map<String, INDArray> clone = new HashMap<>();
        Map<String, INDArray> paramTable = network.paramTable();
        for (String key : paramTable.keySet()) {
            paramTable.get(key).data().asFloat();
            clone.put(key, paramTable.get(key).dup());
        }

        return clone;
    }

    private int findLargestIndex(double[] array) {
        double largestValue = -999999;
        int largestIndex = 0;
        for(int index = 0; index < array.length; index++) {
            double value = array[index];
            if (value > largestValue) {
                largestValue = value;
                largestIndex = index;
            }
        }

        return largestIndex;
    }
}
