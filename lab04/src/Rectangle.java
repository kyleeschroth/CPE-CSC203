//import java.awt.Point;
import java.awt.Color; 
import java.awt.*;


public class Rectangle implements Shape
{
	private double width; 
	private double height; 
	private Point topLeft; 
	private Color color; 

	public Rectangle(double width, double height, Point topLeft, Color color)
	{
		this.width = width; 
		this.height = height; 
		this.topLeft = topLeft; 
		this.color = color; 
	}

	// rectangle instance variables
	public double getWidth(){
		return width; 
	}

	public void setWidth(double newWidth){
		this.width = newWidth; 
	}

	public double getHeight(){
		return height; 
	}

	public void setHeight(double newHeight){
		this.height = newHeight;
	}

	public Point getTopLeft(){
		return topLeft;
	}

	// rectangle implementing instance variables of shape
	public Color getColor(){
		return color; 
	}

	public void setColor(Color newColor){
		this.color = newColor; 
	}

	public double getArea(){
		return width*height; 
	}

	public void translate(double deltaX, double deltaY){
		topLeft = new Point(topLeft.x + deltaX, topLeft.y + deltaY); 
	}

	public double getPerimeter(){
		return 2*width + 2*height; 
	}
	
}







