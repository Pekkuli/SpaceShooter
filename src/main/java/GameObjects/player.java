package GameObjects;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

import static GameObjects.game.*;


public class player extends sprite {

    private double velocityX;
    private double velocityY;

    private ImageView UP;
    private ImageView LEFT;
    private ImageView DOWN;
    private ImageView RIGHT;
    private ImageView CANNON;

    player(double x, double y) {
        super(x, y);
        setImage("/images/player/player.png");

        velocityX = 0;
        velocityY = 0;

        UP = new ImageView("/images/player/UP.png");
        LEFT = new ImageView("/images/player/LEFT.png");
        DOWN = new ImageView("/images/player/DOWN.png");
        RIGHT = new ImageView("/images/player/RIGHT.png");
        CANNON = new ImageView("/images/player/player_cannon.png");
    }

    double getVelocityX() {
        return velocityX;
    }

    double getVelocityY() {
        return velocityY;
    }

    void setVelocity(double X, double Y) {
        velocityX = X;
        velocityY = Y;
    }

    ImageView getUP() {
        UP.setX(getX()+20);
        UP.setY(getY() + height);
        return UP;
    }

    ImageView getLEFT() {
        LEFT.setX(getX() + width);
        LEFT.setY(getY()+20);
        return LEFT;
    }

    ImageView getDOWN() {
        DOWN.setX(getX()+20);
        DOWN.setY(getY()-10);
        return DOWN;
    }

    ImageView getRIGHT() {
        RIGHT.setX(getX()-10);
        RIGHT.setY(getY()+20);
        return RIGHT;
    }

    ImageView getCANNON() {
        CANNON.setX(getX());
        CANNON.setY(getY());
        return CANNON;
    }

    void rotateCannon(double x, double y){
        double deltaX = getX()+width/2 - x;
        double deltaY = getY()+height/2 - y;

        double angle = -Math.toDegrees(Math.atan2(deltaX, deltaY));
//        System.out.println(angle);
        CANNON.setRotate(angle);
    }

//    @Override
//    void setPositionX(double x) {
//        super.setX(x);
//    }
//
//    @Override
//    void setPositionY(double y) {
//        super.setY(y);
//    }

    private void setVelocityX(double x){
        velocityX = x;
    }

    private void setVelocityY(double y) {
        velocityY = y;
    }

    void addVelocity(double x, double y){
        velocityX += x;
        velocityY += y;
    }

    void update(double time) {

        if(Math.abs(velocityX) < 0.001){
            velocityX = 0;
        }
        if (Math.abs(velocityY) < 0.001){
            velocityY = 0;
        }

        double deltaX = velocityX*time;
        double deltaY = velocityY*time;

        double newX = getPositionX() + deltaX;
        double newY = getPositionY() + deltaY;

        if( (newX >=0) && (newX + width <= getGameDimX()) ){
            setPositionX(newX);
            CANNON.setX(newX);
        } else if (newX <=0) {
            setPositionX(0);
            CANNON.setX(0);
            setVelocityX(0.01);
        } else {
            setPositionX(getGameDimX()-getWidth());
            CANNON.setX(getGameDimX()-getWidth());
            setVelocityX(-0.01);
        }

        if( (newY >=0) && (newY + height <= getGameDimY())) {
            setPositionY(newY);
            CANNON.setY(newY);
        }else if (newY <0) {
            setPositionY(0);
            CANNON.setY(0);
            setVelocityY(0.01);
        } else{
            setPositionY(getGameDimY()-height);
            CANNON.setY(getGameDimY()-height);
            setVelocityY(-0.01);
        }
    }

//    private Rectangle2D getBoundaryAfterXMovement(double newx){
//        return new Rectangle2D(newx,positionY,width,height);
//    }
//
//    private Rectangle2D getBoundaryAfterYMovement(double newy){
//        return new javafx.geometry.Rectangle2D(positionX, newy,width,height);
//    }
//
//    boolean intersectsAfterXMovement(sprite s, double newx) {
//        return getBoundaryAfterXMovement(newx).intersects(s.getBoundary());
//    }
//
//    boolean intersectsAfterYMovement(sprite s, double newy) {
//        return getBoundaryAfterYMovement(newy).intersects(s.getBoundary());
//    }

    @Override
    public String toString() {
        return "Position: "+getPositionX() +","+getPositionY() +" , Velocity: "+velocityX +","+velocityY;
    }
}
