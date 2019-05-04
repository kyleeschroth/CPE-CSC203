
public final class ActivityAction implements Action{

	public Entity entity; 
	public WorldModel world; 
	public int repeatCount;

	public ActivityAction(Entity entity, WorldModel world, int repeatCount){
		this.entity = entity;
		this.world = world; 
		this.repeatCount = repeatCount;
	}

	@Override
	public void executeAction(EventSchedule eventSchedule){
        switch (this.entity.kind)
        {
        case MINER_FULL:
            entity.executeMinerFullActivity(this.world, eventSchedule);
            break;

        case MINER_NOT_FULL:
            entity.executeMinerNotFullActivity(this.world, eventSchedule);
            break;

        case ORE:
            entity.executeOreActivity(this.world, eventSchedule);
            break;

        case ORE_BLOB:
            entity.executeOreBlobActivity(this.world, eventSchedule);
            break;

        case QUAKE:
            entity.executeQuakeActivity(this.world, eventSchedule);
            break;

        case VEIN:
            entity.executeVeinActivity(this.world, eventSchedule);
            break;

        default:
            throw new UnsupportedOperationException(
                String.format("executeActivityAction not supported for %s",
                this.entity.kind));
        }
    }

}



