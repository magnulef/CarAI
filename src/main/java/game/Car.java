package game;

public class Car {

    private int locationX;
    private int locationY;
    private int orientation;
    private double speed;

    private final int width;
    private final int height;

    public Car(
            int startLocationX,
            int startLocationY
    ) {
        this.locationX = startLocationX;
        this.locationY = startLocationY;
        this.orientation = 0;
        this.speed = 0.0;
        this.width = 20;
        this.height = 30;
    }

    public void move(int xMomentum, int yMomentum, int orientation) {
        this.locationX = locationX - xMomentum;
        this.locationY = locationY - yMomentum;
        this.orientation = this.orientation - orientation;
        System.out.println("X location: " + locationX);
        System.out.println("Y location: " + locationY);
        System.out.println("Orientation: " + this.orientation);
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationX(int newX) {
        this.locationX = newX;
    }

    public void setLocationY(int newY) {
        this.locationY = newY;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public double getSpeed() {
        return speed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
