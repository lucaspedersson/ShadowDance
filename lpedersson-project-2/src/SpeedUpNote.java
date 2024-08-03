import bagel.Image;

/** Special note which increases speed of notes in level when engaged.
 * @author lpedersson
 * @version 1.0
 */
public class SpeedUpNote extends SpeedNote {
    private final static Image ENTITY_IMAGE = new Image("res/noteSpeedUp.png");
    private final static String MESSAGE = "Speed Up";
    private final static int SPEED_CHANGE_VAL = 1;

    /** Constructs new Speed Up note.
     * @param entryFrame Frame at which note enters screen.
     * @param laneNum Numerical code for lane to which note belongs.
     * @param xPos X position at which note will be rendered on screen; should match that of lane.
     * @param parentLevel Reference to level to which note belongs.
     */
    public SpeedUpNote(int entryFrame, int laneNum, int xPos, Level parentLevel) {
        super(entryFrame, laneNum, xPos, parentLevel);
        entityImage = ENTITY_IMAGE;
        message = MESSAGE;
        speedChangeVal = SPEED_CHANGE_VAL;
        score = new Score(message, scoreVal, DISTANCE_THRESHOLD);
    }
}
