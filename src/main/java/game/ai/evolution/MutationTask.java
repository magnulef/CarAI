package game.ai.evolution;

import game.Handler;
import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.nd4j.linalg.api.ndarray.INDArray;
import static game.ai.GenerationUtils.evolve;

public class MutationTask implements Runnable {

    private final int from;
    private final int to;
    private final double mutationChance;
    private final float mutationRate;
    private final List<Car> cars;
    private final Handler handler;

    public MutationTask(int from, int to, double mutationChance, float mutationRate, List<Car> cars, Handler handler) {
        this.from = from;
        this.to = to;
        this.mutationChance = mutationChance;
        this.mutationRate = mutationRate;
        this.cars = cars;
        this.handler = handler;
    }

    @Override
    public void run() {
        List<Car> newCars = new ArrayList<>();

        for (int i = from; i < to; i++) {
            Car car = cars.get(i);
            //Map<String, INDArray> evolvedWeights = evolve(mutationChance, mutationRate, car.getWeight());
            Map<String, INDArray> evolvedWeights = evolve(mutationChance, car.getWeight());
            newCars.add(new Car(handler, evolvedWeights, false, false, false, true));
        }

        EvolutionStatus.addNewCars(newCars);
        EvolutionStatus.threadIsDone();
    }
}
