package GameObjects;


import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;

import java.util.Random;

class asteroid extends sprite {

    private double size;
    private static double minSize = 100;
    private static double maxSize = 350;
    private double rotate;
    private boolean clockwise;
    private long lastUpdate;
    private double spinningFrequency;



    asteroid(double x, double y) {
        super(x,y);

        size = getRandSize();
        updateSpinningFreq();
        super.setImage("/images/asteroidi1.png",size,size);

        Random rng = new Random();
        rotate = rng.nextDouble() * 360;
        clockwise = rng.nextInt(2) == 1;
        lastUpdate = 0;

        setOnMouseClicked(event -> {
            System.out.println("Asteroid at: "+getX()+","+getY()+" was clicked!");
            if(size > minSize+5){
                size -=5;
                setImage("/images/asteroidi1.png",size,size);
                setPosition(x - getWidth()/2, y - getHeight()/2);
                updateSpinningFreq();
            } else {
                this.setVisible(false);
            }
        });

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

                    if(clockwise) {
                        rotate += 360d / 30 / spinningFrequency;
                    } else {
                        rotate -= 360d / 30 / spinningFrequency;
                    }
                }
            }
        }.start();
    }

    private  void updateSpinningFreq(){
        spinningFrequency = Math.pow(size / maxSize, 1.5) * 10;
    }

    private static double getRandSize(){
        return new Random().nextDouble() * (maxSize - minSize) + minSize;
    }

//    boolean intersects(sprite s) {
//        Circle crl = new Circle(getCenterX(),getCenterY(),size/2);
//        return crl.intersects(s.getX(),s.getY(),s.getWidth(),s.getHeight());
//        return s.new Circle(getCenterX(),getCenterY(),size).intersects(getBoundary());
//    }

    @Override
    public String toString(){
        return "Position: "+getX()+","+getY()+", Size: "+size;
    }

//    private Circle getBoundary(){
//        return new Circle(getCenterX(),getCenterY(),size);
//    }
}
