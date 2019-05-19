
public class Quake extends EntityAnimation{

	public Quake(Point position){
		super(position, VirtualWorld.quakeTiles, 1100, 100); 
		repeatCount = 10; 
	}

	@Override
	public void executeActivityAction(WorldModel world, EventSchedule eventSchedule){
		eventSchedule.unscheduleAllEvents(this);
        world.removeEntity(this);
	}
}