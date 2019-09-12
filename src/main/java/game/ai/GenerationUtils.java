package game.ai;

import game.Simulation;
import java.util.List;
import java.util.Map;
import org.nd4j.linalg.api.ndarray.INDArray;

public class GenerationUtils {

    public static boolean isDone(List<Simulation> simulations) {
        if (simulations == null || simulations.isEmpty()) {
            return true;
        }

        for (Simulation simulation : simulations) {
            if (!simulation.isDeadOrDone()) {
                return false;
            }
        }

        return true;
    }

    public static Simulation getBestPerformer(List<Simulation> simulations) {
        if (!isDone(simulations)) {
            return null;
        }

        double bestFitness = -1;
        Simulation bestCandidate = null;
        for (Simulation simulation : simulations) {
            double fitness = simulation.getFitness();
            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestCandidate = simulation;
            }
        }

        return bestCandidate;
    }

    public static Map<String, INDArray> evolve(double evolutionChance, Map<String, INDArray> oldWeights) {
        return oldWeights;
    }
}
