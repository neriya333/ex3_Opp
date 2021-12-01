package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import src.BrickerGameManager;

public class ChangeCameraSuplimentary {

    private GameObject objToFollow;
    private WindowController windowController;
    private Counter collisionTillNormal;
    private BrickerGameManager manager;
    private final int BASIC_VAL_COLLISION_TILL_NORMAL = 4;

    public ChangeCameraSuplimentary(GameObject objToFollow, BrickerGameManager manager, WindowController windowController, Counter CollisionToNormal) {
        this.objToFollow = objToFollow;
        this.manager = manager;
        this.windowController = windowController;
        collisionTillNormal = CollisionToNormal;
    }

    public void ChangeCamera(){
        if (collisionTillNormal.value()==0) {
            manager.setCamera(new Camera(objToFollow, objToFollow.getCenter(), windowController.getWindowDimensions().mult(4.f),
                    windowController.getWindowDimensions()));
            collisionTillNormal.increaseBy(4);
        }
    }
}
