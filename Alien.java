import javax.swing.*;
import java.awt.event.*;

/**
 * @author Daniel Tyebkhan
 */
public class Alien extends SpaceObject implements ActionListener{
    private Timer timer;

    /**
     * Constructor
     * @param x The initial x position of the alien
     * @param y The initial y position of the alien
     * @param speed The alien's speed
     */
    public Alien(int x, int y, int speed){
        super(x, y, speed, "alienicon.png");
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    /**
     * Moves the alien
     * @param a the action event triggering the method
     */
    @Override
    public void actionPerformed(ActionEvent a){
        setY(getY() + getSpeed());
    }
}
