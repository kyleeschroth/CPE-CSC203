import java.util.List;
import java.util.Random;
import edu.calpoly.spritely.Tile;
import java.util.List;
import java.util.Random;

/**
 * An entity in our virtual world.  An entity occupies a square
 * on the grid.  It might move around, and interact with other
 * entities in the world.
 */
//final class Entity implements Animatable
public abstract class Entity{
    protected Point position;
    protected List<Tile> tiles;

    public Entity(Point position,
                  List<Tile> tiles)
    {
        this.position = position;
        this.tiles = tiles;
    }

    public static final Random rand = new Random(); //needs work

    public Tile getCurrentTile(){
        return tiles.get(0); 
    }

    public Point getPosition()
    {
        return position; 
    }
}
