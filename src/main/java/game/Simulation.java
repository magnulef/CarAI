package game;

import game.ai.GenerationStatus;
import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.nd4j.linalg.api.ndarray.INDArray;

public class Simulation implements Runnable {

    private final int number;
    private boolean running = false;
    private final List<Car> cars;
    private long startTime;
    private boolean singlePlayer = false;

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

        this.cars.forEach(car -> handler.addGameObject(car));
    }

    public Simulation(int number, Handler handler, boolean keyBoardEnabled) {
        this.number = number;
        Car car = new Car(
            handler,
            null,
            keyBoardEnabled,
            true,
            true,
            false
        );

        this.singlePlayer = true;
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

        //if (!singlePlayer) { 15000
        if (!singlePlayer && startTime + 360000 < System.currentTimeMillis()) {
            return true;
        }

        for (Car car : cars) {
            if (!car.isDead()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void run() {
        start();
        long lastTime = System.nanoTime();
        double amountOfTicks = 20;
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
