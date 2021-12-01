package src.brick_strategies;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.util.Counter;
import src.BrickerGameManager;

import java.util.Random;

public class BrickerStrategyFactory {

    private final int numOfStrategies = 2;
    private final CollisionStrategy[] possibleStrategy = new CollisionStrategy[numOfStrategies];

    public BrickerStrategyFactory(GameObjectCollection gameObjects,
                                  GameObject mainBall, BrickerGameManager manager,
                                  WindowController windowController) {

        possibleStrategy[0] = new RemoveBrickStrategy(gameObjects);
        possibleStrategy[1] = new ChangeCameraStrategy(gameObjects,new ChangeCameraSuplimentary(mainBall,manager,windowController, manager.cameraCounter));

    }

    public CollisionStrategy getStrategy() {
        //choose randomly between the possible brick strategies
        Random rand = new Random();
        return possibleStrategy[rand.nextInt(numOfStrategies)];
    }
}
