package game.ai;

import game.Simulation;
import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.nd4j.linalg.api.ndarray.INDArray;
import utils.PrintUtils;
import utils.json.JSON;

public class AiStatus {

    private static double bestOfAllTime = 0;
    private static Map<String, INDArray> bestOfAllTimeWeight = null;
    private static boolean hasBeenPrinted = false;

    private static int generationCount = 0;
    private static double topFitness = 0;
    private static Car bestCar = null;
    private static double averageFitness = 0;

    private static List<Double> averages = new ArrayList<>();
    private static List<Double> tops = new ArrayList<>();

    private static long startTime = 0;
    private static double elapsedTime = 0;

    public static void startGeneration() {
        startTime = System.currentTimeMillis();
    }

    public static double getTopAvg() {
        if (tops.isEmpty()) {
            return 0.0;
        }

        double top = tops.get(tops.size() -1);
        return top - (top / 20);
    }

    public static void generationComplete(List<Simulation> generation) {
        double totalFitness = 0.0;
        double bestFitness = -1;
        int totalNumberOfCars = 0;
        for (Simulation simulation : generation) {
            for (Car car : simulation.getCars()) {
                totalFitness = totalFitness + car.getFitness();
                totalNumberOfCars++;

                if (car.getFitness() > bestFitness) {
                    bestFitness = car.getFitness();
                    bestCar = car;
                }
            }
        }

        topFitness = bestFitness;
        averageFitness = totalFitness / totalNumberOfCars;
        averages.add(averageFitness);
        tops.add(topFitness);
        generationCount++;
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

        if (topFitness > bestOfAllTime) {
            bestOfAllTime = topFitness;
            bestOfAllTimeWeight = bestCar.getWeight();
            hasBeenPrinted = false;
        }
    }

    public static void standardPrint() {
        System.out.println("\nGeneration Status: " + generationCount);
        System.out.println("Top: " + topFitness);
        System.out.println("Average: " + averageFitness);
        System.out.println("Generation Elapsed time: " + elapsedTime);

        if (bestOfAllTime > 450) {
            if (!hasBeenPrinted) {
                hasBeenPrinted = true;
                System.out.println(JSON.toJson(PrintUtils.print(bestOfAllTime, bestOfAllTimeWeight)));
            }
        }
    }
}
