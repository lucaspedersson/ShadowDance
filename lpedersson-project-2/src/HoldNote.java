import bagel.Image;
import bagel.Input;

/** Hold length note.
 * @author lpedersson
 * @version 1.0
 */
public class HoldNote extends ScoreNote {
    /** Distance from centre of image to either top or bottom arrows. */
    private static final int HOLD_CENTRE_DISPLACEMENT = 82;
    /** Y-value of note at spawn; different from other notes. */
    private static final int INITIAL_HOLD_Y_POS = 24;
    private static final Image[] HOLD_NOTE_IMAGES = {
            new Image("res/holdNoteLeft.PNG"),
            new Image("res/holdNoteUp.PNG"),
            new Image("res/holdNoteDown.PNG"),
            new Image("res/holdNoteRight.PNG")
    };

    /** Marks whether key has been held down for hold note */
    private boolean held = false;

    /** Constructs new Hold note.
     * @param entryFrame Frame at which note enters screen.
     * @param laneNum Numerical code for lane to which note belongs.
     * @param xPos X position at which note will be rendered on screen; should match that of lane.
     * @param parentLevel Reference to level to which note belongs.
     */
    public HoldNote(int entryFrame, int laneNum, int xPos, Level parentLevel) {
        super(entryFrame, laneNum, xPos, parentLevel);
        noteImages = HOLD_NOTE_IMAGES;
        entityImage = HOLD_NOTE_IMAGES[laneNum];
        yPos = INITIAL_HOLD_Y_POS;
        yHitPos = yPos + HOLD_CENTRE_DISPLACEMENT;
    }

    @Override
    public Score currNoteCheck(Input input) {
        Score score = super.currNoteCheck(input);

        if (!held && input.wasPressed(laneKey)) {
            held = true;
            score = scoreCurrNote();
            /* Shift scoring position to upper end */
            yHitPos -= 2*HOLD_CENTRE_DISPLACEMENT;
        } else if (held && input.wasReleased(laneKey)) {
            score = scoreCurrNote();
            dischargeNote();
        }
        return score;
    }
}
