//import java.awt.Point;
import java.awt.Color; 

public class ConvexPolygon implements Shape{

	private Point[] vertices; 
	private Color color; 

	public ConvexPolygon(Point[] vertices, Color color){
		this.vertices = vertices; 
		this.color = color; 
	}

	//convex polygon implementation
	public Point getVertex(int index){
		if (index > getNumVertices()){
			throw new ArrayIndexOutOfBoundsException("Illegal Index:" + index);
		}
		else{
			return vertices[index];
		}
	}

	//interface

	public Color getColor(){
		return color;
	}

	public void setColor(Color newColor){
		this.color = newColor; 
	}

	public double getArea(){	
		double result = 0;
		for(int i=0; i < vertices.length-1; i++)
		{
			Point NextPoint = vertices[i+1];
			if (i == vertices.length-1)
			{
				NextPoint = vertices[0];
			}
			result += ( vertices[i].x*NextPoint.y ) - ( NextPoint.x*vertices[i].y );
		}
		return 0.5*result;
	}

	public void translate(double deltaX, double deltaY){
		for(int i=0; i < vertices.length; i++)
		{
			vertices[i] = new Point(vertices[i].x + deltaX, vertices[i].y + deltaY);
		}
	}

	public double getPerimeter(){	
		double result = 0;
		for(int i=0; i < vertices.length; i++)
		{
			Point NextPoint = null;
			if (i == vertices.length-1)
			{
				NextPoint = vertices[0];
			}
			else 
			{
				NextPoint = vertices[i+1];
			}
			double func = Math.pow( Math.pow(NextPoint.x - vertices[i].x, 2) + Math.pow(NextPoint.y - vertices[i].y, 2) , 0.5);
			result += func;
			System.out.println(vertices.length);
		}
		return result;
	}

	public int getNumVertices(){
		return this.vertices.length;

	}
}

