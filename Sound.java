import java.applet.*;
import java.net.URL;
public class Sound {
    URL url;
    AudioClip sound;

    /**
     * Constructor
     * @param s The filename with the sound
     */
    public Sound(String s){
        url = (getClass().getClassLoader().getResource(s));
        sound = Applet.newAudioClip(url);
    }

    /**
     * plays the sound
     */
    public void play(){
        new Thread(() -> sound.play()).start();
    }
}
