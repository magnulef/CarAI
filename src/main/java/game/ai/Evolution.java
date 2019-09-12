package game.ai;

import game.Handler;
import game.Renderer;
import game.Simulation;
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

        currentGeneration.add(new Simulation(handler, true));
        startGeneration();
        return true;
    }

    private boolean preformEvolution() {
        this.evolutionStarted = false;

        return false;
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
