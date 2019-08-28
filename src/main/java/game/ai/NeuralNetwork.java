package game.ai;

import game.valueobjects.Weight;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class NeuralNetwork {

    private final MultiLayerNetwork network;

    public NeuralNetwork() {
        int inputNum = 8;
        int outputNum = 5;

        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
            .list()
            .layer(0, new DenseLayer.Builder()
                .nIn(inputNum)
                .nOut(8)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER)
                .build())
            .layer(1, new OutputLayer.Builder()
                .nIn(8)
                .nOut(outputNum)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER)
                .build())
            .pretrain(false)
            .backprop(false)
            .build();

        network = new MultiLayerNetwork(configuration);
        network.init();

        Map<String, INDArray> paramTable = network.paramTable();
        Set<String> keys = paramTable.keySet();
        Iterator<String> it = keys.iterator();

        while (it.hasNext()) {
            String key = it.next();
            INDArray values = paramTable.get(key);
            System.out.print(key+" ");//print keys
            System.out.println(Arrays.toString(values.shape()));//print shape of INDArray
            System.out.println(values);
            network.setParam(key, Nd4j.rand(values.shape()));//set some random values
        }
    }

    private MultiLayerConfiguration constructConfiguration(int outputNum, int inputNum) {
        return new NeuralNetConfiguration.Builder()
            .list()
            .layer(0, new DenseLayer.Builder()
                .nIn(inputNum)
                .nOut(8)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER)
                .build())
            .layer(1, new OutputLayer.Builder()
                .nIn(8)
                .nOut(outputNum)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER)
                .build())
            .pretrain(false)
            .backprop(false)
            .build();
    }

    private void setWeights(List<Weight> weights) {
        Map<String, INDArray> paramTable = network.paramTable();
        Set<String> keys = paramTable.keySet();
        Iterator<String> it = keys.iterator();

        while (it.hasNext()) {
            String key = it.next();
            INDArray values = paramTable.get(key);
            System.out.print(key+" ");//print keys
            System.out.println(Arrays.toString(values.shape()));//print shape of INDArray
            System.out.println(values);
            network.setParam(key, Nd4j.rand(values.shape()));//set some random values
        }
    }
}
