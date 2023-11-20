package game.ai.evolution;

import game.Handler;
import game.ai.AiStatus;
import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.List;
import utils.PrintUtils;

public class TopTask implements Runnable {

    private final List<Car> cars;
    private final Handler handler;
    private final int top;

    public TopTask(Handler handler, int top, List<Car> cars) {
        this.handler = handler;
        this.top = top;
        this.cars = cars;
    }

    @Override
    public void run() {
        List<Car> newCars = new ArrayList<>();

        double topAvg = AiStatus.getTopAvg();
        for (int i = 0; i < top; i++) {
            Car car = cars.get(i);
            if (car.getFitness() > topAvg) {
                newCars.add(car.clone(handler, true));
            } else {
                newCars.add(car.clone(handler, false));
            }
        }

        EvolutionStatus.addNewCars(newCars);
        EvolutionStatus.threadIsDone();
    }
}
