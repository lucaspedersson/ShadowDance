import bagel.*;

/**
 * SWEN20003 Project 2, Semester 2, 2023
 * @lpedersson
 */
public class ShadowDance extends AbstractGame  {
    /** Determines whether music plays during gameplay. */
    public static final boolean MUSIC_ON = true;
    /** Determines whether test files or actual world files are loaded into game. */
    public static final boolean TEST_MODE = false;

    private final static int WINDOW_WIDTH = Screen.WINDOW_WIDTH;
    private final static int WINDOW_HEIGHT = Screen.WINDOW_HEIGHT;
    /** Title of the game. */
    public final static String GAME_TITLE = "SHADOW DANCE";
    private final MainScreen mainScreen = new MainScreen();

    private int currLevel = 0;
    private GameScreen gameScreen;
    private EndScreen endScreen;

    /** Default constructor for ShadowDance game instance. */
    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            if (currLevel != 0) {
                gameScreen.stopGame();
                currLevel = 0;
            } else {
                Window.close();
            }
        }

        /* If currLevel == 0, loads main screen. If currLevel > 0, loads corresponding game level.
        * If currLevel < 0, loads win or loss screen based on result of previous game. */
        if (currLevel == 0) {
            currLevel = mainScreen.engage(input);
            if (currLevel != 0) {
                gameScreen = new GameScreen(currLevel);
            }
        } else if (currLevel > 0) {
            currLevel = gameScreen.engage(input);
            if (currLevel < 0) {
                endScreen = new EndScreen(currLevel);
            }
        } else {
            currLevel = endScreen.engage(input);
        }
    }
}
