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

    public static boolean keyPressed(KeyEvent k){
        int key = k.getKeyCode();
        if(key == 32){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent a){
        y = y-speed;
    }
}
