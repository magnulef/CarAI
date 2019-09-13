package game.ai;

import game.Handler;
import game.Simulation;
import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nd4j.linalg.api.buffer.FloatBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;

public class GenerationUtils {

    public static List<Simulation> evolveGeneration(Handler handler, double mutationChance, List<Simulation> simulations) {
        List<Car> cars = new ArrayList<>();
        for (Simulation simulation : simulations) {
            cars.addAll(simulation.getCars());
        }

        sort(cars);
        List<Car> newCars = new ArrayList<>();
        int top = cars.size() / 10;
        for (int i = 0; i < top; i = i++) {
            newCars.add(cars.get(0).clone(handler));
        }

        for (int i = top; i <= top * 4; i = i + 2) {
            newCars.add(
                new Car(
                    handler,
                    reproduce(
                        cars.get(i).getWeight(),
                        cars.get(i + 1).getWeight()
                    ),
                    false,
                    true,
                    false,
                    true
                )
            );
        }

        int index = top * 4 + 1;
        while(cars.size() > newCars.size()) {
            if (cars.size() <= index) {
                mutateRandom(handler, mutationChance, cars.size(), newCars);
                continue;
            }

            Car car = cars.get(index);
            Map<String, INDArray> evolvedWeights = evolve(mutationChance, car.getWeight());
            newCars.add(new Car(handler, evolvedWeights, false, true, false, true));
        }

        List<Simulation> newSimulations = new ArrayList<>();
        for (int i = 0; i < simulations.size(); i++) {
            int groupSize = newCars.size() / simulations.size();

            List<Car> grouped = newCars.subList(i * groupSize, i * groupSize + groupSize);
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

    private static void mutateRandom(Handler handler, double mutationChance, int maxSize, List<Car> cars) {
        if (cars.size() >= maxSize) {
            return;
        }

        while (cars.size() < maxSize) {
            int randomTarget = (int) (Math.random() * cars.size());
            Car randomCar = cars.get(randomTarget);
            Map<String, INDArray> evolvedWeights = evolve(mutationChance, randomCar.getWeight());
            cars.add(new Car(handler, evolvedWeights, false, true, false, true));
        }
    }

    /*public static List<Simulation> evolveGeneration(Handler handler, double evolutionChance, List<Simulation> simulations) {
        sort(simulations);
        List<Simulation> newGeneration = new ArrayList<>();

        newGeneration.add(new Simulation(handler, simulations.get(0).getWeights()));
        newGeneration.add(new Simulation(handler, simulations.get(1).getWeights()));
        newGeneration.add(new Simulation(handler, simulations.get(2).getWeights()));
        newGeneration.add(new Simulation(handler, simulations.get(3).getWeights()));
        newGeneration.add(new Simulation(handler, simulations.get(4).getWeights()));

        for (int i = 5; i < 15; i++) {
            newGeneration.add(
                new Simulation(
                    handler,
                    reproduce(
                        simulations.get(i).getWeights(),
                        simulations.get(i+5).getWeights()
                    )
                )
            );

            newGeneration.add(
                new Simulation(
                    handler,
                    evolve(
                        evolutionChance,
                        simulations.get(i).getWeights()
                    )
                )
            );
        }

        return newGeneration;
    }*/

    private static void sort(List<Car> cars) {
        Collections.sort(cars, new Comparator<Car>(){
            @Override
            public int compare(Car nr1, Car nr2){
                if (nr1.getFitness() < nr2.getFitness()) return -1;
                if (nr1.getFitness() > nr2.getFitness()) return 1;
                return 0;
            }
        });
    }

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

    public static Car getBestPerformer(List<Simulation> simulations) {
        if (!isDone(simulations)) {
            return null;
        }

        double bestFitness = -1;
        Car bestCandidate = null;
        for (Simulation simulation : simulations) {
            Car car = simulation.getFittestCar();
            if (car.getFitness() > bestFitness) {
                bestFitness = car.getFitness();
                bestCandidate = car;
            }
        }

        return bestCandidate;
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
                double random = Math.random();
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

    public static Map<String, INDArray> evolve(double evolutionChance, Map<String, INDArray> oldWeights) {
        Map<String, INDArray> alteredWeights = new HashMap<>();
        Set<String> keys = oldWeights.keySet();

        for (String key : keys) {
            INDArray indArray = oldWeights.get(key);

            float[] weight = indArray.data().asFloat();
            for (int i = 0; i < weight.length; i++) {
                double random = Math.random();
                if (random < evolutionChance) {
                    if (random < evolutionChance/2) {
                        weight[i] = weight[i] - (weight[i] * (float)Math.random());
                    } else {
                        weight[i] = weight[i] * (float)Math.random();
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
