
/**
 * An action data structure records information about
 * a future action an entity is to perform.  It 
 * is attached to an Event data structure.
 */

final class Action
{
    public ActionKind kind;
    public Entity entity;
    public WorldModel world;
    public int repeatCount;     // A repeat count of 0 means to repeat forever

    public Action(ActionKind kind, Entity entity, WorldModel world,
                  int repeatCount)
    {
        this.kind = kind;
        this.entity = entity;
        this.world = world;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventSchedule eventSchedule)
    {
        switch (kind)
        {
        case ACTIVITY:
            executeActivityAction(eventSchedule);
            break;

        case ANIMATION:
            executeAnimationAction(eventSchedule);
            break;
        }
    }

    public void executeAnimationAction(EventSchedule eventSchedule)
    {
        entity.nextImage();

        if (this.repeatCount != 1)
        {
            eventSchedule.scheduleEvent(this.entity, 
                entity.createAnimationAction(
                    Math.max(this.repeatCount - 1, 0)),
                entity.getAnimationPeriod());
        }
    }

    public void executeActivityAction(EventSchedule eventSchedule)
    {
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
