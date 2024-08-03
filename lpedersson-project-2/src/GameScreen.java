import bagel.*;

/** Screen rendered while actively playing a level.
 * @author lpedersson
 * @version 1.0
 */
public class GameScreen extends Screen {
    private final int levelNum;
    private final Level level;

    private boolean paused = false;

    /** Creates new Game Screen object with level corresponding to input levelNum.
     * @param levelNum Level number to load.
     */
    public GameScreen(int levelNum) {
        this.levelNum = levelNum;
        if (levelNum == 3) {
            level = new LevelThree(levelNum);
        } else {
            level = new Level(levelNum);
        }
    }

    /** Returns reference to level active in current game.
     * @return Level active in current game.
     */
    public Level getLevel() {
        return level;
    }

    /** Toggles pause of game.
      */
    public void togglePause() {
        level.toggleMusic();
        paused = !paused;
    }

    /** Stops running current game.
      */
    public void stopGame() {
        if (!paused) {
            togglePause();
        }
    }

    @Override
    public void render() {
        super.render();
        level.render();
    }

    /** Lets user interact with game screen; returns levelNum if game not yet complete.
     * Returns Win or Loss code otherwise..
     * @param input User input.
     * @return Returns levelNum is game not yet complete. Otherwise, returns Win or Loss code corresponding to game outcome.
     */
    @Override
    public int engage(Input input) {
        render();
        if (!level.getIsComplete()) {
            if (input.wasPressed(Keys.TAB)) {
                togglePause();
            }
            if (!paused) {
                level.update(input);
            }
            return levelNum;
        } else {
            if (level.getIsWon()) {
                return EndScreen.WIN_CODE;
            } else {
                return EndScreen.LOSS_CODE;
            }
        }
    }
}
