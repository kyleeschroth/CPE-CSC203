
public class Bigger{
    public static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon){
        double circleP = circle.perimeter(); 
        double rectangleP = rectangle.perimeter(); 
        double polygonP = polygon.perimeter(); 

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

