import bagel.*;
import java.lang.Math;

/** Projectile object that is fired from guardian. Destroys enemies when in sufficient proximity.
 * @author lpedersson
 * @version 1.0
 */
public class Projectile extends GameEntity implements ActivatesAndHasPosition {
    private final static Image ENTITY_IMAGE = new Image("res/arrow.PNG");
    private final static int SPEED = 6;
    /** Distance threshold within which a present enemy will be destroyed. */
    public final static int ENEMY_THRESHOLD = 62;

    private final DrawOptions drawOptions = new DrawOptions();
    private final double xVelocity;
    private final double yVelocity;

    private boolean active = true;


    /** Creates new projectile object with reference to parent level and with reference to target enemy.
     * @param target Enemy in the direction of which a projectile will be fired.
     * @param parentLevel Reference to level to which projectile belongs.
     */
    public Projectile(Enemy target, Level parentLevel) {
        super(parentLevel);
        xPos = Guardian.X_POS;
        yPos = Guardian.Y_POS;
        double xDisplacement = target.getXPos() - xPos;
        double yDisplacement = target.getYPos() - yPos;
        double rotation = Math.atan2(yDisplacement, xDisplacement);
        drawOptions.setRotation(rotation);
        xVelocity = SPEED*Math.cos(rotation);
        yVelocity = SPEED*Math.sin(rotation);
        entityImage = ENTITY_IMAGE;
    }

    /** Gets whether projectile is currently active on screen.
     * @return True if projectile is currently active on screen; false otherwise.
     */
    public boolean getActive() {
        return active;
    }

    /** Sets active value to false.
     */
    public void deactivate() {
        active = false;
    }

    /** Renders entity at position (xPos,yPos), with orientation provided by rotation value.
     */
    @Override
    public void render() {
        entityImage.draw(xPos, yPos, drawOptions);
    }

    /** Updates current position according to projectile velocity. Checks if projectile has
     * left bounds of game window; deactivates projectile if so.
     */
    public void update() {
        xPos += xVelocity;
        yPos += yVelocity;
        if (!inBounds()) {
            deactivate();
        }
    }

    /** Checks whether projectile is currently in bounds of game window.
     * @return True if projectile is currently in bounds of game window; false otherwise.
     */
    private boolean inBounds() {
        if ((xPos < -entityImage.getWidth()) || (xPos > (Screen.WINDOW_WIDTH + entityImage.getWidth()))) {
            return false;
        }
        if ((yPos < -entityImage.getHeight()) || (yPos > (Screen.WINDOW_HEIGHT + entityImage.getHeight()))) {
            return false;
        }
        return true;
    }
}
