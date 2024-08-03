import bagel.*;

/** Guardian entity that sits stationary on screen, and from which projectiles are fired.
 * @author lpedersson
 * @version 1.0
 */
public class Guardian extends GameEntity {
    private final static Image ENTITY_IMAGE = new Image("res/guardian.PNG");

    /** X-position of guardian. */
    public final static int X_POS = 800;
    /** Y-position of guardian. */
    public final static int Y_POS = 600;


    /** Creates a new Guardian entity with reference to parent level.
     * @param parentLevel Reference to level to which guardian belongs.
     */
    public Guardian(Level parentLevel) {
        super(parentLevel);
        entityImage = ENTITY_IMAGE;
        xPos = X_POS;
        yPos = Y_POS;
    }
}
