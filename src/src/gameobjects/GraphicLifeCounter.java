package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.Component;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends danogl.GameObject{
    private int currentLife;
    private Counter livesCounter;
    private GameObjectCollection gameObjectsCollection;

    /**
     *
     *
     * @param widgetTopLeftCorner - top left corner of left most life widgets. Other widgets will be displayed to its right, aligned in height.
     * @param widgetDimensions - dimensions of widgets to be displayed.
     * @param livesCounter - global lives counter of game.
     * @param widgetRenderable - image to use for widgets.
     * @param gameObjectsCollection - global game object collection managed by game manager.
     * @param numOfLives - global setting of number of lives a player will have in a game.
     */
    public GraphicLifeCounter(danogl.util.Vector2 widgetTopLeftCorner, danogl.util.Vector2 widgetDimensions,
                       danogl.util.Counter livesCounter, danogl.gui.rendering.Renderable widgetRenderable,
                       danogl.collisions.GameObjectCollection gameObjectsCollection, int numOfLives){

        
        super(widgetTopLeftCorner, widgetDimensions, null);

        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        currentLife = livesCounter.value();

        for (int cur_life = 0; cur_life < numOfLives; cur_life++) {
            Vector2 location = new Vector2(widgetTopLeftCorner.x() + cur_life * widgetDimensions.x(), widgetDimensions.y());
            GameObject heart = new GameObject(location, location.add(widgetDimensions.mult(0.5f)), widgetRenderable);
            setCenter(location.add(widgetDimensions.mult(0.5f)));
            gameObjectsCollection.addGameObject(heart, Layer.BACKGROUND);
        }
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (currentLife < livesCounter.value()){
            removeLastHeart();
        }
    }

    private void removeLastHeart(){
        int counter = 0;
        currentLife--;
        for (GameObject heart : gameObjectsCollection.objectsInLayer(Layer.BACKGROUND)) {
            counter++;
            if (counter>currentLife){
                gameObjectsCollection.removeGameObject(heart);
            }
        }
    }


}
