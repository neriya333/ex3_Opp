package src.gameobjects;

import danogl.util.Counter;
import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;
//    private Counter counter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */

//    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
//                 CollisionStrategy collisionStrategy , danogl.util.Counter counter) {
//        super(topLeftCorner, dimensions, renderable);
//        this.collisionStrategy = collisionStrategy;
//        this.counter = counter;
//        counter.increment();
//    }

    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }


    public void onCollisionEnter(GameObject other, Collision collision, danogl.util.Counter counter) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
//        counter.decrement();
    }

}
