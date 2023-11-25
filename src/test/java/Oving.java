import org.junit.Test;

public class Oving {

    @Test
    public void test() {
        for (int i = 0; i <= 100; i++) {
            if (i % 3 == 0 && i >= 3) {
                System.out.print("Fizz");
            }

            if (i % 5 == 0 && i >= 5) {
                System.out.print("Buzz");
            } else if (i % 3 != 0) {
                System.out.print(i);
            }
        }
    }
}