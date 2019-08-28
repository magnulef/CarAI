package game;

import utils.Keyboard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private final boolean keyBoardEnabled;

    public KeyInput(boolean keyBoardEnabled) {
        this.keyBoardEnabled = keyBoardEnabled;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (keyBoardEnabled) {
            Keyboard.keydown[event.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (keyBoardEnabled) {
            Keyboard.keydown[event.getKeyCode()] = false;
        }
    }
}
