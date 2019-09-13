import game.Handler;
import game.Simulation;
import game.renderables.car.Car;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class ThreadTest {

    @Test
    public void test() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        Handler handler = new Handler();
        Simulation simulation = new Simulation(
            1,
            Arrays.asList(
                new Car(handler, null, false, false, false, true)
            ), handler);
        //executor.getQueue().add(simulation);
        //assertEquals(executor.getQueue().size(), 1);
        executor.execute(simulation);
        assertEquals(executor.getActiveCount(), 1);
        //executor.
        while (true) {
            if (simulation.isRunning()) {
                System.out.println("Running");
                try {
                    Thread.sleep(1000);
                } catch (Exception exception) {

                }
            } else {
                System.out.println("Stopped");
                break;
            }
        }

    }
}
