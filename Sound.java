import java.applet.*;
import java.net.URL;
public class Sound {
    URL url;
    AudioClip sound;
    //sets url
    public Sound(String s){
        url = (getClass().getClassLoader().getResource(s));
        sound = Applet.newAudioClip(url);
    }
    //plays on a new thread so it is not interrupted
    public void play(){
        new Thread(){
            public void run(){
                sound.play();
            }
        }.start();
    }
}
