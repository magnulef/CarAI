package game;

import game.renderables.GameObject;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class Handler {

    private final LinkedList<GameObject> gameObjects = new LinkedList<>();

    public synchronized void render(Graphics graphics) {
        for(int i = 0; i < gameObjects.size(); i++) {
            GameObject tempObject = gameObjects.get(i);

            tempObject.render(graphics);
        }
    }

    public synchronized void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    public synchronized void addGameObjects(List<GameObject> gameObjects) {
        this.gameObjects.addAll(gameObjects);
    }

    public synchronized void removeGameObject(GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }

    public synchronized void clear() {
        this.gameObjects.clear();
    }
}
