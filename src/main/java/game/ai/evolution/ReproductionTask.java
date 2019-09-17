package game.ai.evolution;

import game.Handler;
import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.List;
import static game.ai.GenerationUtils.reproduce;

public class ReproductionTask implements Runnable {

    private final List<Car> cars;
    private final Handler handler;
    private final int top;

    public ReproductionTask(Handler handler, int top, List<Car> cars) {
        this.handler = handler;
        this.top = top;
        this.cars = cars;
    }

    @Override
    public void run() {
        List<Car> newCars = new ArrayList<>();
        for (int i = 0; i < top * 4; i = i + 2) {
            newCars.add(
                new Car(
                    handler,
                    reproduce(
                        cars.get(i).getWeight(),
                        cars.get(i + 1).getWeight()
                    ),
                    false,
                    false,
                    false,
                    true
                )
            );
        }

        EvolutionStatus.addNewCars(newCars);
        EvolutionStatus.threadIsDone();
    }
}
