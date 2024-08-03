import javax.sound.sampled.*;

/**
 * Class for managing the music being played in the background
 * @author SWEN20003 Staff
 */
public class Track extends Thread {
    private Clip clip;

    /** Creates a new track object from .wav file.
     * @param filePath File path to .wav file from which to generate Track.
     */
    public Track(String filePath) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new java.io.File(filePath));
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, stream.getFormat()));
            clip.open(stream);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Pauses track.
      */
    public void pause() {
        try {
            clip.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Runs track.
     */
    public void run(){
        try {
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}