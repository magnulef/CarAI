import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import library.json.JSON;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestSorting {

    @Test
    public void test() {
        List<Double> array = new ArrayList<>();
        array.add(1.5);
        array.add(1.3);
        array.add(1.0);
        array.add(1.1);
        array.add(1.4);
        array.add(1.2);

        Collections.sort(array);
        System.out.println(JSON.toJson(array));
        //assertEquals(array.get(0), 1.0);
    }

    @Test
    public void tes1() {
        List<TestClass> array = new ArrayList<>();
        array.add(new TestClass(1.0));
        array.add(new TestClass(1.1));
        array.add(new TestClass(1.2));
        array.add(new TestClass(1.3));
        array.add(new TestClass(1.4));
        array.add(new TestClass(1.5));

        Collections.sort(array, new Comparator<TestClass>(){
            @Override
            public int compare(TestClass nr1, TestClass nr2){
                if (nr1.getNumber() < nr2.getNumber()) return -1;
                if (nr1.getNumber() > nr2.getNumber()) return 1;
                return 0;
            }
        });

        System.out.println(JSON.toJson(array));
    }

    @Test
    public void test2() {
        List<Integer> array = new ArrayList<>();
        array.add(0);
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);
        array.add(6);
        array.add(7);
        array.add(8);
        array.add(9);

        for (int i = 5; i < 10; i++) {
            System.out.println(array.get(i));
        }
    }
}
