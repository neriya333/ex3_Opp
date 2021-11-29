package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import src.gameobjects.Brick;

public interface CollisionStrategy {
    void onCollision(GameObject first, GameObject second);

}
