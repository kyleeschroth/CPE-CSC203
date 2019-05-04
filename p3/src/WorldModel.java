import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.awt.Color; 

import java.lang.Math;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.SolidColorTile; 

/**
 * Data structures that hold the model of our virtual world.
 * It consists of a grid.  Each point on the grid is occupied
 * by a background tile, and, optionally, an Entity.
 */
final class WorldModel
{
    public final Size size;
    public final Tile background[][];
    public final Entity occupant[][];
    public final Set<Entity> entities;

    public double daylightColor; 
    //public static long currentTimeMillis;
    public double LastTimeDaylight = 0;
    public int Period = 1;

    public Size getSize(){
        return this.size; 
    }

    public Tile[][] getBackground(){
        return this.background; 
    }

    public Entity[][] getOccupant(){
        return this.occupant; 
    }


    public WorldModel(Size gridSize)
    {
        this.size = gridSize;
        this.background = new Tile[gridSize.height][gridSize.width];
        this.occupant = new Entity[gridSize.height][gridSize.width];
        this.entities = new HashSet<Entity>();
    }
    public Entity getOccupant(Point pos)
    {
        if (this.isOccupied(pos)) {
            return this.getOccupantCell(pos);
        } else { 
            return null;
        }
    }

    private Entity getOccupantCell(Point pos)
    {
        return this.occupant[pos.getY()][pos.getX()];
    }

    private void setOccupantCell(Point pos, Entity entity)
    {
        this.occupant[pos.getY()][pos.getX()] = entity;
    }

    public boolean isOccupied(Point pos) //static so VirtualModel/addInitial can access it
    {
        return withinBounds(pos) && getOccupantCell(pos) != null;
    }

    private boolean withinBounds(Point pos) //static so isOccuppied can access it
    {
        return pos.getY() >= 0 && pos.getY() < this.size.height &&
            pos.getX() >= 0 && pos.getX() < this.size.width;
    }

    public Entity findNearest(Point pos, EntityKind kind)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : this.entities)
        {
            if (entity.kind == kind)
            {
                ofType.add(entity);
            }
        }

        return nearestEntity(ofType, pos);
    }

    public void removeEntity(Entity entity)
    {
        removeEntityAt(entity.position);
    }

    public void addEntity(Entity entity)
    {
        if (withinBounds(entity.position))
        {
            setOccupantCell(entity.position, entity);
            this.entities.add(entity);
        }
    }

    public Point findOpenAround(Point pos)
    {
        for (int dy = -1; dy <= 1; dy++)
        {
            for (int dx = -1; dx <= 1; dx++)
            {
                Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
                if (withinBounds(newPt) &&
                    !isOccupied(newPt))
                {
                    return newPt;
                }
            }
        }

        return null;
    }

    public void moveEntity(Entity entity, Point pos)
    {
        Point oldPos = entity.position;
        if (withinBounds(pos) && !pos.equals(oldPos))
        {
            setOccupantCell(oldPos, null);
            removeEntityAt(pos);
            setOccupantCell(pos, entity);
            entity.position = pos;
        }
    }

    private void removeEntityAt(Point pos)
    {
        if (withinBounds(pos)
            && getOccupantCell(pos) != null)
        {
            Entity entity = getOccupantCell(pos);

            /* this moves the entity just outside of the grid for
                debugging purposes */
            entity.position = new Point(-1, -1);
            this.entities.remove(entity);
            setOccupantCell(pos, null);
        }
    }
    /*
    public void paint(AnimationFrame frame) {
        
        //SolidColorTile daylight = new SolidColorTile(green, "daylight");

        for (int y = 0; y < size.height; y++) {
            for (int x = 0; x < size.width; x++) {
                //System.out.println(background[y][x]);
                if (background[y][x] != null){
                    frame.addTile(x, y, background[y][x]);
                }
                if ((System.currentTimeMillis() - LastTimeDaylight) > 100){
                    System.out.println("In if");
                    this.daylightColor = (0.5 * (1.0 - Math.cos((Math.PI * this.Period * 2))));
                    System.out.println(Math.cos((Math.PI * this.Period * 2))); 
                    System.out.println(Math.PI * this.Period * 2); 
                    System.out.println(Math.PI);
                    this.Period ++;
                    Color green = new Color(0, 255, 0, (int) this.daylightColor);
                    System.out.println(this.daylightColor); 
                    System.out.println(this.Period); 
                    SolidColorTile daylight = new SolidColorTile(green, '.');
                    LastTimeDaylight = System.currentTimeMillis(); 

                    frame.addTile(x, y, daylight);


                }
                Entity occupant = this.occupant[y][x];
                if (occupant != null) {
                    Tile tile = occupant.getCurrentTile();
                    frame.addTile(x, y, tile);
                }
            }
        }
    }
    */

    public void paint(AnimationFrame frame) {
        for (int y = 0; y < size.height; y++) {
            for (int x = 0; x < size.width; x++) {
                //System.out.println(background[y][x]);
                if (background[y][x] != null){
                    frame.addTile(x, y, background[y][x]);
                }
                //'frame.addTile(x, y, background[y][x]);
                Entity occupant = this.occupant[y][x];
                if (occupant != null) {
                    Tile tile = occupant.getCurrentTile();
                    frame.addTile(x, y, tile);
                }
            }
        }
    }


    private static Entity nearestEntity(List<Entity> entities, Point pos)
    {
        if (entities.isEmpty()) {
            return null;
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = Point.distanceSquared(nearest.position, pos);

            for (Entity other : entities)
            {
                int otherDistance = Point.distanceSquared(other.position, pos);

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }
            return nearest;
        }
    }

}