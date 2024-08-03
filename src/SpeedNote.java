/** Special note which changes the speed of notes in level when engaged.
 * @author lpedersson
 * @version 1.0
 */
public abstract class SpeedNote extends SpecialNote {
    protected final static int SCORE_VAL = 15;

    protected int speedChangeVal;


    /** Constructs new Speed note.
     * @param entryFrame Frame at which note enters screen.
     * @param laneNum Numerical code for lane to which note belongs.
     * @param xPos X position at which note will be rendered on screen; should match that of lane.
     * @param parentLevel Reference to level to which note belongs.
     */
    public SpeedNote(int entryFrame, int laneNum, int xPos, Level parentLevel) {
        super(entryFrame, laneNum, xPos, parentLevel);
        scoreVal = SCORE_VAL;
    }

    /** Engages speedChange method from parent level; changes speed by value speedChangeVal.
      */
    public void specialAction() {
        parentLevel.speedChange(speedChangeVal);
    }
}
