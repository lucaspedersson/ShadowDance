/** Object containing information: score value (in points), message to be rendered when scored,
 * and distance threshold in order to attain score.
 * @author lpedersson
 * @version 1.0
 */
public class Score {
    /** Message to be printed when score is attained */
    private final String message;
    /** Value associated with this score */
    private final int score;
    /** Distance below or equal to which this score is attained */
    private final int distanceThreshold;


    /** Gets message to be rendered on screen when score is attained.
     * @return Message attribute.
     */
    public String getMessage() {
        return message;
    }

    /** Gets value of score.
     * @return Value of score.
     */
    public int getScore() {
        return score;
    }

    /** Gets distance within which note must be in order to attain score.
     * @return Distance threshold value.
     */
    public int getDistanceThreshold() {
        return distanceThreshold;
    }

    /** Creates new score object.
     * @param message Message to be rendered on screen when score is attained.
     * @param score Value of score.
     * @param distanceThreshold Distance within which score can be attained.
     */
    public Score(String message, int score, int distanceThreshold) {
        this.message = message;
        this.score = score;
        this.distanceThreshold = distanceThreshold;
    }
}