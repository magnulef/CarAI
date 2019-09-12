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

    public Evolution() {
        this.handler = new Handler();
        this.topScore = 0;
        this.currentGeneration = new ArrayList<>();
        new Renderer(handler);
    }

    public void run() {
        boolean isGenerationDone = false;
        boolean evolutionInProgress = false;
        while (true) {
            if (!evolutionInProgress) {
                if (topScore > 1000) {
                    System.out.println("OVER 1000");
                    break;
                }

                if (currentGeneration.isEmpty()) {
                    currentGeneration = initial(1);
                }

                startGeneration();
                evolutionInProgress = true;
            }
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
