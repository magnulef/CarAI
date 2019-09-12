package game.ai;

import game.Handler;
import game.Renderer;
import game.Simulation;
import game.renderables.RewardGates;
import game.renderables.Track;
import java.util.ArrayList;
import java.util.List;

public class Evolution {

    private final Handler handler;

    private double topScore;
    private List<Simulation> currentGeneration;
    private boolean evolutionStarted;
    private final boolean singlePlayer;

    public Evolution(boolean singlePlayer) {
        this.handler = new Handler();
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
        currentGeneration.add(new Simulation(handler, null));
        startGeneration();
        return true;
    }

    private boolean preformEvolution() {
        if (currentGeneration.isEmpty()) {
            currentGeneration = initial(20);
        }

        startGeneration();
        runGeneration();
        List<Simulation> newGeneration = GenerationUtils.evolveGeneration(handler, 0.15, currentGeneration); //returns 5 to many
        Simulation best = GenerationUtils.getBestPerformer(currentGeneration);
        System.out.println("Best fitness: " + best != null ? best.getFitness() : "null");
        currentGeneration.forEach(Simulation::stop);
        clearHandler();
        currentGeneration.clear();
        currentGeneration = newGeneration;
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
                done = true;
            }

            //generationStatus();
        }
    }

    private boolean generationCheck() {
        for (Simulation simulation : currentGeneration) {
            if (!simulation.isDeadOrDone()) {
                return false;
            }
        }

        return true;
    }

    private void generationStatus() {
        System.out.println("NEW CHECK");
        for (Simulation simulation : currentGeneration) {
            System.out.println("Simulation: death: " + simulation.isDeadOrDone() + " running: " + simulation.isRunning());
        }
    }

    private List<Simulation> initial(int size) {
        List<Simulation> simulations = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            simulations.add(new Simulation(handler, null));
        }

        return simulations;
    }

    private void startGeneration() {
        if (currentGeneration.isEmpty()) {
            return;
        }

        currentGeneration.forEach(Simulation::start);
    }
}
