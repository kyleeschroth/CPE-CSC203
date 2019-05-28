import edu.calpoly.spritely.SolidColorTile; 
import java.awt.Color; 
import edu.calpoly.spritely.Size; 
import edu.calpoly.spritely.Tile; 


public class GrassTile implements Animatable{
	
	public int AnimationPeriod; 
	public WorldModel world; 

	public GrassTile(int AnimationPeriod, WorldModel world){
		this.AnimationPeriod = AnimationPeriod; 
		this.world = world; 
	}

	@Override
	public int getAnimationPeriod(){
		return this.AnimationPeriod; 
	}

	@Override
	public AnimationAction createAnimationAction(WorldModel world, int repeatCount){
		return new AnimationAction(null, world, 1); 
	}

	public double Alpha(double time){
		return 0.3*(0.5 * (1.0 - Math.cos((Math.PI * time * 2)/30)));
	}

	@Override
	public void nextImage(){

		for (int y = 0; y < world.getSize().height; y++) {
            for (int x = 0; x < world.getSize().width; x++) {
                if (world.getBackground()[y][x] != null){
                	//System.out.println(VirtualWorld.getFrame()); 
                	VirtualWorld.getFrame().addTile(x, y, world.getBackground()[y][x]);

                	Entity occupant = world.getOccupant()[y][x];
                
                	float alpha = (float) Alpha(VirtualWorld.getTime());
                	// System.out.println("here"); 
                	//System.out.println(VirtualWorld.getTime()); 
                	//System.out.println(alpha); 

                	if (world.getBackground()[y][x].getText() == '.'){
                		VirtualWorld.getFrame().addTile(x, y, new SolidColorTile(new Color(0f, 1f, 0f, alpha), 'x')); 

                	}
                	if (occupant != null) {
                    	Tile tile = occupant.getCurrentTile();
                    	VirtualWorld.getFrame().addTile(x, y, tile);
                	}



                }
            }
        }
	}

		/*
        processEvents(); 
        GrassTile grass = new GrassTile(int AnimationPeriod, WorldModel world); 

        Action daylightAction = createAnimationAction(world, repeatCount); 

        scheduleEvent(null, daylightAction, 1); 
		*/

}






