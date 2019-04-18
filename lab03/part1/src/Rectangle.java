
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
}
