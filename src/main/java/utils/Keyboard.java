package utils;

public class Keyboard {

    public static boolean[] keydown = new boolean[255];

    public static void alldown() {
        for(boolean value : keydown) {
            value = false;
        }
    }
}