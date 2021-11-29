package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class CollisionStrategy {


    private GameObjectCollection gameObjects;

    public CollisionStrategy(GameObjectCollection gameObjects) {

        this.gameObjects = gameObjects;
    }

    public void onCollision(GameObject thisObj, GameObject otherobj){
        gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
    }
}
