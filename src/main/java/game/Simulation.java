package game;

import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.nd4j.linalg.api.ndarray.INDArray;

public class Simulation implements Runnable {

    //private Thread thread;
    private final int number;
    private boolean running = false;
    private final List<Car> cars;
    private long startTime;

    public Simulation(int number, Handler handler, List<Map<String, INDArray>> weights) {
        this.number = number;
        this.cars = new ArrayList<>();
        for (Map<String, INDArray> weight : weights) {
            Car car = new Car(handler, weight, false, true, false, true);
            this.cars.add(car);
            handler.addGameObject(car);
        }
    }

    public Simulation(int number, List<Car> cars, Handler handler) {
        this.number = number;
        this.cars = cars;
        cars.forEach(car -> handler.addGameObject(car));
    }

    public Simulation(int number, Handler handler, boolean keyBoardEnabled) {
        this.number = number;
        Car car = new Car(
            handler,
            null,
            keyBoardEnabled,
            true,
            false,
            false
        );

        this.cars = new ArrayList<>();
        this.cars.add(car);
        handler.addGameObject(car);
    }

    public synchronized int getNumber() {
        return number;
    }

    public synchronized List<Car> getCars() {
        return this.cars;
    }

    public synchronized Car getFittestCar() {
        double bestFitness = -1;
        Car bestCandidate = null;
        for (Car car : cars) {
            double fitness = car.getFitness();
            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestCandidate = car;
            }
        }

        return bestCandidate;
    }

    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        startTime = System.currentTimeMillis();
    }

    public synchronized void stop() {
        try {
            running = false;
            GenerationStatus.threadIsDone();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public boolean isDeadOrDone() {
        if (!running) {
            return true;
        }

        //30000 = 30sec
        if (startTime + 5000 < System.currentTimeMillis()) {
            return true;
        }

        for (Car car : cars) {
            if (!car.isDead()) {
                return false;
            }
        }

        return false;
    }

    @Override
    public void run() {
        start();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }

            if (isDeadOrDone()) {
                break;
            }
        }
        stop();
    }

    private void tick() {
        cars.forEach(Car::tick);
    }
}
