package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class RemoveBrickStrategy implements CollisionStrategy{

    private final GameObjectCollection gameObjects;

    public RemoveBrickStrategy(GameObjectCollection gameObjects) {

        this.gameObjects = gameObjects;
    }

    @Override
    public void onCollision(GameObject first, GameObject second) {
        gameObjects.removeGameObject(first, Layer.STATIC_OBJECTS);
    }
}
