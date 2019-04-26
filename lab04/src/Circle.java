
import java.awt.*; 
import java.awt.Color;

public class Circle implements Shape
{
	private double radius; 
	private Point center; 
	private Color color; 

	public Circle(double radius, Point center, Color color)
	{
		this.radius = radius; 
		this.center = center;
		this.color = color; 

	}
	// instances of circle class
	public double getRadius()
	{
		return radius; 
	}

	public void setRadius(double radius)
	{
		this.radius = radius; 
	}

	public Point getCenter()
	{
		return center; 
	}

	// methods implementing from shape

	public Color getColor()
	{
		return color; 		
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public double getArea()
	{
		return (Math.PI*Math.pow(radius, 2));
	}

	public void translate(double deltaX, double deltaY)
	{
		center = new Point(center.x + deltaX, center.y + deltaY);
	}

	public double getPerimeter()
	{
		return (2*Math.PI*radius); 
	}

}