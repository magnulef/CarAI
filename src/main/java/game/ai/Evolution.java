package game.ai;

import game.GenerationStatus;
import game.Handler;
import game.Renderer;
import game.Simulation;
import game.renderables.RewardGates;
import game.renderables.Track;
import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Evolution {

    private final Handler handler;

    private double topScore;
    private List<Simulation> currentGeneration;
    private boolean evolutionStarted;
    private final boolean singlePlayer;
    private final ThreadPoolExecutor executor;

    public Evolution(boolean singlePlayer) {
        this.handler = new Handler();
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        this.topScore = 0;
        this.currentGeneration = new ArrayList<>();
        new Renderer(handler);
        this.evolutionStarted = false;
        this.singlePlayer = singlePlayer;
    }

    public void run() {
        clearHandler();
        boolean isRunningSinglePlayerMode = singlePlayerMode();
        if (isRunningSinglePlayerMode) {
            return;
        }

        if (!this.evolutionStarted) {
            this.evolutionStarted = true;
        }

        while (this.evolutionStarted) {
            preformEvolution();
        }
    }

    private boolean singlePlayerMode() {
        if (!singlePlayer) {
            return false;
        }

        //currentGeneration.add(new Simulation(handler, true));
        currentGeneration.add(new Simulation(0, handler, null));
        startGeneration();
        return true;
    }

    private boolean preformEvolution() {
        if (currentGeneration.isEmpty()) {
            currentGeneration = initial(4, 50);
        }

        startGeneration();
        runGeneration();
        clearHandler();
        List<Simulation> newGeneration = GenerationUtils.evolveGeneration(handler, 0.10, currentGeneration);
        Car best = GenerationUtils.getBestPerformer(currentGeneration);
        System.out.println("Best fitness: " + best != null ? best.getFitness() : "null");
        System.out.println("Average fitness: " + GenerationUtils.getAverageFitness(currentGeneration));
        //handlePreviousThreads(currentGeneration);
        //clearHandler();
        currentGeneration.clear();
        currentGeneration = newGeneration;
        GenerationStatus.restart();
        return false;
    }

    Track track = new Track();
    RewardGates rewardGates = new RewardGates(true);

    private void clearHandler() {
        handler.clear();
        handler.addGameObject(track);
        handler.addGameObject(rewardGates);
    }

    private void runGeneration() {
        boolean done = false;
        while (!done) {
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {

            }
            if (generationCheck()) {
                System.out.println("Generation is done!");
                done = true;
            }
        }
    }

    private boolean generationCheck() {
        if (!GenerationStatus.allDone()) {
            return false;
        }

        return true;
    }

    private List<Simulation> initial(int simulationSize, int generationGroupSize) {
        List<Simulation> simulations = new ArrayList<>();
        for (int i = 0; i < simulationSize; i++) {
            List<Car> cars = new ArrayList<>();
            for (int j = 0; j < generationGroupSize; j++) {
                cars.add(new Car(handler, null, false, false, false, true));
            }
            simulations.add(new Simulation(i ,cars, handler));
        }

        return simulations;
    }

    private void startGeneration() {
        if (currentGeneration.isEmpty()) {
            return;
        }

        GenerationStatus.setThreadCount(currentGeneration.size());

        for (Simulation simulation : currentGeneration) {
            executor.execute(simulation);
        }
    }
}
