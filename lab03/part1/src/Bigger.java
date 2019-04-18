
public class Bigger{
    public static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon){
        double circleP = Util.perimeter(circle); 
        double rectangleP = Util.perimeter(rectangle); 
        double polygonP = Util.perimeter(polygon); 

        if (circleP > rectangleP & circleP > polygonP){
            return circleP;
        }
        else if (rectangleP > circleP & rectangleP > polygonP){
            return rectangleP; 
        }
        else if (polygonP > circleP & polygonP > rectangleP){
            return polygonP;
        }
        else if (polygonP == rectangleP){   
            return polygonP; 
        }
        else if (polygonP == circleP){
            return polygonP; 
        }
        else{
            return rectangleP; 
        }
    }
} 

