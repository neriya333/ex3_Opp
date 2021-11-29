package src;

import danogl.util.Counter;
import src.brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.Brick;
import src.gameobjects.GraphicLifeCounter;
import src.gameobjects.Paddle;

import java.util.Random;

public class BrickerGameManager extends GameManager{

    public danogl.util.Counter livesCounter;
    public danogl.util.Counter N_BRICKS = new Counter(0);
    public static final float BORDER_WIDTH = 7;
    public static final int LIVE = 4;
    private static final float BALL_SPEED = 200;
    private static GameObject ball;
    private Vector2 windowDimentios;
    private WindowController windowController;


    public BrickerGameManager(String windowTitle, Vector2 dim_vec){
        super(windowTitle, dim_vec);
    }


    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimentios = windowController.getWindowDimensions();

        // creating ball
        Renderable ballImage = imageReader.readImage("assets\\ball.png",true);
        Sound collisionSound = soundReader.readSound("assets\\blop_cut_silenced.wav");
        createGameObjectBall(windowDimentios, ballImage, collisionSound);

        // create paddle

        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);

        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(100,15), paddleImage, inputListener, windowDimentios);
        gameObjects().addGameObject(paddle);
        paddle.setCenter(new Vector2(windowDimentios.x()/2,windowDimentios.y()-20));

        // create walls and ceiling with WIDTH as their width - may cause hiccups(ball too fast, pass boundary)
        createGameObjectBounderies(windowDimentios);

        // add background image
        createGameObjectBackgraound(imageReader, windowController, "assets/DARK_BG2_small.jpeg");

        // load bricks' image and define how many rows and columns of bricks they will be, as well as the bricks height
        Renderable brickImage = imageReader.readImage("assets\\brick.png",false);
        int BRICK_HEIGHT = 15, NUMBER_OF_BRICKS = 8, N_ROWS=5;

        // creates NUMBER_OF_BRICKS*N_ROWS brick objects, looking link brickImage, with height BRICK_HEIGHT
        createGameObjectBricks(windowDimentios, brickImage, BRICK_HEIGHT, NUMBER_OF_BRICKS, N_ROWS);

        // create manager of graphic-hearts and add it to the game objects
        int HEART_SHAPE = 15;
        Vector2 leftDownCorner = new Vector2(0,windowDimentios.y()),
                heartShape = new Vector2(HEART_SHAPE, HEART_SHAPE);
        Renderable heartImage = imageReader.readImage("assets/heart.png", true);

        GameObject graphicHeartsManager = new GraphicLifeCounter(leftDownCorner, heartShape, livesCounter, heartImage,gameObjects(),LIVE);
        gameObjects().addGameObject(graphicHeartsManager);
//        graphicHeartsManager.setCenter(); is there a need for this?


        GameObject heart = new GameObject(Vector2.ZERO, new Vector2(HEART_SHAPE,HEART_SHAPE),
                imageReader.readImage("assets/heart.png", false));
        heart.setCenter(Vector2.ZERO.add(new Vector2(10,350)));
        gameObjects().addGameObject(heart, Layer.BACKGROUND);

        Vector2 heartPlacements = new Vector2(0,windowDimentios.y() - 50);
        GameObject hearts = new GraphicLifeCounter(Vector2.ZERO, heartPlacements, livesCounter, heartImage, this.gameObjects(), livesCounter.value());
        gameObjects().addGameObject(hearts);
//
//        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(graphicHeartsPlacements,
//                graphicHeartsShape,
//                LIFE_COUNTER,
//                heartImage,gameObjects(),LIFE_COUNTER.value());


    }

    private void createGameObjectBall(Vector2 windowDimentios, Renderable ballImage, Sound collisionSound) {

        ball = new Ball(Vector2.ZERO, new Vector2(20,20), ballImage, collisionSound);
        float ballVelY = BALL_SPEED;
        float ballVelX = BALL_SPEED;
        // the directions are random
        Random random = new Random();
        if(random.nextBoolean()){
            ballVelY *=-1;
        }
        if(random.nextBoolean()){
            ballVelX *=-1;
        }

        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        ball.setCenter(windowDimentios.mult(0.5f));
        gameObjects().addGameObject(ball);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        checkForGameEnd();
    }

    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if(ballHeight < 0){
            prompt = "You win!";
        }
        else if (ballHeight > windowDimentios.y()) {
            if (livesCounter.value() == 0){
                prompt = "You Lose!";
            }
            livesCounter.decrement();
        }
        if(!prompt.isBlank()){
            prompt += "Play Again?";
            if(windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
            }
            else  windowController.closeWindow();
        }
    }

    private void createGameObjectBricks(Vector2 windowDimentios, Renderable brickImage, int BRICK_HEIGHT, int NUMBER_OF_BRICKS, int N_ROWS) {
        /*
            // creates NUMBER_OF_BRICKS*N_ROWS brick objects, looking link brickImage, with height BRICK_HEIGHT
            // so that the bricks will fill windowDimentios.x() and start from the top of te screen.
            // note that they are distanced from the borders as collision cause them to pop
         */

        // width - borders - distance between bricks divided by num of bricks:
        int brick_size = (int) (windowDimentios.x()-(NUMBER_OF_BRICKS -1) - 2 * BORDER_WIDTH)/ NUMBER_OF_BRICKS;
        Vector2 brickShape = new Vector2(brick_size, BRICK_HEIGHT);

        // for row
        for (int brick_row = 0; brick_row < N_ROWS; brick_row++) {
            int brick_x_placement = (int)BORDER_WIDTH,
                brick_y_placement = (int)BORDER_WIDTH + (BRICK_HEIGHT +1) * brick_row;
            // for column
            for (int n_brick = 0; n_brick <= NUMBER_OF_BRICKS; n_brick++) {

                GameObject new_brick = new Brick(new Vector2(brick_x_placement, brick_y_placement),
                         brickShape, brickImage, new CollisionStrategy(gameObjects()));
                 gameObjects().addGameObject(new_brick,Layer.STATIC_OBJECTS);
                 new_brick.setCenter(new Vector2(brick_x_placement - brick_size / 2.f, brick_y_placement+ BRICK_HEIGHT /2.f));
                 brick_x_placement += brick_size + 1; // as I take into account the distance between blocks
            }
        }
    }

    /*
    straight forwards - from a path ,open image and add it to the background as an image
     */
    private void createGameObjectBackgraound(ImageReader imageReader, WindowController windowController, String path) {
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                imageReader.readImage(path,false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /*
        create the walls and ceiling. used at initialize game. vary start forwards.

        WARNING: if the ball pass the boundary, there will be no collision and the ball will go missing,
        1 way to solve - widening the boundary, others( slow ball, more frequent frames or combination of the last 3)
     */
    private void createGameObjectBounderies(Vector2 windowDimentios) {
        // left wall
        gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                new Vector2(BORDER_WIDTH, windowDimentios.y()), null));
        // right wall
        gameObjects().addGameObject(new GameObject(new Vector2(windowDimentios.x()-BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, windowDimentios.y()), null));
        // ceiling
//        gameObjects().addGameObject(new GameObject(Vector2.ZERO,
//                new Vector2(windowDimentios.x(), BORDER_WIDTH), null));
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bouncing srs.gameobjects.Ball",new Vector2(700, 500)).run();
    }
}


