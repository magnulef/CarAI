package game.ai.evolution;

import game.ai.Evolution;
import game.renderables.car.Car;
import java.util.ArrayList;
import java.util.List;

public class EvolutionStatus {

    private static int threadCount = 0;
    private static List<Boolean> doneThreads = new ArrayList<>();
    private final static List<Car> newCars = new ArrayList<>();

    private static long startTime = 0;
    private static int targetSize = 0;
    private static final int evolutionSize = Evolution.SIMULATIONS * Evolution.GENERATION_GROUP_SIZE;

    public static void startEvolution() {
        startTime = System.currentTimeMillis();
        setTargetSize();
    }

    private static void setTargetSize() {
        if (newCars.size() > 0) {
            targetSize = evolutionSize * 2;
        } else {
            targetSize = evolutionSize;
        }
    }

    public synchronized static int getTargetSize() {
        return targetSize;
    }

    public static void standardPrint() {
        System.out.println("\nEvolution Started");
        System.out.println("Evolution Elapsed time: " + (System.currentTimeMillis() - startTime) / 1000);
    }

    public synchronized static void addNewCars(List<Car> cars) {
        newCars.addAll(cars);
    }

    public synchronized static void addNewCar(Car car) {
        newCars.add(car);
    }

    public synchronized static List<Car> getNewCars() {
        return newCars;
    }

    public static synchronized void setThreadCount(int count) {
        threadCount = count;
    }

    public static synchronized void threadIsDone() {
        doneThreads.add(true);
    }

    public static synchronized boolean allDone() {
        return doneThreads.size() == threadCount;
    }

    public static synchronized void restart() {
        doneThreads.clear();
        threadCount = 0;
    }

    public static synchronized void remove(List<Car> cars) {
        newCars.removeAll(cars);
    }
}
