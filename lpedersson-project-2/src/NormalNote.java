import bagel.Image;
import bagel.Input;

/** Normal length note.
 * @author lpedersson.
 * @version 1.0
 */
public class NormalNote extends ScoreNote {
    private static final Image[] NORMAL_NOTE_IMAGES = new Image[] {
            new Image("res/noteLeft.png"),
            new Image("res/noteUp.png"),
            new Image("res/noteDown.png"),
            new Image("res/noteRight.png")
    };

    /** Constructs new Normal note.
     * @param entryFrame Frame at which note enters screen.
     * @param laneNum Numerical code for lane to which note belongs.
     * @param xPos X position at which note will be rendered on screen; should match that of lane.
     * @param parentLevel Reference to level to which note belongs.
     */
    public NormalNote(int entryFrame, int laneNum, int xPos, Level parentLevel) {
        super(entryFrame, laneNum, xPos, parentLevel);
        noteImages = NORMAL_NOTE_IMAGES;
        entityImage = NORMAL_NOTE_IMAGES[laneNum];
    }

    @Override
    public Score currNoteCheck(Input input) {
        Score score = super.currNoteCheck(input);

        if (input.wasPressed(laneKey)) {
            score = scoreCurrNote();
        }
        /* If scoring behaviour detected, immediately discharge note */
        if (score != null) {
            dischargeNote();
        }
        return score;
    }
}
