package GameObjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

class projectile extends sprite {

    private double speedX;
    private double speedY;

    projectile(double x, double y,double mouseX, double mouseY) {
        super(x - 5, y - 5);

        double speed = 1.2;
        super.setMouseTransparent(true);
        setImage("/images/projectile1.png");

        BigDecimal deltax2 = BigDecimal.valueOf(mouseX - getCenterX());
        BigDecimal deltay2 = BigDecimal.valueOf(mouseY - getCenterY());

        BigDecimal ratiox2 = deltax2.abs().divide(deltax2.abs().add(deltay2.abs()), RoundingMode.DOWN);
        BigDecimal ratioy2 = deltay2.abs().divide(deltay2.abs().add(deltax2.abs()), RoundingMode.DOWN);

//        double deltax = mouseX - getCenterX();
//        double deltay = mouseY - getCenterY();
//
//        double ratiox = Math.abs(deltax)/(Math.abs(deltax)+Math.abs(deltay));
//        double ratioy = Math.abs(deltay)/(Math.abs(deltax)+Math.abs(deltay));
        
        if(deltax2.doubleValue() < 0){
            speedX = -1*ratiox2.doubleValue()* speed;
        } else {
            speedX = ratiox2.doubleValue()* speed;
        }
        if(deltay2.doubleValue() < 0){
            speedY = -1*ratioy2.doubleValue()* speed;
        } else {
            speedY = ratioy2.doubleValue()* speed;
        }
    }

    void update(double time){
        double newx = getX() + time*speedX;
        double newy = getY() +time*speedY;
        setPosition(newx,newy);
    }
}