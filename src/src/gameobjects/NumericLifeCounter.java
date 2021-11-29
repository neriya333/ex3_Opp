package src.gameobjects;

import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class NumericLifeCounter extends danogl.GameObject{
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public NumericLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    @Override
    public void update(float deltaTime) {

    }
}
