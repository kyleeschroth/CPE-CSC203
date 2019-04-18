import java.util.*;

public class Polygon{
    private final List<Point> vertices; 

    public Polygon(List<Point> vertices){
        this.vertices = vertices; 
    }

    public List<Point> getPoints(){
        return vertices; 
    }
}
