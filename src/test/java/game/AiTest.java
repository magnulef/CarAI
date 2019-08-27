package game;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class AiTest {

    public void test() {
        DenseLayer denseLayer = new DenseLayer.Builder()
            .build();

        MultiLayerConfiguration configuration
                = new NeuralNetConfiguration.Builder()
                //.iterations(1000)
                .activation(Activation.TANH)
                //.weightInit(WeightInit.XAVIER)
                //.learningRate(0.1)
                //.regularization(true).l2(0.0001)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(1).nOut(3).build())
                .layer(1, new DenseLayer.Builder().nIn(3).nOut(3).build())
                .layer(2, new OutputLayer.Builder(
                        LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(3).nOut(1).build())
                //.backprop(true).pretrain(false)
                .build();
    }
}
