package game;

import game.renderables.car.Car;
import java.util.Map;
import org.nd4j.linalg.api.ndarray.INDArray;

public class Simulation implements Runnable {

    private Thread thread;
    private boolean running = false;
    private final Car car;

    public Simulation(Handler handler, Map<String, INDArray> previousWeights) {
        this.car = new Car(
            handler,
            previousWeights,
            true,
            true,
            false,
            false
        );

        handler.addGameObject(car);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                //render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        car.tick();
        //handler.tick();
    }

}
