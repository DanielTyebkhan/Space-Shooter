import java.applet.*;
import java.net.URL;
public class Sound {
    URL url;
    AudioClip sound;

    public Sound(String s){
        url = (getClass().getClassLoader().getResource(s));
        sound = Applet.newAudioClip(url);
    }

    public void play(){
        new Thread(){
            public void run(){
                sound.play();
            }
        }.start();
    }
}
