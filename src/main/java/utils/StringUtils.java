package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StringUtils {

    public static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }

        return toMap(a.toLowerCase()).equals(toMap(b.toLowerCase()));
    }

    public static boolean isAnagramWithSort(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }

        return sort(a).equals(sort(b));
    }

    private static String sort(String s) {
        char[] charsA = s.toLowerCase().toCharArray();
        Arrays.sort(charsA);
        return new String(charsA);
    }

    private static Map<Character, Integer> toMap(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character key = s.charAt(i);
            if (map.containsKey(key)) {
                Integer value = map.get(key);
                map.put(key, value + 1);
            } else {
                map.put(key, 1);
            }
        }

        return map;
    }
}
