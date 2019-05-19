
public final class AnimationAction implements Action{

	public Entity entity; 
	public WorldModel world; 
	public int repeatCount; 
	public Animatable animatable; 

	public AnimationAction(Animatable animatable, WorldModel world, int repeatCount){
		this.animatable = animatable; 
		this.world = world; 
		this.repeatCount = repeatCount; 
	}

	@Override
	public void executeAction(EventSchedule eventSchedule)
    {
        animatable.nextImage();

        if (this.repeatCount != 1)
        {
            eventSchedule.scheduleEvent(this.animatable, 
                animatable.createAnimationAction(world,
                    Math.max(this.repeatCount - 1, 0)),
                animatable.getAnimationPeriod());
        }
    }
}

