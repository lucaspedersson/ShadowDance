import bagel.*;

/** Encompasses entities that belong in the game, such as notes or other moving objects.
 * @author lpedersson
 * @version 1.0
 */
public abstract class GameEntity implements Renderable {
    protected int xPos;
    protected int yPos;
    protected Image entityImage;
    protected Level parentLevel;

    /** Gets x-position of entity.
     * @return x-position.
     */
    public int getXPos() {
        return xPos;
    }

    /** Gets y-position of entity.
     * @return y-position.
     */
    public int getYPos() {
        return yPos;
    }

    /** Creates new GameEntity instance, with reference to level to which entity belongs.
     * @param parentLevel Reference to level to which entity belongs.
     */
    public GameEntity(Level parentLevel) {
        this.parentLevel = parentLevel;
    }

    /** Renders entity at position (xPos, yPos).
     */
    public void render() {
        entityImage.draw(xPos, yPos);
    }
}
