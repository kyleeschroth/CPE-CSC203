
public class Ore extends EntityAction{

	public Ore(Point position, int actionPeriod){
		super(position, VirtualWorld.oreTiles, actionPeriod);
	}

	//@Override
	public void executeActivityAction(WorldModel world, EventSchedule eventSchedule){
    {
        Point pos = this.position;    // store current position before removing

        world.removeEntity(this);
        eventSchedule.unscheduleAllEvents(this);

        //Entity blob = createOreBlob(pos, this.actionPeriod / 4,50 + rand.nextInt(100));
        OreBlob blob = new OreBlob(pos, this.actionPeriod / 4, 50 + rand.nextInt(100)); 

        world.addEntity(blob);
        blob.scheduleActions(eventSchedule, world);
    }
	}

}