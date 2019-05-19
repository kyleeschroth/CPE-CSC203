
public class ActivityAction implements Action{
	/*
	public Entity entity; 
	public WorldModel world; 
	public int repeatCount;

	public ActivityAction(Entity entity, WorldModel world, int repeatCount){
		this.entity = entity;
		this.world = world; 
		this.repeatCount = repeatCount;
	}
	*/
    /*
	@Override
	public void executeAction(EventSchedule eventSchedule){
            throw new UnsupportedOperationException(
                String.format("executeActivityAction not supported for %s",
                this));
    }
    */
    private final Executable executable; 
    private final WorldModel world; 

    public ActivityAction(Executable executable, WorldModel world){
    	this.executable = executable; 
    	this.world = world; 
    }


    @Override
    public void executeAction(EventSchedule eventSchedule){
    	executable.executeActivityAction(world, eventSchedule); 
    }
}
    





