import edu.calpoly.spritely.Tile;
import java.util.Random;
import java.util.List;


public abstract class EntityAction extends Entity implements Executable{

	protected int actionPeriod;
	protected final Random rand = new Random();

	public EntityAction(Point position, List<Tile> tiles, int actionPeriod){
		super(position, tiles);
		this.actionPeriod = actionPeriod; 
	}

	public void scheduleActions(EventSchedule eventSchedule, WorldModel world){
		eventSchedule.scheduleEvent(this, createActivityAction(world), this.actionPeriod);
	}

	@Override
	public abstract void executeActivityAction(WorldModel world, EventSchedule eventSchedule);

	public ActivityAction createActivityAction(WorldModel world)
    {
        return new ActivityAction(this, world);
    }

    public int getActionPeriod(){
    	return actionPeriod; 
    }
}