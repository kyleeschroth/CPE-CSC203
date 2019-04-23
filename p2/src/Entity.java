import java.util.List;
import java.util.Random;
import edu.calpoly.spritely.Tile;
import java.util.List;

/**
 * An entity in our virtual world.  An entity occupies a square
 * on the grid.  It might move around, and interact with other
 * entities in the world.
 */
final class Entity
{
    public EntityKind kind;
    public Point position;
    public List<Tile> tiles;
    public int tileIndex;       // Index into tiles for animation
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

    public Entity(EntityKind kind, Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        this.kind = kind;
        this.position = position;
        this.tiles = tiles;
        this.tileIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public static final Random rand = new Random(); //needs work

    public void executeMinerFullActivity(WorldModel world, EventSchedule eventSchedule)
    {
        Entity fullTarget 
            = world.findNearest(this.position, EntityKind.BLACKSMITH);

        if (fullTarget != null  &&
            moveToFull(this, world, fullTarget, eventSchedule))
        {
            transformFull(world, eventSchedule);
        }
        else
        {
            eventSchedule.scheduleEvent(this,
                createActivityAction(world),
                this.actionPeriod);
        }
    }

    public void executeMinerNotFullActivity(WorldModel world, EventSchedule eventSchedule)
    {
        Entity notFullTarget = world.findNearest(this.position, EntityKind.ORE);

        if (notFullTarget == null ||
            !moveToNotFull(this, world, notFullTarget, eventSchedule) ||
            !transformNotFull(world, eventSchedule))
        {
            eventSchedule.scheduleEvent(this,
                createActivityAction(world),
                this.actionPeriod);
        }
    }

    public void executeOreActivity(WorldModel world, EventSchedule eventSchedule)
    {
        Point pos = this.position;    // store current position before removing

        world.removeEntity(this);
        eventSchedule.unscheduleAllEvents(this);

        Entity blob = createOreBlob(pos, this.actionPeriod / 4,
                                    50 + rand.nextInt(100));

        world.addEntity(blob);
        eventSchedule.scheduleActions(blob, world);
    }

    public void executeOreBlobActivity(WorldModel world, EventSchedule eventSchedule)
    {
        Entity blobTarget = world.findNearest(
            this.position, EntityKind.VEIN);
        long nextPeriod = this.actionPeriod;

        if (blobTarget != null)
        {
            Point tgtPos = blobTarget.position;

            if (moveToOreBlob(this, world, blobTarget, eventSchedule))
            {
                Entity quake = createQuake(tgtPos);

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                eventSchedule.scheduleActions(quake, world);
            }
        }

        eventSchedule.scheduleEvent(this,
            createActivityAction(world),
            nextPeriod);
    }

    public void executeQuakeActivity(WorldModel world, EventSchedule eventSchedule)
    {
        eventSchedule.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public void executeVeinActivity(WorldModel world, EventSchedule eventSchedule)
    {
        Point openPt = world.findOpenAround(this.position);

        if (openPt != null) {
            Entity ore = createOre(openPt, 20000 + rand.nextInt(10000));
            world.addEntity(ore);
            eventSchedule.scheduleActions(ore, world);
        }

        eventSchedule.scheduleEvent(this,
            createActivityAction(world),
            this.actionPeriod);
    }

    public int getAnimationPeriod()
    {
        switch (this.kind)
        {
        case MINER_FULL:
        case MINER_NOT_FULL:
        case ORE_BLOB:
        case QUAKE:
            return this.animationPeriod;
        default:
            throw new UnsupportedOperationException(
                String.format("getAnimationPeriod not supported for %s",
                this.kind));
        }
    }
    public void nextImage()
    {
        this.tileIndex = (this.tileIndex + 1) % this.tiles.size();
    }

    public Tile getCurrentTile()
    {
        return this.tiles.get(this.tileIndex);
    }
    
    private boolean transformNotFull(WorldModel world, EventSchedule eventSchedule)
    {
        if (this.resourceCount >= this.resourceLimit)
        {
            Entity miner = createMinerFull(this.resourceLimit,
                this.position, this.actionPeriod, this.animationPeriod);

            world.removeEntity(this);
            eventSchedule.unscheduleAllEvents(this);

            world.addEntity(miner);
            eventSchedule.scheduleActions(miner, world);

            return true;
        }

        return false;
    }

    private void transformFull(WorldModel world, EventSchedule eventSchedule)
    {
        Entity miner = createMinerNotFull(this.resourceLimit,
            this.position, this.actionPeriod, this.animationPeriod);

        world.removeEntity(this);
        eventSchedule.unscheduleAllEvents(this);

        world.addEntity(miner);
        eventSchedule.scheduleActions(miner, world);
    }

    private boolean moveToNotFull(Entity miner, WorldModel world, Entity target,  EventSchedule eventSchedule)
    {
        if (Point.adjacent(miner.position, target.position))
        {
            miner.resourceCount += 1;
            world.removeEntity(target);
            eventSchedule.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = miner.nextPositionMiner(world, target.position);

            if (!miner.position.equals(nextPos))
            {
                world.moveEntity(miner, nextPos);
            }
            return false;
        }
    }

    private boolean moveToFull(Entity miner, WorldModel world, Entity target,  EventSchedule eventSchedule)
    {
        if (Point.adjacent(miner.position, target.position))
        {
            return true;
        }
        else
        {
            Point nextPos = miner.nextPositionMiner(world, target.position);

            if (!miner.position.equals(nextPos))
            {
                world.moveEntity(miner, nextPos);
            }
            return false;
        }
    }

    private boolean moveToOreBlob(Entity blob, WorldModel world, Entity target,  EventSchedule eventSchedule)
    {
        if (Point.adjacent(blob.position, target.position))
        {
            world.removeEntity(target);
            eventSchedule.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = blob.nextPositionOreBlob(world, target.position);

            if (!blob.position.equals(nextPos))
            {
                Entity occupant = world.getOccupant(nextPos);
                if (occupant != null)
                {
                    eventSchedule.unscheduleAllEvents(occupant);
                }

                world.moveEntity(blob, nextPos);
            }
            return false;
        }
    }

    public Action createActivityAction(WorldModel world)
    {
        return new Action(ActionKind.ACTIVITY, this, world, 0);
    }

    public Action createAnimationAction(int repeatCount)
    {
        return new Action(ActionKind.ANIMATION, this, null, repeatCount);
    }

    private Entity createOreBlob(Point position, int actionPeriod, int animationPeriod)
    {
        return new Entity(EntityKind.ORE_BLOB, position, 
                          VirtualWorld.blobTiles,
                          0, 0, actionPeriod, animationPeriod);
    }

    private Entity createQuake(Point position)
    {
        return new Entity(EntityKind.QUAKE, position, 
                          VirtualWorld.quakeTiles, 0, 0, 1100, 100);
    }

    private Entity createOre(Point position, int actionPeriod)
    {
        return new Entity(EntityKind.ORE, position, 
                          VirtualWorld.oreTiles, 0, 0, actionPeriod, 0);
    }

    private Entity createMinerFull(int resourceLimit, Point position, int actionPeriod, int animationPeriod)
    {
        return new Entity(EntityKind.MINER_FULL, position, 
                          VirtualWorld.minerTiles,
                          resourceLimit, resourceLimit, actionPeriod, 
                          animationPeriod);
    }

    public static Entity createMinerNotFull(int resourceLimit, Point position, int actionPeriod, int animationPeriod)
    {
        return new Entity(EntityKind.MINER_NOT_FULL, position, 
                          VirtualWorld.minerTiles,
                          resourceLimit, 0, actionPeriod, animationPeriod);
    }

    private Point nextPositionMiner(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.position.getX() + horiz,
            this.position.getY());

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(),
                this.position.getY() + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    private Point nextPositionOreBlob(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.position.getX() + horiz,
            this.position.getY());

        Entity occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
            (occupant != null && !(occupant.kind == EntityKind.ORE)))
        {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(), 
                               this.position.getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                (occupant != null && !(occupant.kind == EntityKind.ORE)))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public static Entity createBlacksmith(Point position)
    {
        return new Entity(EntityKind.BLACKSMITH, position, 
                          VirtualWorld.blacksmithTiles, 0, 0, 0, 0);
    }

    public static Entity createObstacle(Point position)
    {
        return new Entity(EntityKind.OBSTACLE, position, 
                          VirtualWorld.obstacleTiles, 0, 0, 0, 0);
    }

    public static Entity createVein(Point position, int actionPeriod)
    {
        return new Entity(EntityKind.VEIN, position, 
                          VirtualWorld.veinTiles, 0, 0, actionPeriod, 0);
    }
}
