package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class CollisionStrategy {

    private final GameObjectCollection gameObjects;

    public CollisionStrategy(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
    }
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        gameObjects.removeGameObject(thisObj);
    }
}