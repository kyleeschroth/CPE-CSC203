import edu.calpoly.spritely.Tile;

import java.util.List;

public abstract class EntityMiner extends EntityMoves {

	protected int resourceCount; 
	protected int resourceLimit;

	public EntityMiner(Point position, List<Tile> tiles, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod){
		super(position, tiles, actionPeriod, animationPeriod);
		this.resourceLimit = resourceLimit; 
		this.resourceCount = resourceCount; 
	}
	
	public abstract EntityMiner transformation(); 

	public void transform(WorldModel world, EventSchedule eventSchedule){
		EntityMiner miner = transformation(); 

		world.removeEntity(this); 
		eventSchedule.unscheduleAllEvents(this); 

		world.addEntity(miner); 
		miner.scheduleActions(eventSchedule, world); 
	}

	public boolean isFull()
    {
        return resourceCount >= resourceLimit;
    }
    
	@Override
	protected boolean nextPosCondition(WorldModel world, Point newPos){
		return !world.isOccupied(newPos); 
	}
	

	@Override
    protected boolean canPassThrough(final WorldModel world, final Point newPos) {
        return !world.isOccupied(newPos);
    }

















}