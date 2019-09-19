package game.ai;

import game.Handler;
import game.Simulation;
import game.ai.evolution.EvolutionStatus;
import game.ai.evolution.MutationTask;
import game.ai.evolution.ReproductionTask;
import game.ai.evolution.TopTask;
import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import org.nd4j.linalg.api.buffer.FloatBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import utils.ImportInitialWeights;
import static utils.Random.random;

public class GenerationUtils {

    public static List<Simulation> evolve(ThreadPoolExecutor executor, Handler handler, List<Simulation> previousGeneration) {
        EvolutionStatus.restart();
        EvolutionStatus.startEvolution();
        List<Car> cars = new ArrayList<>();
        for (Simulation simulation : previousGeneration) {
            cars.addAll(simulation.getCars());
        }

        sort(cars);

        //Still wont turn
        int top = cars.size() / 10;
        TopTask topTask = new TopTask(handler, top, cars);
        MutationTask mutationTask01 = new MutationTask(0, top, 0.2, 0.01f, cars, handler);
        MutationTask mutationTask10 = new MutationTask(0, top, 0.3, 0.1f, cars, handler);
        ReproductionTask reproductionTask = new ReproductionTask(handler, top, cars);
        //MutationTask mutationTask20 = new MutationTask(top, top * 4, 0.5, 0.1f, cars, handler);
        //MutationTask mutationTask30 = new MutationTask(top * 3, top * 5, 0.7, 0.2f, cars, handler);
        MutationTask mutationTask20 = new MutationTask(top, top * 4, 0.5, (float) random(), cars, handler);
        MutationTask mutationTask30 = new MutationTask(top * 3, top * 5, 0.7, (float) random(), cars, handler);

        EvolutionStatus.setThreadCount(6);
        executor.execute(topTask);
        executor.execute(mutationTask01);
        executor.execute(mutationTask10);
        executor.execute(reproductionTask);
        executor.execute(mutationTask20);
        executor.execute(mutationTask30);

        runEvolution();

        while(EvolutionStatus.getTargetSize() > EvolutionStatus.getNewCars().size()) {
            EvolutionStatus.addNewCar(new Car(handler, ImportInitialWeights.getImportedWeights(), false, false, false, true));
        }

        EvolutionStatus.remove(cars);

        List<Simulation> newSimulations = new ArrayList<>();
        for (int i = 0; i < previousGeneration.size(); i++) {
            int groupSize = EvolutionStatus.getNewCars().size() / previousGeneration.size();

            List<Car> grouped = EvolutionStatus.getNewCars().subList(i * groupSize, i * groupSize + groupSize);
            newSimulations.add(
                new Simulation(
                    i,
                    grouped,
                    handler
                )
            );
        }

        return newSimulations;
    }

    private static void runEvolution() {
        boolean done = false;
        while (!done) {
            try {
                Thread.sleep(200);
            } catch (Exception ex) {

            }
            if (EvolutionStatus.allDone()) {
                done = true;
            }
        }
    }

    private static void sort(List<Car> cars) {
        Collections.sort(cars, new Comparator<Car>(){
            @Override
            public int compare(Car nr1, Car nr2){
                if (nr1.getFitness() < nr2.getFitness()) return 1;
                if (nr1.getFitness() > nr2.getFitness()) return -1;
                return 0;
            }
        });
    }

    public static Map<String, INDArray> reproduce(Map<String, INDArray> parent1, Map<String, INDArray> parent2) {
        Map<String, INDArray> child = new HashMap<>();
        Set<String> keys = parent1.keySet();

        for (String key : keys) {
            INDArray parent1Row = parent1.get(key);
            INDArray parent2Row = parent2.get(key);

            float[] parent1Weight = parent1Row.data().asFloat();
            float[] parent2Weight = parent2Row.data().asFloat();
            float[] weight = new float[parent1Weight.length];

            for (int i = 0; i < parent1Weight.length; i++) {
                double random = random();
                if (random < 0.33) {
                    weight[i] = parent1Weight[i];
                } else if (random > 0.33 && random < 0.66) {
                    if (parent2Weight.length < i) {
                        weight[i] = parent1Weight[i];
                    } else {
                        weight[i] = parent2Weight[i];
                    }
                } else {
                    if (parent2Weight.length < i) {
                        weight[i] = parent1Weight[i];
                    } else {
                        weight[i] = (parent1Weight[i] + parent2Weight[i]) / 2;
                    }
                }
            }

            FloatBuffer doubleBuffer = new FloatBuffer(weight);
            INDArray array = parent1Row.dup();
            array.setData(doubleBuffer);

            child.put(key, array);
        }

        return child;
    }

    public static Map<String, INDArray> evolve(double mutationChance, float mutationRate, Map<String, INDArray> oldWeights) {
        Map<String, INDArray> alteredWeights = new HashMap<>();
        Set<String> keys = oldWeights.keySet();

        for (String key : keys) {
            INDArray indArray = oldWeights.get(key);

            float[] weight = indArray.data().asFloat();
            for (int i = 0; i < weight.length; i++) {
                double random = random();
                if (random < mutationChance) {
                    if (random < mutationChance/2) {
                        weight[i] = weight[i] + mutationRate;
                    } else {
                        weight[i] = weight[i] - mutationRate;
                    }
                }
            }

            FloatBuffer doubleBuffer = new FloatBuffer(weight);
            INDArray array = indArray.dup();
            array.setData(doubleBuffer);

            alteredWeights.put(key, array);
        }

        return alteredWeights;
    }

    public static Map<String, INDArray> evolve(double evolutionChance, Map<String, INDArray> oldWeights) {
        Map<String, INDArray> alteredWeights = new HashMap<>();
        Set<String> keys = oldWeights.keySet();

        for (String key : keys) {
            INDArray indArray = oldWeights.get(key);

            float[] weight = indArray.data().asFloat();
            for (int i = 0; i < weight.length; i++) {
                double random = random();
                if (random < evolutionChance) {
                    if (random < evolutionChance/2) {
                        weight[i] = weight[i] * (1 + (float)random());
                    } else {
                        weight[i] = weight[i] * (1 - (float)random());
                    }
                }
            }

            FloatBuffer doubleBuffer = new FloatBuffer(weight);
            INDArray array = indArray.dup();
            array.setData(doubleBuffer);

            alteredWeights.put(key, array);
        }

        return alteredWeights;
    }
}
