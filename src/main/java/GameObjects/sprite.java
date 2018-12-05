package GameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;


class sprite extends ImageView{

    double width;
    double height;

    sprite(double x, double y) {
        super();
        super.setX(x);
        super.setY(y);
    }

    void setImage(String filename, double x, double y){
        super.setImage(new Image(filename, x, y,false,false));
        width = x;
        height = y;
    }

    void setImage(String filename){
        Image img = new Image(filename);
        super.setImage(img);
        width = img.getWidth();
        height = img.getHeight();
    }

    double getPositionX() {
        return super.getX();
    }

    double getPositionY() {
        return super.getY();
    }

    double getCenterX(){
        return super.getX() + width/2;
    }

    double getCenterY(){
        return super.getY() + height/2;
    }

    double getWidth(){
        return width;
    }

    double getHeight(){
        return height;
    }

    void setPosition(double x, double y) {
        super.setX(x);
        super.setY(y);
    }

    void setPositionX(double x) {
        super.setX(x);
    }

    void setPositionY(double y) {
        super.setY(y);
    }

    private Circle getBoundary(){
        return new Circle(getCenterX(),getCenterY(),width/2);
    }

    boolean intersects(sprite s) {
        double deltaX = Math.abs(getCenterX()-s.getCenterX());
        double deltaY = Math.abs(getCenterY()-s.getCenterY());

        double distance = Math.sqrt( Math.pow(deltaX,2) + Math.pow(deltaY,2) );

        return distance <= getWidth()/2 + s.getWidth()/2;


//        return s.getBoundary().intersects(getBoundary().getBoundsInLocal());
//        return s.getBoundary().intersects(getBoundary());
    }
}
