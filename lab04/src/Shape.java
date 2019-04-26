//import java.awt.Point;
import java.awt.Color; 
import java.awt.*;

public interface Shape
{
	Color getColor();
	void setColor(Color color); 
	double getArea(); 
	double getPerimeter(); 
	void translate(double deltaX, double deltaY); 
}