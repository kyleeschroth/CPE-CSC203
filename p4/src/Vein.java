
public class Vein extends EntityAction{

	public Vein(Point position, int actionPeriod){
		super(position, VirtualWorld.veinTiles, actionPeriod);
	}

    @Override
	public void executeActivityAction(WorldModel world, EventSchedule eventSchedule){
        Point openPt = world.findOpenAround(this.position);

        if (openPt != null) {
            //Entity ore = createOre(openPt, 20000 + rand.nextInt(10000));
            Ore ore = new Ore(openPt, 20000 + rand.nextInt(10000));
            world.addEntity(ore);
            ore.scheduleActions(eventSchedule, world);
        }

        eventSchedule.scheduleEvent(this,
            createActivityAction(world),
            this.actionPeriod);
	}
}