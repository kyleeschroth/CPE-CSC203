
public class OreBlob extends EntityMoves{

	public OreBlob(Point position, int actionPeriod, int animationPeriod){
		super(position, VirtualWorld.blobTiles, actionPeriod, animationPeriod); 
	}

    @Override
	public void executeActivityAction(WorldModel world, EventSchedule eventSchedule)
	{
        //Entity blobTarget = world.findNearest(this.position, EntityKind.VEIN);
        Vein vein = (Vein) world.findNearest(position, (Entity e) -> e instanceof Vein); 
        long nextPeriod = getActionPeriod();

        if (vein != null)
        {
            Point tgtPos = vein.position;

            if (moveTo(world, vein, eventSchedule))
            {
                //Entity quake = createQuake(tgtPos);
                Quake quake = new Quake(tgtPos); 

                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(eventSchedule, world);
            }
        }

        eventSchedule.scheduleEvent(this,
            createActivityAction(world),
            nextPeriod);
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventSchedule eventSchedule){
        if (Point.adjacent(this.position, target.position))
        {
            world.removeEntity(target);
            eventSchedule.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.position);

            if (!this.position.equals(nextPos))
            {
                Entity occupant = world.getOccupant(nextPos);
                if (occupant != null)
                {
                    eventSchedule.unscheduleAllEvents(occupant);
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    @Override
    protected boolean nextPosCondition(WorldModel world, Point newPos){
        Entity occupant = world.getOccupant(newPos); 

        return (occupant != null && !(occupant instanceof Ore));
    }

    @Override
    protected boolean canPassThrough(WorldModel world, Point next) {
            if (world.getOccupant(next) instanceof Ore || world.getOccupant(next) == null) {
                return true;
        }
            return false;
    }

}