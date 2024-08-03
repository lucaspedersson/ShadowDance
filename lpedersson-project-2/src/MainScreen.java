import bagel.*;

/** Screen rendered at start of game and between levels; contains menu options.
 * @author lpedersson
 * @version 1.0
 */
public class MainScreen extends Screen {
    private final static String TITLE = ShadowDance.GAME_TITLE;
    private final static String INSTRUCTION_1 = "SELECT LEVELS WITH";
    private final static String INSTRUCTION_2 = "NUMBER KEYS";
    private final static String INSTRUCTION_3 = "1   2   3";
    private final static double TITLE_Y_VAL = 250;
    private final static double INSTR_1_Y_OFFSET = 190;
    private final static double INSTR_2_Y_OFFSET = 220;
    private final static double INSTR_3_Y_OFFSET = 280;


    /** Renders screen, with title text and instruction text.
      */
    public void render() {
        super.render();
        MAIN_FONT.drawString(TITLE, (double) Screen.WINDOW_WIDTH /2 - MAIN_FONT.getWidth(TITLE)/2,
                TITLE_Y_VAL);
        SUB_FONT.drawString(INSTRUCTION_1, (double) Screen.WINDOW_WIDTH /2 - SUB_FONT.getWidth(INSTRUCTION_1)/2,
                TITLE_Y_VAL + INSTR_1_Y_OFFSET);
        SUB_FONT.drawString(INSTRUCTION_2, (double) Screen.WINDOW_WIDTH /2 - SUB_FONT.getWidth(INSTRUCTION_2)/2,
                TITLE_Y_VAL + INSTR_2_Y_OFFSET);
        SUB_FONT.drawString(INSTRUCTION_3, (double) Screen.WINDOW_WIDTH /2 - SUB_FONT.getWidth(INSTRUCTION_3)/2,
                TITLE_Y_VAL + INSTR_3_Y_OFFSET);
    }

    /** Checks if number keys 1, 2, or 3 have been pressed to select a level. Returns corresponding number if so.
     * @return Integer corresponding to value input by user.
     */
    public int engage(Input input) {
        this.render();
        if (input.wasPressed(Keys.NUM_1)) {
            return 1;
        } else if (input.wasPressed(Keys.NUM_2)) {
            return 2;
        } else if (input.wasPressed(Keys.NUM_3)) {
            return 3;
        } else {
            return 0;
        }
    }
}
