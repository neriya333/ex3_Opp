package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class CollisionStrategyClass {

    private final GameObjectCollection gameObjects;
    CollisionStrategy strategy;

    public CollisionStrategyClass(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
        strategy = new RemoveBrickStrategy(gameObjects);
    }
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        strategy.onCollision(thisObj,otherObj);
    }
}