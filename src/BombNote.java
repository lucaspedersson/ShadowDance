import bagel.*;

/** Bomb note; clears all notes from lane when engaged.
 * @author lpedersson
 * @version 1.0
 */
public class BombNote extends SpecialNote {
    private final static Image ENTITY_IMAGE = new Image("res/noteBomb.PNG");
    private final static String MESSAGE = "Lane Clear";
    private final static int SCORE_VAL = 0;


    /** Constructs new Bomb note.
     * @param entryFrame Frame at which note enters screen.
     * @param laneNum Numerical code for lane to which note belongs.
     * @param xPos X position at which note will be rendered on screen; should match that of lane.
     * @param parentLevel Reference to level to which note belongs.
     */
    public BombNote(int entryFrame, int laneNum, int xPos, Level parentLevel) {
        super(entryFrame, laneNum, xPos, parentLevel);
        entityImage = ENTITY_IMAGE;
        message = MESSAGE;
        scoreVal = SCORE_VAL;
        score = new Score(message, scoreVal, DISTANCE_THRESHOLD);
    }

    /** Engages laneClear method in parent level.
     */
    public void specialAction() {
        parentLevel.laneClear(laneNum);
    }
}
