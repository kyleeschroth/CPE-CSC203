
public class FakeAnimation implements Animatable{
	public int counter = 0; 
	public int AnimationPeriod; 

	public FakeAnimation(int AnimationPeriod){
		//this.counter ++; 
		this.AnimationPeriod = AnimationPeriod; 
	}

	@Override
	public void nextImage(){
		this.counter += 1; 
	}

	@Override
	public int getAnimationPeriod(){
		return 0; 
	}
	
	@Override
	public AnimationAction createAnimationAction(WorldModel world, int repeatCount){
		return new AnimationAction(this, world, repeatCount); 
	}
}