
public class MinerNotFull extends EntityMiner{

	public MinerNotFull(int resourceLimit, Point position, int actionPeriod, int animationPeriod){
		super(position, VirtualWorld.minerTiles, resourceLimit, 0, actionPeriod, animationPeriod); 
	}

    @Override
	public void executeActivityAction(WorldModel world, EventSchedule eventSchedule){
		//Entity notFullTarget = world.findNearest(this.position, EntityKind.ORE);
        Ore ore = (Ore) world.findNearest(position, (Entity e) -> e instanceof Ore); 

        if (ore != null && moveTo(world, ore, eventSchedule) && isFull()){
            transform(world, eventSchedule); 
        }
        else
        {
            scheduleActions(eventSchedule, world); 
        }
	}

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventSchedule eventSchedule){
        if (Point.adjacent(this.position, target.position))
        {
            this.resourceCount += 1;
            world.removeEntity(target);
            eventSchedule.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.position);

            if (!this.position.equals(nextPos))
            {
                world.moveEntity(this, nextPos);
            }
            return false;
        }

    }


    @Override
    public EntityMiner transformation(){
        return new MinerFull(resourceLimit, position, actionPeriod, animationPeriod); 
    }
    
}