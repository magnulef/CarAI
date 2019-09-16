package game;

import java.util.ArrayList;
import java.util.List;

public class GenerationStatus {

    private static int threadCount = 0;
    private static List<Boolean> doneThreads = new ArrayList<>();

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
}
