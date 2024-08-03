/** Encompasses objects that have position and a means of computing distance from other points.
 * @author lpedersson
 * @version 1.0
 */
public interface HasDistance extends HasPosition {
    /** Computes distance from object to specified point in 2D space.
     * @param xPos x position of point to compare.
     * @param yPos y position of point to compare.
     */
    double distanceFrom(double xPos, double yPos);
}
