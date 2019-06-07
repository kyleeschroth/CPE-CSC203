

import edu.calpoly.testy.Testy;
import java.util.List;
import java.util.ArrayList;
import static edu.calpoly.testy.Assert.assertFalse; 
import edu.calpoly.spritely.Size; 
import static edu.calpoly.testy.Assert.assertEquals; 


/**
 * This class contains unit tests for Minecraft 2: Electric Boogaloo.
 */
public class TestCases {

    private void loadImagesTest() {
        // This will fail if an image name is misspelled.  By doing this
        // here, we make checkgit test image loading.
        //VirtualWorld.loadEntityImages();
        assertFalse(VirtualWorld.blacksmithTiles==null); 
        assertFalse(VirtualWorld.blobTiles==null); 
        assertFalse(VirtualWorld.minerTiles==null); 
        assertFalse(VirtualWorld.obstacleTiles==null); 
        assertFalse(VirtualWorld.oreTiles==null); 
        assertFalse(VirtualWorld.quakeTiles==null); 
        assertFalse(VirtualWorld.veinTiles==null); 
        assertFalse(VirtualWorld.minerFullTiles==null); 
        assertFalse(VirtualWorld.grassTiles==null); 
        assertFalse(VirtualWorld.rockTiles==null); 
    }

    private void testAnimatable1(){
        FakeAnimation f = new FakeAnimation(0);
        AnimationAction a = new AnimationAction(f, new WorldModel(new Size(1,1)), 30);
        EventSchedule e = new EventSchedule();
        e.scheduleEvent(f, a, 0);
        e.processEvents(100); 
        assertEquals(f.counter, 30);
    }

    private void testAnimatable2(){
        EventSchedule e = new EventSchedule();
        FakeAnimation t = new FakeAnimation(0);
        AnimationAction a = new AnimationAction(t, new WorldModel(new Size(1,1)), 100);
        e.scheduleEvent(t, a, 0);
        e.processEvents(100);
        assertEquals(t.counter, 100);
    }

    
    /**
     * Run the tests.
     *
     * @return The number of failures.
     */
    
    public int runTests() {
        return Testy.run(
                () -> loadImagesTest(),
                () -> testAnimatable1(), 
                () -> testAnimatable2()
        );           
    }
}
