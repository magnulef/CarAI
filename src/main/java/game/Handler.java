package game;

import game.renderables.GameObject;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

    private final LinkedList<GameObject> gameObjects = new LinkedList<>();

    public void render(Graphics graphics) {
        for(int i = 0; i < gameObjects.size(); i++) {
            GameObject tempObject = gameObjects.get(i);

            tempObject.render(graphics);
        }
    }

    public synchronized void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    public synchronized void removeGameObject(GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }

    public synchronized void clear() {
        this.gameObjects.clear();
    }
}
