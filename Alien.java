import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Alien extends SpaceObject implements ActionListener{
    Timer timer;
    public Alien(int ix, int iy, int ispeed){
        super(ix, iy, ispeed, "alienicon.png");
        timer = new Timer(5, this);
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent a){
        y += speed;
    }
}
