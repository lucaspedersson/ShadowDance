import bagel.*;
import java.lang.Math;

/** Enemy entity that spawns on screen at constant intervals and steals notes when in sufficient proximity.
 * Can be destroyed with arrows.
 * @author lpedersson
 * @version 1.0
 */
public class Enemy extends GameEntity implements ActivatesAndHasDistance {
    private final static Image ENTITY_IMAGE = new Image("res/enemy.PNG");
    private final static int X_LOW_BOUND = 100;
    private final static int X_UP_BOUND = 900;
    private final static int Y_LOW_BOUND = 100;
    private final static int Y_UP_BOUND = 500;
    private final static int MOVEMENT_SPEED = 1;

    /** Frequency with which enemies should spawn, in frames. */
    public final static int SPAWN_FREQ = 600;
    /** Distance from enemy within which notes will be stolen. */
    public final static int NOTE_DIST_THRESHOLD = 104;

    private int velocity;
    private boolean active = true;


    /** Creates new Enemy instance with reference to parent level.
     * @param parentLevel Reference to level to which Enemy belongs.
     */
    public Enemy(Level parentLevel) {
        super(parentLevel);
        entityImage = ENTITY_IMAGE;
        /* Spawn enemy at random x-value between upper and lower X bounds. */
        xPos = X_LOW_BOUND + Math.round((float)Math.random() * (X_UP_BOUND - X_LOW_BOUND));
        /* Spawn enemy at random y-value between upper and lower Y bounds. */
        yPos = Y_LOW_BOUND + Math.round((float)Math.random() * (Y_UP_BOUND - Y_LOW_BOUND));
        /* Set speed to MOVEMENT_SPEED in left or right direction, selected randomly. */
        velocity = 2*MOVEMENT_SPEED*Math.round((float)Math.random()) - MOVEMENT_SPEED;
    }

    /** Gets whether enemy is currently active on screen.
     * @return True if enemy is currently active on screen; false otherwise.
     */
    public boolean getActive() {
        return active;
    }

    /** Sets activity value to false.
     */
    public void deactivate() {
        active = false;
    }

    /** Computes distance between enemy and point at specified (xPos,yPos).
     * @param xPos x-value of point to compute distance from.
     * @param yPos y-value of point to compute distance from.
     * @return Distance between enemy and point (xPos,yPos).
     */
    public double distanceFrom(double xPos, double yPos) {
        return Math.hypot(this.xPos - xPos, this.yPos - yPos);
    }

    /** Checks whether enemy has struck left or right boundaries; inverts direction if so. Then updates position
     * according to current speed.
     */
    public void update() {
        if (xPos >= X_UP_BOUND) {
            velocity = -MOVEMENT_SPEED;
        } else if (xPos <= X_LOW_BOUND) {
            velocity = MOVEMENT_SPEED;
        }
        xPos += velocity;
    }
}

