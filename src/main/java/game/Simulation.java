package game;

import game.renderables.car.Car;
import java.util.Map;
import org.nd4j.linalg.api.ndarray.INDArray;

public class Simulation implements Runnable {

    private Thread thread;
    private boolean running = false;
    private final Car car;
    private long startTime;

    public Simulation(Handler handler, Map<String, INDArray> previousWeights) {
        this.car = new Car(
            handler,
            previousWeights,
            false,
            false,
            false,
            true
        );

        handler.addGameObject(car);
    }

    public Simulation(Handler handler, boolean keyBoardEnabled) {
        this.car = new Car(
            handler,
            null,
            keyBoardEnabled,
            true,
            false,
            false
        );

        handler.addGameObject(car);
    }

    public synchronized double getFitness() {
        return this.car.getFitness();
    }

    public synchronized Map<String, INDArray> getWeights() {
        return this.car.getWeights();
    }

    public synchronized void start() {
        if (running) {
            return;
        }

        thread = new Thread(this);
        thread.start();
        running = true;
        startTime = System.currentTimeMillis();
    }

    public synchronized void stop() {
        try {
            thread.interrupt();
            //thread.stop();
            //thread.join();
            running = false;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized boolean isDeadOrDone() {
        if (!running) {
            return false;
        }

        if (car.isDead()) {
            return true;
        }

        //30000 = 30sec
        if (startTime + 5000 < System.currentTimeMillis()) {
            return true;
        }

        return false;
    }

    @Override
    public void run() {
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
        }
        stop();
    }

    private void tick() {
        car.tick();
    }
}
