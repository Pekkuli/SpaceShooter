package GameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.text.DecimalFormat;
import static GameObjects.game.*;

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

    boolean isOutOfBounds(){
        if((getX() > getGameDimX()) || (getY() > getGameDimY())){
            return true;
        }
        if(getX() + width < 0){
            return true;
        }
        if(getY() + height < 0){
            return true;
        }
        return false;
    }

    String roundDecimals(double number){
        DecimalFormat df = new DecimalFormat("0.00");
        String decimal = df.format(number);
        decimal = decimal.replace(",",".");
        return  decimal;
    }
}