package robot;

public class Point implements Cloneable {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Boolean samePoint(Point p) {
        return (this.x == p.x && this.y == p.y);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}