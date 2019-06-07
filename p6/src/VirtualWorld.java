import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.ImageTile;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.SpriteWindow;
import edu.calpoly.spritely.Tile;
import java.util.Collections; 

/**
 * A representation of a virtual world, containing various entities
 * that move around a grid.  The data structures representing the
 * current state of the virtual world are split out in a separate
 * model class, called WorldModel.
 */
public final class VirtualWorld
{
    public static final Size TILE_SIZE = new Size(32, 32);
    public static final Size WORLD_SIZE = new Size(40, 30);
    // Name, as decided by CSC 203 in Spring 2018:
    public static final String NAME = "Minecraft 2: Electric Boogaloo";
    public static final File IMAGE_DIR = new File("images");

    public static final String[] BACKGROUND; 

    public static double timeScale;

    public static final List<Tile> blacksmithTiles;
    public static final List<Tile> blobTiles;
    public static final List<Tile> minerTiles;
    public static final List<Tile> obstacleTiles;
    public static final List<Tile> oreTiles;
    public static final List<Tile> quakeTiles;
    public static final List<Tile> veinTiles;
    public static final List<Tile> minerFullTiles;
    public static final List<Tile> grassTiles; 
    public static final List<Tile> rockTiles;
    public static final List<Tile> cowTiles; 

    public static final List<Tile> rainbowTiles; 

    private static AnimationFrame frame; 

    public static AnimationFrame getFrame(){
        return frame; 
    }

    public static WorldModel model;
    public static EventSchedule eventSchedule;
    public static SpriteWindow window;

    //public static Tile grassTile; 
    //public static Tile rockTile; 

    public static double getTime(){
        //System.out.println(window.getTimeSinceStart()); 
        return (window.getTimeSinceStart() * timeScale)/1000; 
    }

    private static Tile getImageTile(String imageFileName, char text) {
        Tile t = null;
        File f = new File(IMAGE_DIR, imageFileName);
        try {
            t = new ImageTile(f, TILE_SIZE, text);
        } catch (IOException ex) {
            System.out.println("Fatal error:  Image not found in " + f);
            ex.printStackTrace();
            System.exit(1);
        }
        return t;
    }

    public VirtualWorld(double timeScale)
    {
        this.timeScale = timeScale; 
        Tile rainbow = getImageTile("rainbow.png", '-'); 
        window = new SpriteWindow(NAME, WORLD_SIZE);
        window.setFps(30f);
        window.setTileSize(TILE_SIZE);
        System.out.println(NAME + ".  Press 'q' to quit.");
        
        window.setMouseClickedHandler((int x, int y) -> {
            for (int i = x-1; i <= x+1; i++){
                for (int j = y-1; j <= y+1; j++){
                    Point clicked = new Point(i, j); 
                    //if ((i < WORLD_SIZE.width) && (i > 0) && (j < WORLD_SIZE.height) && (j > 0)){
                    if (!model.withinBounds(new Point(i,j))){
                        continue;
                    }
                    else if (model.isOccupied(clicked)){
                        Entity bye = model.getOccupant(clicked); 
                        eventSchedule.unscheduleAllEvents(bye);
                        model.removeEntity(bye);
                        Cow cow = new Cow(2, new Point(x, y), 900, 100);
                        model.addEntity(cow); 
                        cow.executeActivityAction(model, eventSchedule);
                    }
                    else{
                        model.background[j][i] = rainbow;
                    }
                }
            }
            Cow cow = new Cow(2, new Point(x, y), 900, 100);
            model.addEntity(cow); 
            cow.executeActivityAction(model, eventSchedule);

        });
        window.setKeyTypedHandler((char ch) -> {
            if (ch == 'q' || ch == 'Q') {
                window.stop();
            }
        });
        model = new WorldModel(WORLD_SIZE);
        eventSchedule = new EventSchedule();
        setupBackground();
        //loadEntityImages();
        createInitialEntities();
        scheduleInitialActions(model, eventSchedule);
    }

    private void setupBackground() {
        Tile grass = getImageTile("grass.png", '.');
        Tile rocks = getImageTile("rocks.png", '=');
        for (int y = 0; y < WORLD_SIZE.height; y++) {
            for (int x = 0; x < WORLD_SIZE.width; x++) {
                char c = BACKGROUND[y].charAt(x);
                if (c == ' ') {
                    model.background[y][x] = grass;
                } else if (c == 'R') {
                    model.background[y][x] = rocks;
                } else {
                    assert false;
                }
            }
        }
    }

    /*
    public static void loadEntityImages() {
        blacksmithTiles = loadImages("blacksmith", "B");
        blobTiles = loadImages("blob", "===*===*=");
        minerTiles = loadImages("miner", "mMmMm");
        obstacleTiles = loadImages("obstacle", "O");
        oreTiles = loadImages("ore", "$");
        quakeTiles = loadImages("quake", "QqQqQq");
        veinTiles = loadImages("vein", "V");
        minerFullTiles = loadImages("miner_full", "mM$mM");
        grassTiles = loadImages("grass.png", ".");
        rockTiles = loadImages("rocks.png", "-");
    }
    */

    static{
        BACKGROUND = new String[]{
        "                   R                    ",
        "                    R                  R",
        " RR   RR   RR                           ",
        "R  R R  R R  R                          ",
        "   R R  R    R                          ",
        " RR  R  R  RR                           ",
        "R    R  R    R                          ",
        "R    R  R R  R                          ",
        "RRRR  RR   RR                           ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                    R                  R",
        "                   R                    ",
        "                    R                  R",
        "                   R                    ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                   R                    ",
        "                    R                   "
        };

        blacksmithTiles = Collections.unmodifiableList(loadImages("blacksmith", "B"));
        blobTiles = Collections.unmodifiableList(loadImages("blob", "===*===*="));
        minerTiles = Collections.unmodifiableList(loadImages("miner", "mMmMm"));
        obstacleTiles = Collections.unmodifiableList(loadImages("obstacle", "O"));
        oreTiles = Collections.unmodifiableList(loadImages("ore", "$"));
        quakeTiles = Collections.unmodifiableList(loadImages("quake", "QqQqQq"));
        veinTiles = Collections.unmodifiableList(loadImages("vein", "V"));
        minerFullTiles = Collections.unmodifiableList(loadImages("miner_full", "mM$mM"));
        grassTiles = Collections.unmodifiableList(loadImages("grass", "."));
        rockTiles = Collections.unmodifiableList(loadImages("rocks", "-"));
        rainbowTiles = Collections.unmodifiableList(loadImages("rainbow", "+"));
        cowTiles = Collections.unmodifiableList(loadImages("cow", "y"));
        //grassTile = Collections.unmodifiableList(getImageTile("grass.png", '.')); 
        //rockTile = Collections.unmodifiableList(getImageTile("rocks.png", '=')); 
    }

    private void createInitialEntities() {
        addInitial(new Blacksmith(new Point(0, 11)));
        addInitial(new Blacksmith(new Point(0, 29)));
        addInitial(new Blacksmith(new Point(19, 14)));
        addInitial(new Blacksmith(new Point(19, 29)));
        addInitial(new Blacksmith(new Point(20, 0)));
        addInitial(new Blacksmith(new Point(39, 0)));
        addInitial(new Blacksmith(new Point(39, 14)));
        addInitial(new Blacksmith(new Point(39, 29)));
        addInitial(new MinerNotFull(2, new Point(12,23), 954, 300));
        addInitial(new MinerNotFull(2, new Point(17,22), 982, 310));
        addInitial(new MinerNotFull(2, new Point(23,6), 777, 320));
        addInitial(new MinerNotFull(2, new Point(24,26), 851, 90));
        addInitial(new MinerNotFull(2, new Point(31,15), 933, 95));
        addInitial(new MinerNotFull(2, new Point(31,26), 734, 87));
        addInitial(new MinerNotFull(2, new Point(37,10), 400, 33));
        addInitial(new MinerNotFull(2, new Point(37,18), 888, 100));
        addInitial(new MinerNotFull(2, new Point(37,6), 991, 317));
        addInitial(new MinerNotFull(2, new Point(5,6), 992, 318));
        addInitial(new MinerNotFull(2, new Point(6,25), 930, 106));
        addInitial(new MinerNotFull(2, new Point(6,3), 813, 92));
        addInitial(new MinerNotFull(2, new Point(7,13), 913, 97));
        addInitial(new Obstacle(new Point(10, 23)));
        addInitial(new Obstacle(new Point(10, 24)));
        addInitial(new Obstacle(new Point(11, 21)));
        addInitial(new Obstacle(new Point(11, 24)));
        addInitial(new Obstacle(new Point(11, 25)));
        addInitial(new Obstacle(new Point(12, 22)));
        addInitial(new Obstacle(new Point(12, 25)));
        addInitial(new Obstacle(new Point(12, 26)));
        addInitial(new Obstacle(new Point(13, 22)));
        addInitial(new Obstacle(new Point(13, 26)));
        addInitial(new Obstacle(new Point(14, 23)));
        addInitial(new Obstacle(new Point(14, 24)));
        addInitial(new Obstacle(new Point(26, 26)));
        addInitial(new Obstacle(new Point(27, 25)));
        addInitial(new Obstacle(new Point(28, 19)));
        addInitial(new Obstacle(new Point(28, 25)));
        addInitial(new Obstacle(new Point(29, 20)));
        addInitial(new Obstacle(new Point(29, 26)));
        addInitial(new Obstacle(new Point(30, 21)));
        addInitial(new Obstacle(new Point(31, 22)));
        addInitial(new Obstacle(new Point(32, 23)));
        addInitial(new Obstacle(new Point(5, 20)));
        addInitial(new Obstacle(new Point(5, 21)));
        addInitial(new Obstacle(new Point(6, 20)));
        addInitial(new Obstacle(new Point(6, 21)));
        addInitial(new Obstacle(new Point(7, 20)));
        addInitial(new Obstacle(new Point(7, 21)));
        addInitial(new Obstacle(new Point(8, 21)));
        addInitial(new Obstacle(new Point(8, 22)));
        addInitial(new Obstacle(new Point(9, 22)));
        addInitial(new Obstacle(new Point(9, 23)));
        addInitial(new Vein(new Point(10, 25), 8366));
        addInitial(new Vein(new Point(14, 22), 8248));
        addInitial(new Vein(new Point(21, 20), 9294));
        addInitial(new Vein(new Point(27, 6), 9456));
        addInitial(new Vein(new Point(28, 23), 13422));
        addInitial(new Vein(new Point(33, 11), 10278));
        addInitial(new Vein(new Point(33, 13), 10865));
        addInitial(new Vein(new Point(33, 3), 11101));
        addInitial(new Vein(new Point(34, 19), 11702));
        addInitial(new Vein(new Point(6, 11), 15026));
        addInitial(new Vein(new Point(7, 11), 9377));
        addInitial(new Vein(new Point(8, 11), 13146));
    }

    private static void addInitial(Entity entity) {
        assert !model.isOccupied(entity.position);
        model.addEntity(entity);
    }

    /**
     * Load a list of images for an entity.  text gives a series of
     * characters that serve as the animation for the text 
     * representation of the entity when in text mode.
     */
    private static List<Tile> loadImages(String fileBasename, String text) {
        int len = text.length();
        List<Tile> result = new ArrayList<Tile>(len);
        if (len == 1) {
            result.add(getImageTile(fileBasename + ".png", text.charAt(0)));
        } else {
            for (int i = 1; i <= len; i++) {
                String name = fileBasename + i + ".png";
                result.add(getImageTile(name, text.charAt(i - 1)));
            }
        }
        return result;
    }

    private void scheduleInitialActions(WorldModel model,
                                              EventSchedule eventSchedule)
    {
        for (Entity entity : model.entities)
        {
            if (entity instanceof EntityAction){
                EntityAction e = (EntityAction) entity;
                e.scheduleActions(eventSchedule, model);
            }
            
        }
    }

    /**
     * Entry point to run the virtual world simulation.
     */
    public void runSimulation() {
        //setup();
        Object target = new Object(); 
        model.paint(window.getInitialFrame());
        window.start();
        while (true) {
            frame = window.waitForNextFrame();
            if (frame == null) {
                break;
            }
            model.paint(frame);
            // System.out.println(window.getTimeSinceStart()); 
            eventSchedule.processEvents(window.getTimeSinceStart() * timeScale);
            Animatable grass = new GrassTile(100, model); 
            Action grassAction = new AnimationAction(grass, model, 1);
            eventSchedule.scheduleEvent(target, grassAction, 1); 

            window.showNextFrame();
        }
    }
}
