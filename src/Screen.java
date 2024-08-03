import bagel.*;

/** Screen object is a window that can be rendered onto the game.
 * @author lpedersson
 * @version 1.0
 */
public abstract class Screen implements Renderable {
    /** Width of screen. */
    public final static int WINDOW_WIDTH = 1024;
    /** Height of screen. */
    public final static int WINDOW_HEIGHT = 768;
    /** Font file for generic use in screen. */
    public final static String FONT_FILE = "res/FSO8BITR.TTF";
    protected final static Font MAIN_FONT = new Font(FONT_FILE, 64);
    protected final static Font SUB_FONT = new Font(FONT_FILE, 24);
    protected final static Image BACKGROUND_IMAGE = new Image("res/background.png");

    /** Renders screen onto game.
      */
    public void render() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
    }

    /** Lets user interact with screen; returns integer output codes.
     * @param input User input.
     * @return Integer code for flexible use.
     */
    public abstract int engage(Input input);
}
