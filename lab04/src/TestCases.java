import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.Color;
//import java.awt.Point;

import static edu.calpoly.testy.Assert.assertEquals;
import static edu.calpoly.testy.Assert.assertTrue;
import static edu.calpoly.testy.Assert.fail;
import edu.calpoly.testy.Testy;

public class TestCases
{
    public static final double DELTA = 0.00001;

     
    // The provided tests are commented out.  Remove this line, the
    //line above it, and the close comment line just before runTests().

    // some sample tests but you must write more! see lab write up

    public void testCircleGetArea()
    {
        Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

        assertEquals(101.2839543, c.getArea(), DELTA);
    }

    
    public void testCircleGetPerimeter()
    {
        Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

        assertEquals(35.6759261, c.getPerimeter(), DELTA);
    }
    
    public void testWorkSpaceAreaOfAllShapes()
    {
        WorkSpace ws = new WorkSpace();

        ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
        ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
        ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), 
                      Color.BLACK));

        assertEquals(114.2906063, ws.getAreaOfAllShapes(), DELTA);
    }

    public void testWorkSpaceGetCircles()
    {
        WorkSpace ws = new WorkSpace();
        List<Circle> expected = new LinkedList<>();

        // Have to make sure the same objects go into the WorkSpace as
        // into the expected List since we haven't overriden equals in Circle.
        Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
        Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);

        ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
        ws.add(c1);
        ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
                      Color.BLACK));
        ws.add(c2);

        expected.add(c1);
        expected.add(c2);

        // Doesn't matter if the "type" of lists are different (e.g Linked vs
        // Array).  List equals only looks at the objects in the List.
        assertEquals(expected, ws.getCircles());
    }

    public void testWorkspaceGetRect() {
        WorkSpace wk = new WorkSpace();
        Shape c1 = new Circle(2.2, new Point(0, 0), Color.BLACK);
        Shape r1 = new Rectangle(2.0, 1.0, new Point(0, 0), Color.GRAY);
        Triangle t1 = new Triangle(new Point(0, 0), new Point(1, 0), new Point(0, 1), Color.GRAY);
        Point[] vertexArray = {new Point(0, 0), new Point(2, 5), new Point(5, 0)};
        ConvexPolygon cP1 = new ConvexPolygon(vertexArray, Color.MAGENTA);
        Rectangle r2 = new Rectangle(1.2, 2.2, new Point(3, 1), Color.GRAY);
        wk.add(c1);
        wk.add(r1);
        wk.add(t1);
        wk.add(cP1);
        wk.add(r2);
        List<Shape> expected = new LinkedList<>();
        expected.add(r1);
        expected.add(r2);
        assertEquals(expected, wk.getRectangles());
    }
    /*
    public void testConvexPolygonShapeMethods() {
        // ConvexPolygon -- 5-sided
        Point[] v1 = new Point[] {
            new Point(1.124,1.124),
            new Point(-3.645,4.0978),
            new Point(-10.123,-14.5238),
            new Point(5,-10.021736),
            new Point(20.09,1.001237)};
        ConvexPolygon cp1 = new ConvexPolygon(v1, Color.ORANGE);

        // getColor
        assertTrue(cp1.getColor().equals(Color.ORANGE));

        // setColor
        cp1.setColor(Color.GRAY);
        assertTrue(cp1.getColor().equals(Color.GRAY));

        // getArea
        double area = 252.4959260; 
        assertEquals(area, cp1.getArea(), DELTA);

        // getPerimeter
        double perimeter = 78.7689833;
        assertEquals(perimeter, cp1.getPerimeter(), DELTA);

        // translate
        double a = 4.65324;
        double b = 6.123;
        cp1.translate(a, b);
        Point newA = new Point(1.124+a,1.124+b);
        Point newB = new Point(-3.645+a,4.0978+b); 
        Point newC = new Point(-10.123+a,-14.5238+b);
        Point newD = new Point(5+a,-10.021736+b);
        Point newE = new Point(20.09+a,1.001237+b);
        assertTrue(cp1.getVertex(0).equals(newA));
        assertTrue(cp1.getVertex(1).equals(newB));
        assertTrue(cp1.getVertex(2).equals(newC));
        assertTrue(cp1.getVertex(3).equals(newD));
        assertTrue(cp1.getVertex(4).equals(newE));
        assertEquals(area, cp1.getArea(), DELTA); // Area should be unchanged

        double c = -45.324;
        double d = -123.4;
        cp1.translate(c, d);
        Point newA1 = new Point(1.124+a+c,1.124+b+d);
        Point newB1 = new Point(-3.645+a+c,4.0978+b+d); 
        Point newC1 = new Point(-10.123+a+c,-14.5238+b+d);
        Point newD1 = new Point(5+a+c,-10.021736+b+d);
        Point newE1 = new Point(20.09+a+c,1.001237+b+d);
        assertTrue(cp1.getVertex(0).equals(newA1));
        assertTrue(cp1.getVertex(1).equals(newB1));
        assertTrue(cp1.getVertex(2).equals(newC1));
        assertTrue(cp1.getVertex(3).equals(newD1));
        assertTrue(cp1.getVertex(4).equals(newE1));
        assertEquals(area, cp1.getArea(), DELTA); // Area should be unchanged

    }
    */

    public void testCircleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getRadius", "setRadius", "getCenter");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            double.class, void.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {double.class, double.class},
            new Class[0], new Class[] {double.class}, new Class[0]);

        verifyImplSpecifics(Circle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }
    
    public void testRectangleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeft");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            double.class, void.class, double.class, void.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {double.class, double.class},
            new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, new Class[0]);

        verifyImplSpecifics(Rectangle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    public void testTriangleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getVertexA", "getVertexB", "getVertexC");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            Point.class, Point.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {double.class, double.class},
            new Class[0], new Class[0], new Class[0]);

        verifyImplSpecifics(Triangle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    public void testConvexPolygonImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getVertex", "getNumVertices");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            Point.class, int.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {double.class, double.class},
            new Class[] {int.class}, new Class[0]);

        verifyImplSpecifics(ConvexPolygon.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    private static void verifyImplSpecifics(
        final Class<?> clazz,
        final List<String> expectedMethodNames,
        final List<Class> expectedMethodReturns,
        final List<Class[]> expectedMethodParameters)
        throws NoSuchMethodException
    {
        assertEquals("Unexpected number of public fields",
            0, clazz.getFields().length);

        final List<Method> publicMethods = Arrays.stream(
            clazz.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .collect(Collectors.toList());

        assertEquals("Unexpected number of public methods",
            expectedMethodNames.size(), publicMethods.size());

        assertTrue("Invalid test configuration",
            expectedMethodNames.size() == expectedMethodReturns.size());
        assertTrue("Invalid test configuration",
            expectedMethodNames.size() == expectedMethodParameters.size());

        for (int i = 0; i < expectedMethodNames.size(); i++)
        {
            Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
                expectedMethodParameters.get(i));
            assertEquals(expectedMethodReturns.get(i), method.getReturnType());
        }
    }



    public void runTests() 
    {
        Testy.run(
                () -> testCircleGetArea(),
                () -> testCircleGetPerimeter(),
                () -> testWorkSpaceAreaOfAllShapes(),
                () -> testWorkSpaceGetCircles(),
                () -> testCircleImplSpecifics(),
                () -> testRectangleImplSpecifics(),
                () -> testTriangleImplSpecifics(),
                () -> testConvexPolygonImplSpecifics(),
                () -> testWorkspaceGetRect()
        );
    }
}
