import java.util.ArrayList; 
import java.util.List; 

public class Cow extends EntityMiner{

	public Cow(int resourceLimit, Point position, int actionPeriod, int animationPeriod){
		super(position, VirtualWorld.cowTiles, resourceLimit, resourceLimit, actionPeriod, animationPeriod);  

	}

	public void executeActivityAction(WorldModel world, EventSchedule eventSchedule){
	//Entity fullTarget = world.findNearest(this.position, EntityKind.BLACKSMITH);
        //OreBlob blob = (OreBlob) world.findNearest(position, (Entity e) -> e instanceof OreBlob);
        MinerNotFull m = (MinerNotFull) world.findNearest(position, (Entity e) -> e instanceof MinerNotFull);

        if (m != null  &&
            moveTo(world, m, eventSchedule))
        {
            //transform(world, eventSchedule);
            changeToCow(world, eventSchedule, m);
        }
        else
        {
            scheduleActions(eventSchedule, world); 
        }
	}

	@Override
    public boolean moveTo(WorldModel world, Entity target, EventSchedule eventSchedule){
        if (this.position.adjacent(target.position))
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
    public Cow transformation(){
        return new Cow(2, position, 900, 100); 
    }

    @Override
    public List<Point> potentialNeighbors(Point point){
        List<Point> neighbors = new ArrayList<>(4); 
        neighbors.add(new Point(point.getX() - 1, point.getY() + 1));
        neighbors.add(new Point(point.getX() + 1, point.getY() - 1));
        neighbors.add(new Point(point.getX() + 1, point.getY() + 1)); 
        neighbors.add(new Point(point.getX() - 1, point.getY() - 1)); 
        return neighbors; 
    }

    @Override
    public void transform(WorldModel world, EventSchedule eventSchedule){
        Cow cow = transformation(); 

        world.removeEntity(this); 
        eventSchedule.unscheduleAllEvents(this); 
        
        world.addEntity(cow); 
        cow.scheduleActions(eventSchedule, world); 
    }
    
    public void changeToCow(WorldModel world, EventSchedule eventSchedule, Entity target){
        Cow cow = new Cow(2, this.position, 900, 100); 

        //eventSchedule.unscheduleAllEvents(this);
        eventSchedule.unscheduleAllEvents(target);
        //world.removeEntity(this);
        world.removeEntity(target);
        world.addEntity(cow); 
        cow.executeActivityAction(world, eventSchedule);
        cow.scheduleActions(eventSchedule, world); 
    }
}