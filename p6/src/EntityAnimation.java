import edu.calpoly.spritely.Tile;

import java.util.List;


public abstract class EntityAnimation extends EntityAction implements Animatable{

    protected int animationPeriod; 
    protected int tileIndex; 
    protected int repeatCount; 

    public EntityAnimation(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod){
        super(position, tiles, actionPeriod); 
        this.animationPeriod = animationPeriod; 
        this.tileIndex = 0; 
        this.repeatCount = 0; 
    }
    
    @Override
    public void scheduleActions(EventSchedule eventSchedule, WorldModel world){
            //eventSchedule.scheduleEvent(this, createActivityAction(world), getActionPeriod());
            super.scheduleActions(eventSchedule, world); 
            eventSchedule.scheduleEvent(this, createAnimationAction(world, repeatCount), getAnimationPeriod());
    }

    @Override
    public abstract void executeActivityAction(WorldModel world, EventSchedule eventSchedule);
    
    @Override
    public int getAnimationPeriod(){
        return animationPeriod; 
    }

    @Override
    public void nextImage()
    {
        this.tileIndex = (this.tileIndex + 1) % this.tiles.size();
    }

    @Override
    public AnimationAction createAnimationAction(WorldModel world, int repeatCount)
    {
        return new AnimationAction(this, world, repeatCount);
    }

    @Override
    public Tile getCurrentTile()
    {
        return this.tiles.get(this.tileIndex);
    }
}