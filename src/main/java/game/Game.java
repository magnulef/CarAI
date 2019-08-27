package game;

import game.renderables.Car;
import game.renderables.Edge;
import game.renderables.Track;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    private Thread thread;
    private boolean running = false;
    private Handler handler;

    public Game() {
        this.handler = new Handler();
        new Window(WIDTH, HEIGHT, "CarAI", this);
        this.addKeyListener(new KeyInput());

        this.handler.addGameObject(new Track());
        this.handler.addGameObject(new Car(handler));
        //this.handler.addGameObject(new Edge(500, 300, 500, 700));
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

    private void tick() {
        handler.tick();
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
        //graphics.setColor(Color.WHITE);
        //graphics.drawLine(100, 0, 200, 0);

        handler.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}
