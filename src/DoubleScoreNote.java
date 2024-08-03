import bagel.*;

/** Special note that temporarily doubles all scores in the game when engaged.
 * @author lpedersson
 * @version 1.0
 */
public class DoubleScoreNote extends SpecialNote {
    /** Duration for which doubling effect will be implemented. */
    public final static int DOUBLE_DURATION = 480;
    private final static Image ENTITY_IMAGE = new Image("res/note2x.PNG");
    private final static String MESSAGE = "Double Score";
    private final static int SCORE_VAL = 0;


    /** Constructs new Double Score note.
     * @param entryFrame Frame at which note enters screen.
     * @param laneNum Numerical code for lane to which note belongs.
     * @param xPos X position at which note will be rendered on screen; should match that of lane.
     * @param parentLevel Reference to level to which note belongs.
     */
    public DoubleScoreNote(int entryFrame, int laneNum, int xPos, Level parentLevel) {
        super(entryFrame, laneNum, xPos, parentLevel);
        entityImage = ENTITY_IMAGE;
        message = MESSAGE;
        scoreVal = SCORE_VAL;
        score = new Score(message, scoreVal, DISTANCE_THRESHOLD);
    }

    /** Activates doubleScore method in parent level.
     */
    public void specialAction() {
        parentLevel.doubleScore();
    }
}
