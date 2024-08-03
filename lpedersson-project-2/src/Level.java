import bagel.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ArrayList;

/** Level in game. Contains all information and operations relevant to gameplay.
 * @author lpedersson
 * @version 1.0
 */
public class Level implements Renderable {
    /** When LANE_KEYWORD is recognised, input will be read as lane metadata */
    protected static final String LANE_KEYWORD = "Lane";
    protected static final String FONT_FILE = Screen.FONT_FILE;
    protected static final Font SCORE_FONT = new Font(FONT_FILE, 30);
    protected static final Font MESSAGE_FONT = new Font(FONT_FILE, 40);
    protected static final int INITIAL_SPEED = 2;
    protected static final String[] WORLD_FILES = {"res/level1.csv", "res/level2.csv", "res/level3.csv"};
    protected static final String[] TEST_FILES = {"res/test1.csv", "res/test2.csv", "res/test3.csv"};
    protected static final int[] TARGET_SCORES = {150, 400, 350};
    protected static final String[] TRACK_FILES = {"res/track1.wav", "res/track2.wav", "res/track3.wav"};
    protected static final int RENDER_MESSAGE_DURATION = 30;
    /** X coordinate of bottom left corner at which score announcement will be rendered */
    protected final static int SCORE_X_POS = 35;
    /** Y coordinate of bottom left corner at which score announcement will be rendered */
    protected static final int SCORE_Y_POS = 35;

    protected String worldFile;
    protected boolean isComplete = false;
    protected int levelNum;
    protected int speed = INITIAL_SPEED;
    protected int frameNum = 0;

    protected final Lane[] lanes = new Lane[5];
    protected int score = 0;
    protected int scoreMultiplier = 1;
    protected boolean isWon;
    protected final int targetScore;
    protected ArrayList<Integer> doubleScoreUntil = new ArrayList<>();

    protected String currMessage;
    protected int renderMessageUntil;

    protected Track music;
    protected boolean musicStarted = false;
    protected boolean musicPaused = false;


    /** Creates new Level instance corresponding to provided level number.
     * @param levelNum Numerical code denoting which level to load.
     */
    public Level(int levelNum) {
        this.levelNum = levelNum;
        this.music = new Track(TRACK_FILES[levelNum - 1]);
        this.targetScore = TARGET_SCORES[levelNum - 1];
        /* If in Test Mode, load test files instead of regular world files. */
        if (ShadowDance.TEST_MODE) {
            this.worldFile = TEST_FILES[levelNum - 1];
        } else {
            this.worldFile = WORLD_FILES[levelNum - 1];
        }
        readCSV(worldFile);
    }

    /** Gets whether level is completed.
     * @return True if level has been completed; false otherwise
     */
    public boolean getIsComplete() {
        return isComplete;
    }

    /** Gets whether level is won.
     * @return True if level has been won; false otherwise.
     */
    public boolean getIsWon() {
        return isWon;
    }

    /** Read CSV world file for current level; load data into level.
     * @param worldFile String giving file path for CSV world file to load.
     */
    private void readCSV(String worldFile) {
        /* Parse lane types from CSV data */
        Dictionary<String, Integer> laneTypeDict = new Hashtable<>();
        laneTypeDict.put("Left", Lane.LEFT_LANE_INDEX);
        laneTypeDict.put("Up", Lane.UP_LANE_INDEX);
        laneTypeDict.put("Down", Lane.DOWN_LANE_INDEX);
        laneTypeDict.put("Right", Lane.RIGHT_LANE_INDEX);
        laneTypeDict.put("Special", Lane.SPECIAL_LANE_INDEX);

        /* Parse note types from CSV data */
        Dictionary<String, Integer> noteTypeDict = new Hashtable<>();
        noteTypeDict.put("Normal", AbstractNote.NORMAL_NOTE_CODE);
        noteTypeDict.put("Hold", AbstractNote.HOLD_NOTE_CODE);
        noteTypeDict.put("SpeedUp", AbstractNote.SPEED_UP_NOTE_CODE);
        noteTypeDict.put("SlowDown", AbstractNote.SLOW_DOWN_NOTE_CODE);
        noteTypeDict.put("DoubleScore", AbstractNote.DOUBLE_SCORE_NOTE_CODE);
        noteTypeDict.put("Bomb", AbstractNote.BOMB_NOTE_CODE);

        try (BufferedReader br = new BufferedReader(new FileReader(worldFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");
                /* If the first cell in the row is LANE_KEYWORD, read lane metadata */
                if (cells[0].equals(LANE_KEYWORD)) {
                    Integer laneNum = laneTypeDict.get(cells[1]);
                    int xPos = Integer.parseInt(cells[2]);
                    lanes[laneNum] = new Lane(laneNum, xPos, this);
                } else {
                    /* Decompose note data and add to prescribed lane */
                    Integer laneNum = laneTypeDict.get(cells[0]);
                    int noteType = noteTypeDict.get(cells[1]);
                    int entryFrame = Integer.parseInt(cells[2]);
                    lanes[laneNum].addNote(entryFrame, noteType);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Ensure notes in each lane are sorted by entry frame. */
        for (Lane lane : lanes) {
            if (lane != null) {
                lane.sortNotes();
            }
        }
    }

    /** Updates isLevelComplete attribute to true if all lanes are complete.
      */
    public void checkCompletion() {
        boolean allComplete = true;
        for (Lane lane : lanes) {
            if (lane != null) {
                allComplete = allComplete && (lane.getComplete());
            }
        }
        isComplete = allComplete;
        if (isComplete) {
            isWon = (score >= targetScore);
        }
    }

    /** Increment frame number and update each lane according to frame number and user input.
     * @param input User input.
     */
    public void update(Input input) {
        Score bufferScore;

        /* Start music at frame 0 if music enabled */
        if (ShadowDance.MUSIC_ON && frameNum == 0) {
            musicStart();
        }

        /* Check if current frame number matches any entries in doubleScoreUntil. If so,
        * halve current score multiplier and remove entry. */
        for (Integer num : doubleScoreUntil) {
            if (frameNum == num) {
                scoreMultiplier /= 2;
                doubleScoreUntil.remove(num);
                break;
            }
        }

        for (Lane lane : lanes) {
            /* For each lane in lanes, update lane and register any score updates returned */
            if ((lane != null) && ((bufferScore = lane.update(input, frameNum, speed)) != null)) {
                score += (scoreMultiplier * bufferScore.getScore());
                addNewMessage(bufferScore.getMessage());
            }
        }
        checkCompletion();
        frameNum++;
    }

    /** Render score counter, each lane, active notes in each lane, and any messages.
      */
    public void render() {
        SCORE_FONT.drawString("SCORE " + score, SCORE_X_POS, SCORE_Y_POS);

        for (Lane lane : lanes) {
            if (lane != null) {
                lane.render();
            }
        }

        /* Check if most recent score announcement needs to be rendered; render if so */
        if ((currMessage != null) && (frameNum <= renderMessageUntil)) {
            MESSAGE_FONT.drawString(currMessage,
                    (double) Screen.WINDOW_WIDTH /2 - MESSAGE_FONT.getWidth(currMessage)/2,
                    (double) Screen.WINDOW_HEIGHT /2);
        }
    }

    /** Updates current message, and updates frame number until which current message should be rendered.
     * @param message Message to be rendered.
     */
    public void addNewMessage(String message) {
        currMessage = message;
        renderMessageUntil = frameNum + RENDER_MESSAGE_DURATION;
    }

    /** Change speed of level by value provided.
     * @param changeVal Value by which to change speed.
     */
    public void speedChange(int changeVal) {
        speed += changeVal;
    }

    /** Doubles current score multiplier, and adds to doubleScoreUntil register which frame to double until.
      */
    public void doubleScore() {
        doubleScoreUntil.add(frameNum + DoubleScoreNote.DOUBLE_DURATION);
        scoreMultiplier *= 2;
    }

    /** Clears all active notes in specified lane.
     * @param laneNum Lane in which to clear all active notes.
     */
    public void laneClear(int laneNum) {
        if (lanes[laneNum] != null) {
            lanes[laneNum].clearNotes();
        }
    }

    /** Toggles pause of current music track.
      */
    @SuppressWarnings("CallToThreadRun")
    public void toggleMusic() {
        if (musicStarted) {
            if (musicPaused) {
                music.run();
                musicPaused = false;
            } else {
                music.pause();
                musicPaused = true;
            }
        }
    }

    /** Starts music track.
      */
    public void musicStart() {
        music.start();
        musicStarted = true;
    }
}
