package GameObjects;

class projectile extends sprite {

    private double speedX;
    private double speedY;

    projectile(double x, double y,double deltax, double deltay) {
        super(x, y);

        double speed = 1.2;

        setImage("/images/projectile1.png");

        double ratiox = Math.abs(deltax)/(Math.abs(deltax)+Math.abs(deltay));
        double ratioy = Math.abs(deltay)/(Math.abs(deltax)+Math.abs(deltay));

        if(deltax < 0){
            speedX = -1*ratiox* speed;
        } else {
            speedX = ratiox* speed;
        }
        if(deltay < 0){
            speedY = -1*ratioy* speed;
        } else {
            speedY = ratioy* speed;
        }
    }

    void update(double time){
        double newx = getX() + time*speedX;
        double newy = getY() +time*speedY;
        setPosition(newx,newy);
    }
}
