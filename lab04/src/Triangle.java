//import java.awt.Point;
import java.awt.Color; 

public class Triangle implements Shape
{

	private Point vertexA; 
	private Point vertexB; 
	private Point vertexC; 
	private Color color; 

	public Triangle(Point vertexA, Point vertexB, Point vertexC, Color color){
		this.vertexA = vertexA; 
		this.vertexB = vertexB; 
		this.vertexC = vertexC;
		this.color = color; 
	}

	// instance variables and methods of triangle class

	public Point getVertexA(){
		return vertexA;
	}

	public Point getVertexB(){
		return vertexB;
	}

	public Point getVertexC(){
		return vertexC;
	}

	// implementing instance varavles of shape

	public Color getColor(){
		return color; 
	}

	public void setColor(Color newColor){
		this.color = newColor; 
	}

	public double getArea(){
		return Math.abs((vertexA.x*(vertexB.y- vertexC.y) + 
						vertexB.x*(vertexC.y - vertexA.y) + 
						vertexC.x*(vertexA.y - vertexB.y) / 2));
	}

	public void translate(double deltaX, double deltaY){
		vertexA = new Point(vertexA.x + deltaX, vertexA.y + deltaY);
		vertexB = new Point(vertexB.x + deltaX, vertexB.y + deltaY);
		vertexC = new Point(vertexC.x + deltaX, vertexC.y + deltaY);
	}

	public double getPerimeter(){
		double AB = Math.pow( Math.pow(vertexB.x - vertexA.x, 2) + Math.pow(vertexB.y - vertexA.y, 2) , 0.5);
		double BC = Math.pow( Math.pow(vertexC.x - vertexB.x, 2) + Math.pow(vertexC.y - vertexB.y, 2) , 0.5);
		double AC = Math.pow( Math.pow(vertexC.x - vertexA.x, 2) + Math.pow(vertexC.y - vertexA.y, 2) , 0.5);
		return AB+BC+AC;
	}
}