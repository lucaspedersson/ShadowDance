import bagel.*;
import java.util.Dictionary;
import java.util.Hashtable;

/** Describes any object that falls down a lane, in the manner of a note.
 * @author lpedersson
 * @version 1.0
 */
abstract class AbstractNote extends GameEntity implements Comparable<AbstractNote>, ActivatesAndHasPosition {
    /** Numerical code describing Normal note */
    public final static int NORMAL_NOTE_CODE = 0;
    /** Numerical code describing Hold note */
    public final static int HOLD_NOTE_CODE = 1;
    /** Numerical code describing Speed Up note */
    public final static int SPEED_UP_NOTE_CODE = 2;
    /** Numerical code describing Slow Down note */
    public final static int SLOW_DOWN_NOTE_CODE = 3;
    /** Numerical code describing Double Score note */
    public final static int DOUBLE_SCORE_NOTE_CODE = 4;
    /** Numerical code describing Bomb note */
    public final static int BOMB_NOTE_CODE = 5;

    protected final static int WINDOW_HEIGHT = Screen.WINDOW_HEIGHT;
    /** Y position of stationary note */
    protected final static int STATIONARY_Y_POS = 657;

    /** Y-value from which distance to stationary note will be calculated. Typically centre of the note. */
    protected int yHitPos;
    /** Frame number at which note spawns on screen. */
    protected int entryFrame;
    /** Marks whether note is currently being actively rendered on screen. */
    protected boolean active = false;
    /** Marks whether note has ceased to be rendered on screen.  */
    protected boolean discharged = false;
    /** Current distance from yHitPos to stationary note position in current lane. */
    protected int currDistance;
    /** Key from which note responds to input. */
    protected Keys laneKey;
    /** Numerical code for lane to which note belongs. */
    protected int laneNum;


    /** Constructs new note.
     * @param entryFrame Frame at which note enters screen.
     * @param laneNum Numerical code for lane to which note belongs.
     * @param xPos X position at which note will be rendered on screen; should match that of lane.
     * @param parentLevel Reference to level to which note belongs.
     */
    public AbstractNote(int entryFrame, int laneNum, int xPos, Level parentLevel) {
        super(parentLevel);
        /* Maps lane type to corresponding key */
        Dictionary<Integer, Keys> laneKeyDict = new Hashtable<>();
        laneKeyDict.put(0, Keys.LEFT);
        laneKeyDict.put(1, Keys.UP);
        laneKeyDict.put(2, Keys.DOWN);
        laneKeyDict.put(3, Keys.RIGHT);
        laneKeyDict.put(4, Keys.SPACE);
        this.entryFrame = entryFrame;
        this.laneNum = laneNum;
        this.xPos = xPos;
        this.laneKey = laneKeyDict.get(laneNum);
        yPos = 100;
        yHitPos = yPos;
        currDistance = getDistance();
    }

    /** Gets whether note is currently active on screen.
     * @return True if note active; false otherwise.
     */
    public boolean getActive() {
        return active;
    }

    /** Sets active to false; marks note as no longer active on screen.
     */
    public void deactivate() {
        dischargeNote();
    }

    /** Gets whether note has been discharged.
     * @return Returns true if note has been discharged, false otherwise.
     */
    public boolean getDischarged() {
        return discharged;
    }

    /** Checks whether note has yet to be rendered on screen.
     * @return True if note is neither active nor discharged. False otherwise.
     */
    public boolean isPending() {
        return (!getActive() && !getDischarged());
    }

    /** Sets note to active.
     */
    public void activate() {
        active = true;
    }

    /** Gets frame at which note is rendered onto screen.
     * @return entryFrame number.
     */
    public int getEntryFrame() {
        return entryFrame;
    }

    /** Renders note at current position (xPos, yPos).
     */
    @Override
    public void render() {
        super.render();
    }

    /** Updates note position, and distance between yHitPos and new note position.
     * @param speed Number of pixels by which note should move.
     */
    public void update(int speed) {
        yPos += speed;
        yHitPos += speed;
        update();
    }

    /** Update distance between yHitPos and stationary note position, without updating note position.
     */
    public void update() {
        currDistance = getDistance();
    }

    /** Returns a positive integer if this note has later entry than parameter note, negative integer if parameter note
     * has later entry, and 0 if they have equal entry frame.
     * @param note Note against which entry frame is being compared.
     * @return Integer describing whether parameter note spawns on screen before, after, or at the same time as this note.
     */
    public int compareTo(AbstractNote note) {
        return this.getEntryFrame() - note.getEntryFrame();
    }

    /** Returns distance in pixels between hit position of current note and centre of stationary note in its lane.
     * @return Integer distance between yHitPos and stationary y position for lane.
     */
    public int getDistance() {
        return Math.abs(STATIONARY_Y_POS - yHitPos);
    }

    /** Mark note as discharged and inactive
     */
    public void dischargeNote() {
        active = false;
        discharged = true;
    }

    /** Checks if lane key has been engaged; if so, checks whether a score should be generated.
     * @param input This is the input object to which the note listens.
     * @return Score object describing score appropriate to current state.
     */
    public abstract Score currNoteCheck(Input input);

    /** Computes score for note given current distance from the stationary note.
     * @return Score object describing score appropriate to the current state of the note.
     */
    public abstract Score scoreCurrNote();
}
