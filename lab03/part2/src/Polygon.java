import java.util.*;

public class Polygon{
    private final List<Point> vertices; 

    public Polygon(List<Point> vertices){
        this.vertices = vertices; 
    }

    public List<Point> getPoints(){
        return vertices; 
    }
    public double perimeter(){
        double total = 0.0;
        for (int x = 0; x < vertices.size()-1; x++){
            Point temp1 = vertices.get(x);
            Point temp2 = vertices.get(x+1);
            double distance = Math.sqrt(((temp1.getX() - temp2.getX())*(temp1.getX() - temp2.getX())) + ((temp1.getY() - temp2.getY())*(temp1.getY() - temp2.getY())));
            total = total + distance;
        }
        Point lastPt = vertices.get(vertices.size()-1);
        Point firstPt = vertices.get(0);
        double firstAndLast = Math.sqrt(((lastPt.getX()-firstPt.getX())*(lastPt.getX()-firstPt.getX())) + ((lastPt.getY() - firstPt.getY())*(lastPt.getY()-firstPt.getY())));
        total = total + firstAndLast;
        return total;
    }
}
