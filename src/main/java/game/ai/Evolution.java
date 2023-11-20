package game.ai;

import game.Handler;
import game.Renderer;
import game.Simulation;
import game.ai.evolution.EvolutionStatus;
import game.renderables.RewardGates;
import game.renderables.Track;
import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import utils.ImportInitialWeights;

public class Evolution {


    public static final int SIMULATIONS = 8;
    public static final int GENERATION_GROUP_SIZE = 100;
    private static final Track track = new Track();
    private static final RewardGates rewardGates = new RewardGates(true);

    private final Handler handler;

    private List<Simulation> currentGeneration;
    private boolean evolutionStarted;
    private final boolean singlePlayer;
    private final ThreadPoolExecutor executor;

    public Evolution(boolean singlePlayer) {
        this.handler = new Handler();
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(16);
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

        currentGeneration.add(new Simulation(0, handler, true));
        startGeneration();
        return true;
    }

    private boolean preformEvolution() {
        if (currentGeneration.isEmpty()) {
            currentGeneration = initial(SIMULATIONS, GENERATION_GROUP_SIZE);
        }

        startGeneration();
        runGeneration();
        AiStatus.generationComplete(currentGeneration);
        AiStatus.standardPrint();
        clearHandler();
        List<Simulation> newGeneration = GenerationUtils.evolve(executor, handler, currentGeneration);
        currentGeneration.clear();
        currentGeneration = newGeneration;
        GenerationStatus.restart();
        EvolutionStatus.standardPrint();
        return false;
    }

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
            if (GenerationStatus.allDone()) {
                done = true;
            }
        }
    }

    private List<Simulation> initial(int simulationSize, int generationGroupSize) {
        List<Simulation> simulations = new ArrayList<>();
        for (int i = 0; i < simulationSize; i++) {
            List<Car> cars = new ArrayList<>();
            for (int j = 0; j < generationGroupSize; j++) {
                cars.add(new Car(handler, ImportInitialWeights.getImportedWeights(), false, false, false, true));
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

        AiStatus.startGeneration();
        for (Simulation simulation : currentGeneration) {
            executor.execute(simulation);
        }
    }
}
