
public class MinerFull extends EntityMiner{

	public MinerFull(int resourceLimit, Point position, int actionPeriod, int animationPeriod){
		super(position, VirtualWorld.minerFullTiles, resourceLimit, resourceLimit, actionPeriod, animationPeriod);  

	}

    //@Override
	public void executeActivityAction(WorldModel world, EventSchedule eventSchedule){
		//Entity fullTarget = world.findNearest(this.position, EntityKind.BLACKSMITH);
        Blacksmith blacksmith = (Blacksmith) world.findNearest(position, (Entity e) -> e instanceof Blacksmith);

        if (blacksmith != null  &&
            moveTo(world, blacksmith, eventSchedule))
        {
            transform(world, eventSchedule);
        }
        else
        {
            scheduleActions(eventSchedule, world); 
        }
	}

    /*
    @Override
    public void transform(WorldModel world, EventSchedule eventSchedule){
        MinerNotFull miner = new MinerNotFull(this.resourceLimit, this.position, this.actionPeriod, this.animationPeriod);

        world.removeEntity(this);
        eventSchedule.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(eventSchedule, world);
    }
    */

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventSchedule eventSchedule){
        if (Point.adjacent(position, target.position))
        {
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.position);

            if (!position.equals(nextPos))
            {
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    @Override
    public EntityMiner transformation(){
        return new MinerNotFull(resourceLimit, position, actionPeriod, animationPeriod); 
    }
}