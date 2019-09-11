package game;

import utils.Keyboard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    public KeyInput() {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        Keyboard.keydown[event.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent event) {
        Keyboard.keydown[event.getKeyCode()] = false;
    }
}
