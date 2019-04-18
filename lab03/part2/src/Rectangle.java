
public class Rectangle{
    private final Point topLeft;
    private final Point botRight; 

    public Rectangle(Point topLeft, Point botRight){
        this.topLeft = topLeft;
        this.botRight = botRight; 
    }

    public Point getTopLeft(){
        return topLeft;
    }
    public Point getBotRight(){
        return botRight;
    }
    public double perimeter(){
        //Point topLeft = rectangle.getTopLeft();
        //Point botRight = rectangle.getBotRight();
        double perimeter = (2*Math.abs(topLeft.getY() - botRight.getY()) + 2*Math.abs(botRight.getX() - topLeft.getX()));
        return perimeter;
    }
}
