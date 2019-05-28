import edu.calpoly.spritely.Tile;

import java.util.List;
import java.lang.Math; 

import java.util.ArrayList; 

public abstract class EntityMoves extends EntityAnimation{

	//public int animationPeriod; 

    public EntityMoves(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod){
        super(position, tiles, actionPeriod, animationPeriod);
    }
    /*
    @Override
    public void scheduleActions(EventSchedule eventSchedule, WorldModel world){
    	eventSchedule.scheduleEvent(this, createActivityAction(world), getActionPeriod());
        eventSchedule.scheduleEvent(this, createAnimationAction(world, 0), getAnimationPeriod());
    }
    */

    protected abstract boolean moveTo(WorldModel world, Entity target, EventSchedule eventSchedule); 

    public Point nextPosition(WorldModel world, Point destPos)
    {
        /*
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.position.getX() + horiz,
            this.position.getY());

        Entity occupant = world.getOccupant(newPos);

        if (horiz == 0 || nextPosCondition(world, newPos))
        {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(), 
                               this.position.getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 || nextPosCondition(world, newPos))
            {
                newPos = this.position;
            }
        }
        return newPos;
        */
        List<Point> path = aStar.computePath(position, destPos, 
            p -> canPassThrough(world, p) && world.withinBounds(p), 
            EntityMoves::potentialNeighbors, EntityMoves::stepsFromTo);

        if (path == null || path.isEmpty()) {
            return this.getPosition(); 
        }
        else{
            return path.get(0);
        }

    }

    protected abstract boolean nextPosCondition(WorldModel world, Point newPos);

    private static PathingStrategy aStar = new AStarPathingStrategy(); 

    protected abstract boolean canPassThrough(WorldModel world, Point point); 

    private static List<Point> potentialNeighbors(Point point){
        List<Point> neighbors = new ArrayList<>(4); 
        neighbors.add(new Point(point.getX(), point.getY() + 1));
        neighbors.add(new Point(point.getX(), point.getY() - 1));
        neighbors.add(new Point(point.getX() + 1, point.getY())); 
        neighbors.add(new Point(point.getX() - 1, point.getY())); 
        return neighbors; 
    }

    private static int stepsFromTo(Point p1, Point p2){
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()); 
    }
}