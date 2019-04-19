
public class Rectangle{
    private final Point topLeft;
    private final Point bottomRight; 

    public Rectangle(Point topLeft, Point bottomRight){
        this.topLeft = topLeft;
        this.bottomRight = bottomRight; 
    }

    public Point getTopLeft(){
        return topLeft;
    }
    public Point getBottomRight(){
        return bottomRight;
    }
    public double perimeter(){
        double perimeter = (2*Math.abs(topLeft.getY() - bottomRight.getY()) + 2*Math.abs(bottomRight.getX() - topLeft.getX()));
        return perimeter;
    }
}
