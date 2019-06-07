
public interface Animatable{

	void nextImage(); 
	AnimationAction createAnimationAction(WorldModel world, int repeatCount); 
	int getAnimationPeriod();

}
