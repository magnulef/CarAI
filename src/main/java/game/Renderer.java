package game;

import game.renderables.RewardGates;
import game.renderables.Track;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Renderer extends Canvas implements Runnable {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    private Thread thread;
    private boolean running = false;
    private final Handler handler;
    private final Window1 window;

    public Renderer() {
        this.handler = new Handler();
        this.window = new Window1(WIDTH, HEIGHT, "CarAI", this);
        this.start();
        this.addKeyListener(new KeyInput());
        this.handler.addGameObject(new Track());
        this.handler.addGameObject(new RewardGates(false));
        Simulation simulation = new Simulation(handler, null);
        simulation.start();

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
                //tick();
                delta--;
            }
            if (running) {
                render();
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

    private void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }
}
