import bagel.*;

/** Screen rendered after a level; informs user whether they have successfully completed the level,
 * and instructs them to return to main screen.
 * @author lpedersson
 * @version 1.0
 */
public class EndScreen extends Screen {
    private final static String WIN_MESSAGE = "CLEAR!";
    private final static String LOSS_MESSAGE = "TRY AGAIN";
    private final static String INSTRUCTION = "PRESS SPACE TO RETURN TO LEVEL SELECTION";
    private final static double MESSAGE_Y_VAL = 300;
    private final static double INSTRUCTION_Y_VAL = 500;
    /** Numerical code expected in event level is won. */
    public final static int WIN_CODE = -1;
    /** Numerical code expected in event level is lost. */
    public final static int LOSS_CODE = -2;


    private final String finalMessage;

    /** Generates new End Screen and sets finalMessage to win or loss message in accordance with outcome code.
     * @param outcomeCode Numerical code informing whether level has been won or lost.
     */
    public EndScreen(int outcomeCode) {
        if (outcomeCode == WIN_CODE) {
            finalMessage = WIN_MESSAGE;
        } else {
            finalMessage = LOSS_MESSAGE;
        }
    }

    /** Renders win or loss message, and renders instruction to return to main screen.
      */
    public void render() {
        super.render();
        MAIN_FONT.drawString(finalMessage, (double) Screen.WINDOW_WIDTH /2 - MAIN_FONT.getWidth(finalMessage)/2,
                MESSAGE_Y_VAL);
        SUB_FONT.drawString(INSTRUCTION, (double) Screen.WINDOW_WIDTH /2 - SUB_FONT.getWidth(INSTRUCTION)/2,
                INSTRUCTION_Y_VAL);
    }

    /** Lets user engage with screen; returns 0 if space-bar is pressed, to inform game to return to main screen.
     * Else returns -1 to inform game to remain on end screen.
     * @return 0 if space-bar pressed, -1 otherwise.
     */
    public int engage(Input input) {
        this.render();
        if (input.wasPressed(Keys.SPACE)) {
            return 0;
        } else {
            return -1;
        }
    }
}
