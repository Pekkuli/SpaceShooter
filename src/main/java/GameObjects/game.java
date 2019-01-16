package GameObjects;

import javafx.collections.ObservableList;
import javafx.scene.Node;
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
    private static ArrayList<String> input;

    private static double dimX;
    private static double dimY;

    private static Scene gameScene;
    private static Pane gameBox;
    private static Label debugLabel;


    public game(double x, double y) {
        player = new player(0,0);
        input = new ArrayList<>();
        dimX = x;
        dimY = y;

        BorderPane root = new BorderPane();
        gameBox = new Pane();
        HBox optionBox = new HBox();
        optionBox.setStyle("-fx-background-color: #ffffff;");
        debugLabel = new Label();

        Image back = new Image("/images/Background.png",x,y,false,false);
        ImageView background = new ImageView(back);
        background.setX(0);
        background.setY(0);

        gameBox.getChildren().add(background);
        gameBox.getChildren().addAll(player);
        gameBox.getChildren().addAll(player.getCANNON());

        spawnAsteroid(400,250);
        spawnAsteroid(550,250);

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

        gameScene.setOnKeyReleased(event -> {
            String keyInput = event.getCode().toString();
            input.remove(keyInput);
        });

        gameBox.setOnMouseDragged(event -> player.rotateCannon(event));
        gameBox.setOnMouseMoved(event -> player.rotateCannon(event));

        gameBox.setOnMousePressed(event -> {
//            player.rotateCannon(event);

            if(event.getButton().toString().equals("PRIMARY")){
                player.startFiring();
            }
        });

        gameBox.setOnMouseReleased(event -> player.stopFiring());

        root.setCenter(gameBox);
        root.setTop(optionBox);
    }

    public Scene getGameScene(){
        return gameScene;
    }

//    private void updateGameScene(){
//        BorderPane root = new BorderPane();
//        gameScene.setRoot(root);
//        gameBox.getChildren().clear();
//        gameBox.getChildren().add(background);
//        gameBox.getChildren().addAll(asteroids);
//        gameBox.getChildren().addAll(player);
//        gameBox.getChildren().addAll(player.getCANNON());
//        root.setCenter(gameBox);
//        root.setTop(optionBox);
//        updateScene();
//    }

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

        addFlameEffects(w, a, s, d, ctrl);
//        addFlameEffectUP(w,ctrl);
//        addFlameEffectLeft(a,ctrl);
//        addFlameEffectDOWN(s,ctrl);
//        addFlameEffectRIGHT(d,ctrl);

        player.rotateCannon();

        ObservableList<Node> list = gameBox.getChildren();
        for(int i = 0; i < list.size();i++){
            Node nd = list.get(i);

            if(nd instanceof asteroid){
                asteroid ast = (asteroid) nd;

                ObservableList<Node> listproj = gameBox.getChildren();

                for(int j = 0; j < listproj.size();j++){

                    Node pro = listproj.get(j);
                    if(pro instanceof projectile){
                        if (ast.intersects((projectile)pro)){
                            list.remove(pro);
                            if(ast.getHit()){
                                list.remove(ast);
                            }
                        }
                    }
                }

//                if(ast.intersects(player)){
//                    list.remove(ast);
//                    break;
//                }
            }
            if(nd instanceof projectile){
                projectile pro = (projectile) nd;
                if(!pro.isOutOfBounds()){
                    pro.update((double) 1000/ getUpdateRate());
                } else {
                    list.remove(pro);
                }
            }
        }
    }

//    public static player getPlayer() {
//        return player;
//    }

    private static void spawnAsteroid(double x, double y) {
        asteroid ast = new asteroid(x,y);
        ast.setPosition(x-ast.getWidth()/2, y-ast.getHeight()/2);
        gameBox.getChildren().add(ast);
    }

    private void mouseClick(MouseEvent event){
        System.out.println("game was clicked at: " + event.getX() + "," + event.getY()+" ("+event.getButton()+")");
        switch (event.getButton()){
            case PRIMARY:
//                double deltaX = event.getX() - player.getCenterX();
//                double deltaY = event.getY() - player.getCenterY();
//                spawnProjectile(player.getCenterX(),player.getCenterY(),deltaX, deltaY);
//              do left getHit

                break;
            case SECONDARY:

                spawnAsteroid(event.getX(),event.getY());
//                do right getHit

                break;
            case MIDDLE:

//                do middle getHit

                break;
            case NONE:
                break;
        }
    }

    private void addFlameEffects(boolean w,boolean a, boolean s, boolean d, boolean ctrl){
        addFlameEffectUP(w,ctrl);
        addFlameEffectLeft(a,ctrl);
        addFlameEffectDOWN(s,ctrl);
        addFlameEffectRIGHT(d,ctrl);
    }

    private void addFlameEffectUP(boolean on, boolean ctrl){
        if(on){

            if (!ctrl){
                if(!gameBox.getChildren().contains(player.getUP())){
                    gameBox.getChildren().add(player.getUP());
                    gameBox.getChildren().remove(player.getUP_SMALL());
                }
            } else {
                if(!gameBox.getChildren().contains(player.getUP_SMALL())){
                    gameBox.getChildren().add(player.getUP_SMALL());
                    gameBox.getChildren().remove(player.getUP());
                }
            }
        } else {
            gameBox.getChildren().remove(player.getUP());
            gameBox.getChildren().remove(player.getUP_SMALL());
        }
//        if(on){
//
//            if (!ctrl){
//                if(player.getUP().isVisible()){
//                    player.getUP().setVisible(true);
//                    player.getUP_SMALL().setVisible(false);
//                }
//            } else {
//                if(!player.getUP_SMALL().isVisible()){
//                    player.getUP_SMALL().setVisible(true);
//                    player.getUP().setVisible(false);
//                }
//            }
//        } else {
//            player.getUP().setVisible(false);
//            player.getUP_SMALL().setVisible(false);
//        }
    }

    private void addFlameEffectLeft(boolean on, boolean ctrl){
        if(on){
            if (!ctrl){
                if(!gameBox.getChildren().contains(player.getLEFT())){
                    gameBox.getChildren().add(player.getLEFT());
                    gameBox.getChildren().remove(player.getLEFT_SMALL());
                }
            } else {
                if(!gameBox.getChildren().contains(player.getLEFT_SMALL())){
                    gameBox.getChildren().add(player.getLEFT_SMALL());
                    gameBox.getChildren().remove(player.getLEFT());
                }
            }
        } else {
            gameBox.getChildren().remove(player.getLEFT());
            gameBox.getChildren().remove(player.getLEFT_SMALL());
        }
    }

    private void addFlameEffectDOWN(boolean on, boolean ctrl){
        if(on){
            if (!ctrl){
                if(!gameBox.getChildren().contains(player.getDOWN())){
                    gameBox.getChildren().add(player.getDOWN());
                    gameBox.getChildren().remove(player.getDOWN_SMALL());
                }
            } else {
                if(!gameBox.getChildren().contains(player.getDOWN_SMALL())){
                    gameBox.getChildren().add(player.getDOWN_SMALL());
                    gameBox.getChildren().remove(player.getDOWN());
                }
            }
        } else {
            gameBox.getChildren().remove(player.getDOWN());
            gameBox.getChildren().remove(player.getDOWN_SMALL());
        }
    }

    private void addFlameEffectRIGHT(boolean on, boolean ctrl){
        if(on){
            if (!ctrl){
                if(!gameBox.getChildren().contains(player.getRIGHT())){
                    gameBox.getChildren().add(player.getRIGHT());
                    gameBox.getChildren().remove(player.getRIGHT_SMALL());
                }
            } else {
                if(!gameBox.getChildren().contains(player.getRIGHT_SMALL())){
                    gameBox.getChildren().add(player.getRIGHT_SMALL());
                    gameBox.getChildren().remove(player.getRIGHT());
                }
            }
        } else {
            gameBox.getChildren().remove(player.getRIGHT());
            gameBox.getChildren().remove(player.getRIGHT_SMALL());
        }
    }

    static void spawnProjectile(double x, double y, double mousex, double mousey){
        gameBox.getChildren().add(new projectile(x,y,mousex,mousey));
    }

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
                + player.getWidth())+"), "+(short) player.getPositionY()+" ("+(short)(player.getPositionY()+ player.getHeight())+")" + "Game objects: "+gameBox.getChildren().size()
                +"\nwidth: "+ player.getWidth()+", height: "+ player.getHeight()
                +", Velocity: "+ player.getVelocityX()+","+ player.getVelocityY();
    }
}