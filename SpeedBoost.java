import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class SpeedBoost extends SpaceObject implements ActionListener{
    Timer timer;
    public SpeedBoost(int ix, int iy, int ispeed){
        super(ix, iy, ispeed, "speedboosticon.png");
        timer = new Timer(5, this);
        timer.start();
    }
    //moves the speedboost by changing its y coordinate
    @Override
    public void actionPerformed(ActionEvent a){
        y += speed;
    }
}
