package GameObjects;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import static Game.SpaceShooter.*;

public class game {

    private static player player;

    private static ArrayList<asteroid> asteroids;
    private static ArrayList<String> input;

    private static double dimX;
    private static double dimY;

    private double mouseX;
    private double mouseY;

    private static Scene gameScene;
    private static Pane gameBox;
    private static ImageView background;
    private static HBox optionBox;
    private static Label debugLabel;


    public game(double x, double y) {
        player = new player(0,0);
        asteroids = new ArrayList<>();
        input = new ArrayList<>();
        dimX = x;
        dimY = y;

        BorderPane root = new BorderPane();
        gameBox = new Pane();
        optionBox = new HBox();
        optionBox.setStyle("-fx-background-color: #ffffff;");
        debugLabel = new Label();

        Image back = new Image("/images/Background.png",x,y,false,false);
        background = new ImageView(back);
        background.setX(0);
        background.setY(0);

        spawnAsteroid(400,250);
        spawnAsteroid(550,250);

        gameBox.getChildren().add(background);
        gameBox.getChildren().addAll(asteroids);
        gameBox.getChildren().addAll(player);
        gameBox.getChildren().addAll(player.getCANNON());

        Label debug = getDebugLabel();
        optionBox.getChildren().add(debug);

        gameScene = new Scene(root);

//        graphicsContext = canvas.getGraphicsContext2D();
//
//
//        MediaPlayer music = new MediaPlayer(new Media(getClass().getResource("/sounds/steam_monster_summer_game.mp3").toString()));
//        music.setOnEndOfMedia(() -> music.seek(Duration.ZERO));
//        music.setVolume(0.1);
//        music.play();
//
//        VBox musicBox = new VBox();
//        Label musicLabel = new Label("Music volume");
//
//        Slider musicVolumeSlider = new Slider(0,0.5,0.1);
//        musicVolumeSlider.setMaxWidth(200);
//        musicVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
//            music.setVolume((Double) newValue);
//        });
//
//        musicBox.getChildren().addAll(musicLabel,musicVolumeSlider);
//        optionBox.getChildren().add(musicBox);

        gameBox.setOnMouseClicked(this::mouseClick);

        gameScene.setOnKeyPressed(event -> {
            String keyInput = event.getCode().toString();
            System.out.println(keyInput + " was pressed!");
            if (!input.contains(keyInput)){
                input.add(keyInput);
            }
        });

        gameScene.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            player.rotateCannon(mouseX,mouseY);
        });

        gameScene.setOnKeyReleased(event -> {
            String keyInput = event.getCode().toString();
            input.remove(keyInput);
        });
        root.setCenter(gameBox);
        root.setTop(optionBox);
    }

    public Scene getGameScene(){
        return gameScene;
    }

    private void updateGameScene(){
        BorderPane root = new BorderPane();
        gameScene.setRoot(root);
        gameBox.getChildren().clear();
        gameBox.getChildren().add(background);
        gameBox.getChildren().addAll(asteroids);
        gameBox.getChildren().addAll(player);
        gameBox.getChildren().addAll(player.getCANNON());
        root.setCenter(gameBox);
        root.setTop(optionBox);
        updateScene();
    }

    public void moveGameObjects() {
        moveCharacter();
        debugLabel.setText(getDebugInfo());
    }

    private void moveCharacter(){
        boolean w = input.contains("W");
        boolean a = input.contains("A");
        boolean s = input.contains("S");
        boolean d = input.contains("D");
        boolean ctrl = input.contains("CONTROL");

        if(w){
            if(!ctrl){
                player.addVelocity(0,-0.01);
            } else {
                player.addVelocity(0,-0.001);
            }
        }
        if(a){
            if(!ctrl){
                player.addVelocity(-0.01,0);
            } else {
                player.addVelocity(-0.001,0);
            }
        }
        if(s){
            if(!ctrl){
                player.addVelocity(0,0.01);
            } else {
                player.addVelocity(0,0.001);
            }
        }
        if(d){
            if(!ctrl){
                player.addVelocity(0.01,0);
            } else {
                player.addVelocity(0.001,0);
            }
        }

        player.update((double) 1000/ getUpdateRate());

        if(w){
            if(!gameBox.getChildren().contains(player.getUP())){
                gameBox.getChildren().add(player.getUP());
            }
        } else {
            gameBox.getChildren().remove(player.getUP());
        }
        if(a){
            if(!gameBox.getChildren().contains(player.getLEFT())){
                gameBox.getChildren().add(player.getLEFT());
            }
        } else {
            gameBox.getChildren().remove(player.getLEFT());
        }
        if(s){
            if(!gameBox.getChildren().contains(player.getDOWN())){
                gameBox.getChildren().add(player.getDOWN());
            }
        } else {
            gameBox.getChildren().remove(player.getDOWN());
        }
        if(d){
            if(!gameBox.getChildren().contains(player.getRIGHT())){
                gameBox.getChildren().add(player.getRIGHT());
            }
        } else {
            gameBox.getChildren().remove(player.getRIGHT());
        }
        player.rotateCannon(mouseX,mouseY);

        for(asteroid ast: asteroids){
            if(ast.intersects(player)){
                asteroids.remove(ast);
                updateGameScene();
                break;
//                System.out.println("Player is colliding with asteroid: "+ast);
            }
        }
    }

    private void spawnAsteroid(double x, double y) {
        asteroid ast = new asteroid(x,y);
        ast.setPosition(x-ast.getWidth()/2, y-ast.getHeight()/2);

        asteroids.add(ast);
    }

    private void mouseClick(MouseEvent event){
        System.out.println("game was clicked at: " + event.getX() + "," + event.getY()+" ("+event.getButton()+")");

        switch (event.getButton()){
            case PRIMARY:

                spawnAsteroid(event.getX(),event.getY());
                updateGameScene();

                break;
            case SECONDARY:
                break;
            case MIDDLE:
                break;
            case NONE:
                break;
        }
    }

//    private void changeLevel() {
//
//        if(player.intersects(door)){
//            System.out.println("Changing level!");
//            createNewLevel();
//        }
//
//        if(player.getPositionX() + player.getWidth() > dimX - 20) {
//
//        }
//    }
//
//    public void createNewLevel(){
//    }
//
//    private void spawnDoor(double y){
//        double width = getGameDimX() - 56;
//        double height = y - 148;
//        door.setPosition(width,height);
//    }
//
//    public void render(){
//        player.render();
//        for(asteroid pt: asteroids){
//            pt.render();
//        }
//        door.render();
//    }
//
//    static double getGravity(){
//        return gravity;
//    }
//
//    private void createPlatform(double x, double y){
//        double length = 100 + new Random().nextDouble()*(150 - 100);
//        platform pt = new platform(x - length/2,y - ((double) 75 / 2),length);
//        platforms.add(pt);
//    }
//
//    static boolean playerCollidesAfterX(double newx){
//        for(platform pt: platforms){
//            if(player.intersectsAfterXMovement(pt,newx)){
//
//                if ((!(player.getPositionX() > pt.getPositionX()) || !(player.getPositionX() < pt.getPositionX() + pt.getWidth())) &&
//                    player.getPositionY() < (pt.getPositionY()+pt.getHeight()) ) {
//                    double deltax = pt.getPositionX() - player.positionX;
//
//                    if(deltax > 0) {
//                        player.setPositionX(pt.getPositionX()-player.getWidth()-2);
//                    } else {
//                        player.setPositionX(pt.getPositionX()+pt.getWidth()+2);
//                    }
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        }
//        return false;
//    }
//
//    static boolean playerCollidesAfterY(double newy){
//        for(platform pt: platforms){
//            if(player.intersectsAfterYMovement(pt,newy)){
//
//                double deltaY = pt.getPositionY() - player.positionY;
//
//                if(deltaY > 0) {
//                    player.setStatus("on platform");
//                    player.setPositionY(pt.getPositionY()-player.getHeight()+getGrassPixelsHeight());
//                    player.setOnGround(true);
//                    player.setVelocityY(0);
//                } else {
//                    player.setPositionY(pt.getPositionY()+pt.getHeight()+2);
//                    player.setOnGround(false);
//                    player.setVelocityY(0);
//                }
//                return true;
//            }
//        }
//        return false;
//    }

    static double getGameDimX() {
        return dimX;
    }

    static double getGameDimY() {
        return dimY;
    }

    private Label getDebugLabel(){
        debugLabel.setText(getDebugInfo());
        debugLabel.setMinHeight(40);
        return debugLabel;
    }

    private String getDebugInfo(){
        return "Time: " + getTime()/getUpdateRate()+", Position: " + (short) player.getPositionX()+" ("+(short)(player.getPositionX()
                + player.getWidth())+"), "+(short) player.getPositionY()+" ("+(short)(player.getPositionY()+ player.getHeight())+")"
                +"\nwidth: "+ player.getWidth()+", height: "+ player.getHeight()
                +", Velocity: "+ player.getVelocityX()+","+ player.getVelocityY();
    }
}