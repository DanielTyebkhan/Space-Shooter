import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Missile extends SpaceObject implements ActionListener{
    Timer timer;

    public Missile(int ix, int iy, int ispeed){
        super(ix, iy, ispeed, "missileicon.png");
        timer = new Timer(1, this);
        timer.start();
    }
    //returns that the missile should be shot if the key is the spacebar
    public static boolean keyPressed(KeyEvent k){
        int key = k.getKeyCode();
        if(key == 32){
            return true;
        }else{
            return false;
        }
    }
    //moves the missile by changing y coordinate
    @Override
    public void actionPerformed(ActionEvent a){
        y = y-speed;
    }
}
