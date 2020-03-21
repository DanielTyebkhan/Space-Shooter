import javax.swing.*;
import java.awt.event.*;

/**
 * @author Daniel Tyebkhan
 */
public class SpeedBoost extends SpaceObject implements ActionListener{
    private Timer timer;

    /**
     * Constructor
     * @param x The initial x position
     * @param y The intial y position
     * @param speed The speed
     */
    public SpeedBoost(int x, int y, int speed){
        super(x, y, speed, "speedboosticon.png");
        timer = new Timer(5, this);
        timer.start();
    }

    /**
     * Moves the Speed Boost
     * @param a The action triggering the move
     */
    @Override
    public void actionPerformed(ActionEvent a){
        setY(getY() + getSpeed());
    }
}
