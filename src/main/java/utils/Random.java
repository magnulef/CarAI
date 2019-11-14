package utils;

import java.util.Stack;

public class Random {

    private final static Stack<Double> current = new Stack<>();
    private final static Stack<Double> previous = new Stack<>();
    private final static int MAX_COUNT = 50000;
    private static int counter = MAX_COUNT;

    static {
        for (int i = 0; i < MAX_COUNT; i++) {
            current.add(Math.random());
        }
    }

    public static synchronized double random() {
        if (counter == 0) {
            return getFromPrevious();
        }

        return getFromCurrent();
    }

    private static double getFromPrevious() {
        double number = previous.pop();
        current.add(number);
        if (current.size() == MAX_COUNT) {
            counter = MAX_COUNT;
        }

        return number;
    }

    private static double getFromCurrent() {
        double number = current.pop();
        previous.add(number);
        counter--;

        return number;
    }
}
