package jvm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Intellij IDEA.
 *
 * @author Eric Cui
 * @since 2018/3/15 23:20
 *     <p>
 */
@RunWith(JUnit4.class)
public class DistinctListTest {

    @Test
    public void test() {
        List<Integer> lista = Arrays.asList(23, 11, 18, 20);
        List<Integer> listb = Arrays.asList(5, 33, 15, 22);

        TreeMap<Integer, Integer> sortMap = new TreeMap<>((a, b) -> b - a);
        lista.forEach(e -> sortMap.put(e, 0));
        listb.forEach(e -> sortMap.put(e, 0));
        sortMap.keySet().forEach(System.out::println);
        System.out.println(sortMap);
    }
}
