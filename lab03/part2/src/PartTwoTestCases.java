import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static edu.calpoly.testy.Assert.assertEquals;
import static edu.calpoly.testy.Assert.assertTrue;
import static edu.calpoly.testy.Assert.fail;
import edu.calpoly.testy.Testy;

public class PartTwoTestCases
{
    public static final double DELTA = 0.00001;


    public void testPerimPoly(){
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(3,1));
        points.add(new Point(1,4));
        points.add(new Point(-1,4));
        Polygon myPoly = new Polygon(points); 
        double d = myPoly.perimeter();
        assertEquals(12.8909345613, d, DELTA);
    }

    public void testPerimCircle(){
        Circle circle = new Circle(new Point(1,1), 2.0);
        double d = circle.perimeter();
        assertEquals(4.0 * Math.PI, d, DELTA);
    }

    public void testRect(){
        Rectangle rect = new Rectangle(new Point(-1, 2), new Point(1, -1.6));
        double d = rect.perimeter();
        assertEquals(11.2 , d, DELTA);
    }

    public void testwhichIsBigger(){
        Circle circ = new Circle(new Point(1.0, 1.0), 2.0);
        Rectangle rect = new Rectangle(new Point(-1.0, 2.0), new Point(1.0, -1.6));
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(3.0, 1.0));
        points.add(new Point(1.0, 4.0));
        points.add(new Point(-1.0, 4.0));
        Polygon poly = new Polygon(points);
        double d = Bigger.whichIsBigger(circ, rect, poly);
        double f = poly.perimeter();
        assertEquals(f, d, DELTA);
    }

    public void testCircleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getCenter", "getRadius", "perimeter");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Point.class, double.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[0], new Class[0]);

        verifyImplSpecifics(Circle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    public void testRectangleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getTopLeft", "getBottomRight", "perimeter");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Point.class, Point.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[0], new Class[0]);

        verifyImplSpecifics(Rectangle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    public void testPolygonImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getPoints", "perimeter");

        final List<Class> expectedMethodReturns = Arrays.asList(
            List.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[0]);

        verifyImplSpecifics(Polygon.class, expectedMethodNames,
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
                () -> testPerimPoly(),
                () -> testRect(),
                () -> testPerimCircle(),
                () -> testwhichIsBigger(),
                () -> testCircleImplSpecifics(),
                () -> testRectangleImplSpecifics(),
                () -> testPolygonImplSpecifics()
        );
    }
}
