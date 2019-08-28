package game;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import lib.json.JSON;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.EmbeddingLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIteratorFactory;
import org.nd4j.linalg.factory.Nd4j;
import utils.Keyboard;
import utils.VisionInput;

public class NNTest {

    private final MultiLayerNetwork network;

    public NNTest() {
        Keyboard.alldown();
        int inputNum = 5;
        int outputNum = 5;

        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
            .list()
            .layer(0, new DenseLayer.Builder()
                .nIn(inputNum)
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
                .nOut(outputNum)
                .activation(Activation.RELU)
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
            //System.out.print(key+" ");//print keys
            //System.out.println(Arrays.toString(values.shape()));//print shape of INDArray
            //System.out.println(values);
            network.setParam(key, Nd4j.rand(values.shape()));//set some random values
        }

        print();
    }

    private void print() {
        Map<String, INDArray> paramTable = network.paramTable();
        Set<String> keys = paramTable.keySet();
        for (String key : keys) {
            System.out.println(JSON.toJson(paramTable.get(key).data().asDouble()));
        }
    }

    public void calculateAction() {
        double[] data = new double[5];
        data[0] = VisionInput.front;
        data[1] = VisionInput.front_right;
        data[2] = VisionInput.front_left;
        data[3] = VisionInput.right;
        data[4] = VisionInput.left;

        INDArray input = Nd4j.create(data);
        INDArray output = network.output(input);


        if (checkLargest(output.getDouble(0), output)) {
            Keyboard.keydown[38] = true;
        }

        if (checkLargest(output.getDouble(1), output)) {
            Keyboard.keydown[38] = true;
            Keyboard.keydown[39] = true;
        }

        if (checkLargest(output.getDouble(2), output)) {
            Keyboard.keydown[38] = true;
            Keyboard.keydown[37] = true;
        }

        if (checkLargest(output.getDouble(3), output)) {
            Keyboard.keydown[39] = true;
        }

        if (checkLargest(output.getDouble(4), output)) {
            Keyboard.keydown[37] = true;
        }
    }

    private boolean checkLargest(double value, INDArray output) {
        return value >= output.getDouble(0) && value >= output.getDouble(1) && value >= output.getDouble(2) && value >= output.getDouble(3) && value >= output.getDouble(4);
    }
}
