import bagel.Image;
import bagel.Input;

/** Note which only has scoring behaviour; i.e. Normal or Hold note.
 * @author lpedersson
 * @version 1.0
 */
public abstract class ScoreNote extends AbstractNote {
    protected final static Score[] SCORE_TEMPLATES = {
            new Score("PERFECT", 10, 15),
            new Score("GOOD", 5, 50),
            new Score("BAD", -1, 100),
            new Score("MISS", -5, 200)
    };

    protected Image[] noteImages;

    @Override
    public Score currNoteCheck(Input input) {
        Score score = null;
        /* Checks whether note has exited bottom of screen; returns Miss and discharges if so */
        if (yPos > (WINDOW_HEIGHT + entityImage.getHeight()/2)) {
            score = SCORE_TEMPLATES[3];
            dischargeNote();
        }
        return score;
    }

    @Override
    public Score scoreCurrNote() {
        Score score = null;
        /* Iterates through Score templates in increasing order of distance threshold until
         * one is found; if none found, returns null as note is not within scoring distance */
        for (Score scoreTemplate : SCORE_TEMPLATES) {
            if (currDistance <= scoreTemplate.getDistanceThreshold()) {
                score = scoreTemplate;
                break;
            }
        }
        return score;
    }

    /** Creates new ScoreNote object.
     * @param entryFrame Frame at which note enters screen.
     * @param laneNum Numerical code for lane to which note belongs.
     * @param xPos X position at which note will be rendered on screen; should match that of lane.
     * @param parentLevel Reference to level to which note belongs.
     */
    public ScoreNote(int entryFrame, int laneNum, int xPos, Level parentLevel) {
        super(entryFrame, laneNum, xPos, parentLevel);
    }
}
