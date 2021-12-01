package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class ChangeCameraStrategy implements CollisionStrategy{

    private final GameObjectCollection gameObjects;
    private ChangeCameraSuplimentary changeCameraSuplimentary;

    public ChangeCameraStrategy(GameObjectCollection gameObjects,ChangeCameraSuplimentary changeCameraSuplimentary) {
        this.gameObjects = gameObjects;
        this.changeCameraSuplimentary = changeCameraSuplimentary;
    }

    @Override
    public void onCollision(GameObject first, GameObject second) {
        RemoveBrickStrategy removeBrickStrategy = new RemoveBrickStrategy(gameObjects);
        changeCameraSuplimentary.ChangeCamera();
        removeBrickStrategy.onCollision(first,second);
    }
}
