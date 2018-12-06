package Game;

import GameObjects.game;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class SpaceShooter extends Application {

    private static game game;

    private long lastUpdate;
    private static int time;
    private static int updateRate = 120;
    private static Stage pStage;

    @Override
    public void start(Stage primaryStage) {
        pStage = primaryStage;
        lastUpdate = 0L;
        time = 0;

        final int dimX = 1200;
        final int dimY = 750;

        game = new game(dimX, dimY);

        new AnimationTimer() {
            public void handle(long now) {

                long deltaTime = (now - lastUpdate)/1000000;
                if(deltaTime >= (1000/updateRate) ){

                    game.moveGameObjects();
                    lastUpdate = now;
                    time++;
                }
            }
        }.start();

        primaryStage.setScene(game.getGameScene());
//        primaryStage.getIcons().add(new Image("/images/pedobear.jpg"));
        primaryStage.setTitle("Space Shooter");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static int getTime() {
        return time;
    }

    public static int getUpdateRate() {
        return updateRate;
    }

    public static void updateScene(){
        pStage.setScene(game.getGameScene());
    }
}
