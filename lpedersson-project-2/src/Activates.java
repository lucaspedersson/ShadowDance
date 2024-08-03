/** Encompasses objects with the ability to activate or deactivate, and update.
 * @author lpedersson
 * @version 1.0
 */
public interface Activates {
    boolean getActive();
    void deactivate();
    void update();
}
