import java.lang.Math; 


public class Util{
    public static double perimeter(Circle circle){
        double perimeter = 2*circle.getRadius() * Math.PI; 
        return perimeter; 
    }
    public static double perimeter(Rectangle rectangle){
        Point topLeft = rectangle.getTopLeft();
        Point bottomRight = rectangle.getBottomRight();
        double perimeter = (2*Math.abs(topLeft.getY() - bottomRight.getY()) + 2*Math.abs(bottomRight.getX() - topLeft.getX()));
        return perimeter;
    }
    public static double perimeter(Polygon polygon){
        double total = 0.0;
        for (int x = 0; x < polygon.getPoints().size()-1; x++){
            Point temp1 = polygon.getPoints().get(x);
            Point temp2 = polygon.getPoints().get(x+1);
    	    //double distance = Math.sqrt(Math.pow(temp1.getX() - temp2.getX(),2) + Math.pow(temp1.getY() - temp2.getY(),2));
	    double distance = Math.sqrt(((temp1.getX() - temp2.getX())*(temp1.getX() - temp2.getX())) + ((temp1.getY() - temp2.getY())*(temp1.getY() - temp2.getY())));
            total = total + distance;
        }
        Point lastPt = polygon.getPoints().get(polygon.getPoints().size()-1);
	Point firstPt = polygon.getPoints().get(0);
	//double firstAndLast = Math.sqrt(Math.pow(lastPt.getX() - firstPt.getX(),2) + Math.pow(lastPt.getY() - firstPt.getY(),2));
        double firstAndLast = Math.sqrt(((lastPt.getX()-firstPt.getX())*(lastPt.getX()-firstPt.getX())) + ((lastPt.getY() - firstPt.getY())*(lastPt.getY()-firstPt.getY())));
	total = total + firstAndLast; 
	return total;

    }
}
