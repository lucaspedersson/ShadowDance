import bagel.*;

/** Special note which reduces speed of notes in level when engaged.
 * @author lpedersson
 * @version 1.0
 */
public class SlowDownNote extends SpeedNote {
    private final static Image ENTITY_IMAGE = new Image("res/noteSlowDown.PNG");
    private final static String MESSAGE = "Slow Down";
    private final static int SPEED_CHANGE_VAL = -1;

    /** Constructs new Slow Down note.
     * @param entryFrame Frame at which note enters screen.
     * @param laneNum Numerical code for lane to which note belongs.
     * @param xPos X position at which note will be rendered on screen; should match that of lane.
     * @param parentLevel Reference to level to which note belongs.
     */
    public SlowDownNote(int entryFrame, int laneNum, int xPos, Level parentLevel) {
        super(entryFrame, laneNum, xPos, parentLevel);
        entityImage = ENTITY_IMAGE;
        message = MESSAGE;
        speedChangeVal = SPEED_CHANGE_VAL;
        score = new Score(message, scoreVal, DISTANCE_THRESHOLD);
    }
}
