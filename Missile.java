import javax.swing.*;
import java.awt.event.*;
public class Missile extends SpaceObject implements ActionListener{
    private Timer timer;

    /**
     * Constructor
     * @param x The initial x position
     * @param y The initial y position
     * @param speed The speed of the missile
     */
    public Missile(int x, int y, int speed){
        super(x, y, speed, "missileicon.png");
        timer = new Timer(1, this);
        timer.start();
    }

    /**
     * Checks if a missile should be shot when the user presses a key
     * @param k the event to check for the spacebar
     * @return true if the spacebar is pressed, else false
     */
    public static boolean keyPressed(KeyEvent k){
        int key = k.getKeyCode();
        return key == 32;
    }

    /**
     * Moves the missile
     * @param a The timer tick triggering the missile to move
     */
    @Override
    public void actionPerformed(ActionEvent a){
        setY(getY()-getSpeed());
    }
}
