package game;

import game.renderables.GameObject;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.EmbeddingLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class NNTest extends GameObject {

    private final MultiLayerNetwork network;

    public NNTest() {
        int inputNum = 4;
        int outputNum = 3;

        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
            .seed(123)
            .layer(new EmbeddingLayer.Builder()
                .nIn(inputNum)
                .nOut(8)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER)
                .build())
            .list()
            .layer(1, new DenseLayer.Builder()
                .nIn(inputNum)
                .nOut(8)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER)
                .build())
            .layer(2, new OutputLayer.Builder()
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

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {

    }
}
