import bagel.*;
import java.util.Collections;
import java.util.ArrayList;

/** Represents a lane belonging to a level. Contains notes, and facilitates interaction with those notes
 * during gameplay.
 * @author lpedersson
 * @version 1.0
 */
public class Lane implements Renderable {
    /** Numerical code denoting Left lane. */
    public static final int LEFT_LANE_INDEX = 0;
    /** Numerical code denoting Up lane. */
    public static final int UP_LANE_INDEX = 1;
    /** Numerical code denoting Down lane. */
    public static final int DOWN_LANE_INDEX = 2;
    /** Numerical code denoting Right lane. */
    public static final int RIGHT_LANE_INDEX = 3;
    /** Numerical code denoting Special lane. */
    public static final int SPECIAL_LANE_INDEX = 4;

    private static final int Y_POS = 384;
    private static final Image[] LANE_BACKGROUNDS = {
            new Image("res/laneLeft.png"),
            new Image("res/laneUp.PNG"),
            new Image("res/laneDown.PNG"),
            new Image("res/laneRight.png"),
            new Image("res/laneSpecial.PNG")
    };

    private final Level parentLevel;
    private final Image laneBackground;
    private final ArrayList<AbstractNote> notes = new ArrayList<>();
    private final int laneNum;
    private final int xPos;

    private boolean complete = false;
    private int nextNote = 0;
    private int currNote = 0;


    /** Constructs a new Lane instance, with specified x-position, lane number, and reference to parent level.
     * @param laneNum Denotes which lane type to construct.
     * @param xPos Denotes x-position at which to render lane.
     * @param parentLevel Reference to level to which lane belongs.
     */
    public Lane(int laneNum, int xPos, Level parentLevel) {
        this.laneNum = laneNum;
        this.xPos = xPos;
        this.laneBackground = LANE_BACKGROUNDS[laneNum];
        this.parentLevel = parentLevel;
    }

    /** Gets whether lane is complete; lane is complete when all notes have been discharged.
     * @return True if all notes in lane have been discharged; false otherwise.
     */
    public boolean getComplete() {
        return complete;
    }

    /** Returns reference to notes ArrayList.
     * @return Reference to notes ArrayList.
     */
    public ArrayList<? extends ActivatesAndHasPosition> getNotes() {
        return notes;
    }

    /** Renders lane background, and then each currently active note.
      */
    public void render() {
        laneBackground.draw(xPos, Y_POS);

        for (int i=currNote; i<nextNote; i++) {
            if (notes.get(i).getActive()) {
                notes.get(i).render();
            }
        }
    }

    /** Updates lane according to user input, current frame number, and level speed.
     * @param input User input.
     * @param frameNum Frame number at current time.
     * @param speed Current game speed; i.e. number of pixels by which notes should move per frame.
     * @return Score object informing whether and what scoring behaviour is generated at current frame.
     */
    public Score update(Input input, int frameNum, int speed) {
        Score score = null;

        if (getComplete()) {
            return null;
        }

        if ((nextNote < notes.size()) && (notes.get(nextNote) != null)) {
            /* If next note exists and current frame is entry frame for next note, activate next note
            and increment nextNote. */
            if (frameNum == notes.get(nextNote).entryFrame) {
                notes.get(nextNote).activate();
                nextNote++;
            }
        }

        /* While current note exists, update all notes currently active and check
         current note for response to input. */
        if (notes.get(currNote) != null) {
            for (int i=currNote; i<nextNote; i++) {
                notes.get(i).update(speed);
            }
            /* Check current note for event of user input or note exiting screen; store returned score. */
            if ((nextNote != 0) && (notes.get(currNote).getActive())) {
                score = notes.get(currNote).currNoteCheck(input);
            }

            /* If current note is marked as being discharged, increase currNote to next
            lowest active or pending note */
            if (notes.get(currNote).getDischarged()) {
                currNote = nextCurrNote();
            }
        }

        /* If currNote matches size of array, array has been fully discharged.
        Update complete attribute accordingly. */
        if (currNote == notes.size()) {
            complete = true;
        }
        return score;
    }

    /** Identifies next lowest active or pending note and returns its index in notes.
     * @return Index of next lowest active or pending note.
     */
    private int nextCurrNote() {
        int candidateNote = nextNote;

        for (int i=currNote + 1; i<nextNote; i++) {
            if (notes.get(i).getActive() || notes.get(i).isPending()) {
                candidateNote = i;
                break;
            }
        }
        return candidateNote;
    }

    /** Adds note to notes at specified entry frame, with specified note type.
     * @param entryFrame Frame number at which note will enter.
     * @param noteType Numerical code indicating what type of note to add.
     */
    public void addNote(int entryFrame, int noteType) {
        switch(noteType) {
            case AbstractNote.NORMAL_NOTE_CODE:
                notes.add(new NormalNote(entryFrame, laneNum, xPos, parentLevel));
                break;
            case AbstractNote.HOLD_NOTE_CODE:
                notes.add(new HoldNote(entryFrame, laneNum, xPos, parentLevel));
                break;
            case AbstractNote.SPEED_UP_NOTE_CODE:
                notes.add(new SpeedUpNote(entryFrame, laneNum, xPos, parentLevel));
                break;
            case AbstractNote.SLOW_DOWN_NOTE_CODE:
                notes.add(new SlowDownNote(entryFrame, laneNum, xPos, parentLevel));
                break;
            case AbstractNote.DOUBLE_SCORE_NOTE_CODE:
                notes.add(new DoubleScoreNote(entryFrame, laneNum, xPos, parentLevel));
                break;
            case AbstractNote.BOMB_NOTE_CODE:
                notes.add(new BombNote(entryFrame, laneNum, xPos, parentLevel));
                break;
        }
    }

    /** Sorts notes ArrayList.
      */
    public void sortNotes() {
        Collections.sort(notes);
    }

    /** Discharges all currently active notes on screen.
      */
    public void clearNotes() {
        for (int i=currNote; i<nextNote; i++) {
            notes.get(i).dischargeNote();
        }
        currNote = nextCurrNote();
    }
}
