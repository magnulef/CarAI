import org.junit.Assert;
import org.junit.Test;
import utils.Pascal;
import utils.StringUtils;

import java.util.List;

public class Anagram {

    @Test
    public void test1() {
        boolean result = StringUtils.isAnagramWithSort("anagram", "margana");

        Assert.assertTrue(result);
    }

    @Test
    public void test2() {
        boolean result = StringUtils.isAnagramWithSort("anagramm", "marganaa");

        Assert.assertFalse(result);
    }

    @Test
    public void test3() {
        boolean result = StringUtils.isAnagramWithSort("Hello", "hello");

        Assert.assertTrue(result);
    }

    @Test
    public void test4() {
        boolean result = StringUtils.isAnagramWithSort("anaxram", "margana");

        Assert.assertFalse(result);
    }

    @Test
    public void test5() {
        boolean result = StringUtils.isAnagramWithSort("anagram", "marxana");

        Assert.assertFalse(result);
    }

    @Test
    public void test7() {
        for (List<Integer> row : Pascal.getPascal(20)) {
            System.out.println(row);
        }
    }
}
