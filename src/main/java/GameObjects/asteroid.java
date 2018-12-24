package GameObjects;

import javafx.animation.AnimationTimer;
import java.util.Random;

class asteroid extends sprite {

    private static double minSize = 100;
    private static double maxSize = 350;

    private double size;
    private double rotate;

    private boolean clockwise;
    private double spinningFrequency;

    private long lastUpdate;

    asteroid(double x, double y) {
        super(x,y);

        size = getRandSize();
        updateSpinningFreq();
        super.setImage("/images/asteroidi1.png",size,size);

        Random rng = new Random();
        rotate = rng.nextDouble() * 360;
        clockwise = rng.nextInt(2) == 1;
        lastUpdate = 0;

//        setOnMouseClicked(event -> {
//            System.out.println("Asteroid at: "+getX()+","+getY()+" was clicked!");
//            double dmg = player.getDamage();
//            if(size > minSize+dmg){
//                size -=player.getDamage();
//                setImage("/images/asteroidi1.png",size,size);
//                setPosition(x - getWidth()/2, y - getHeight()/2);
//                updateSpinningFreq();
//            } else {
//                this.setVisible(false);
//            }
//        });

        new AnimationTimer()
        {
            public void handle(long now)
            {
                long deltaTime = (now - lastUpdate) / 1000000;

                if(deltaTime >= (1000 / 30) ){
                    if(rotate >= 360){
                        rotate = rotate%360;
                    }
                    if (rotate <= -360){
                        rotate = rotate%360;
                    }
                    setRotate(rotate);
                    lastUpdate = now;

                    addRotation();
                }
            }
        }.start();
    }

    private  void updateSpinningFreq(){
        spinningFrequency = Math.pow(size / maxSize, 1.5) * 10;
    }

    private void addRotation(){
        if(clockwise) {
            rotate += 360d / 30 / spinningFrequency;
        } else {
            rotate -= 360d / 30 / spinningFrequency;
        }
    }

    private double getSize(){
        return size;
    }

    boolean intersects(sprite s) {
        double deltaX = Math.abs(getCenterX()-s.getCenterX());
        double deltaY = Math.abs(getCenterY()-s.getCenterY());
        double distance = Math.sqrt( Math.pow(deltaX,2) + Math.pow(deltaY,2) );

        return distance <= getWidth()/2 + s.getWidth()/2;
    }

    boolean getHit(){
        System.out.println("Asteroid at: "+roundDecimals(getX())+","+roundDecimals(getY())+", Size: "+roundDecimals(getSize())+" was hit!");
        double dmg = player.getDamage();
        if(size > minSize+dmg){
            size -=dmg;
            double x = getX();
            double y = getY();

            setImage("/images/asteroidi1.png",size,size);
//            setPosition(x + getWidth()/2, y + getHeight()/2);
            setPosition(x + dmg/2,y + dmg/2);
            updateSpinningFreq();
            return false;
        } else {
            return true;
        }
    }

    private static double getRandSize(){
        return new Random().nextDouble() * (maxSize - minSize) + minSize;
    }

    @Override
    public String toString(){
        return "Position: "+getX()+","+getY()+", Size: "+size;
    }
}