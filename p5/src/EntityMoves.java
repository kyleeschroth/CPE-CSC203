import edu.calpoly.spritely.Tile;

import java.util.List;

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
    }

    protected abstract boolean nextPosCondition(WorldModel world, Point newPos);
}