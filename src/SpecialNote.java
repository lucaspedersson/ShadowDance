import bagel.Input;

/** Notes that implement a special ability when engaged.
 * @author lpedersson
 * @version 1.0
 */
public abstract class SpecialNote extends AbstractNote {
    protected static final int DISTANCE_THRESHOLD = 50;

    protected Score score;
    protected String message;
    protected int scoreVal;


    /** Constructs new Special note.
     * @param entryFrame Frame at which note enters screen.
     * @param laneNum Numerical code for lane to which note belongs.
     * @param xPos X position at which note will be rendered on screen; should match that of lane.
     * @param parentLevel Reference to level to which note belongs.
     */
    public SpecialNote(int entryFrame, int laneNum, int xPos, Level parentLevel) {
        super(entryFrame, laneNum, xPos, parentLevel);
    }

    @Override
    public Score scoreCurrNote() {
        if (currDistance <= score.getDistanceThreshold()) {
            return score;
        } else {
            return null;
        }
    }

    /** Activates special function belonging to note.
      */
    protected abstract void specialAction();

    /** Checks if lane key has been engaged; if so, checks whether a score should be generated. If so, engages
     * the note's special ability.
     * @param input This is the input object to which the note listens.
     * @return Score object describing score appropriate to current state.
     */
    @Override
    public Score currNoteCheck(Input input) {
        Score checkScore = null;

        /* Checks whether note has exited bottom of screen; discharges if so */
        if (yPos > (WINDOW_HEIGHT + entityImage.getHeight()/2)) {
            dischargeNote();
            return null;
        }
        if (input.wasPressed(laneKey)) {
            checkScore = scoreCurrNote();
            if (checkScore != null) {
                specialAction();
                dischargeNote();
            }
        }
        return checkScore;
    }
}
