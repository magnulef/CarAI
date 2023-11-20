package utils;

import java.util.ArrayList;
import java.util.List;

public class Pascal {

    public static List<List<Integer>> getPascal(int rows) {
        List<List<Integer>> pascal = new ArrayList<>();
        if (rows == 0) {
            return pascal;
        }

        firstRow(pascal);
        if (rows == 1) {
            return pascal;
        }

        secondRow(pascal);
        if (rows == 2) {
            return pascal;
        }

        for (int i = 1; i < rows; i++) {
            List<Integer> newRow = new ArrayList<>();
            newRow.add(1);

            for (int j = 0; j < pascal.get(i).size(); j++) {
                List<Integer> holder = pascal.get(i);
                if (j == holder.size() - 1) {
                    break;
                }
                newRow.add(holder.get(j) + holder.get(j + 1));
            }

            newRow.add(1);
            pascal.add(newRow);
        }

        return pascal;
    }

    private static void firstRow(List<List<Integer>> pascal) {
        List<Integer> first = new ArrayList<>();
        first.add(1);
        pascal.add(first);
    }

    private static void secondRow(List<List<Integer>> pascal) {
        List<Integer> second = new ArrayList<>();
        second.add(1);
        second.add(1);
        pascal.add(second);
    }
}
