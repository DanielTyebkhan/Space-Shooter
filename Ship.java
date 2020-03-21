import java.awt.event.*;

/**
 * @author Daniel Tyebkhan
 */
public class Ship extends SpaceObject{
    private boolean leftMove = false;
    private boolean rightMove = false;

    /**
     * Constructor
     * @param x The initial x position
     * @param y The initial y position
     */
    public Ship(int x, int y){
        super(x,  y,  2, "shipicon.png");
    }

    /**
     * Moves the ship
     */
    public void move(){
        if(leftMove){
            moveLeft();
        }else if(rightMove){
            moveRight();
        }
    }

    /**
     * Moves the ship right
     */
    private void moveRight() {
        setX(getX() + getSpeed());
        if(getX()>Game.WIDTH - getWidth()){
            setX(Game.HEIGHT - getWidth());
        }
        if(getX()<0){
            setX(0);
        }
    }

    /**
     * Moves the ship left
     */
    private void moveLeft() {
        setX(getX()-getSpeed());
        if(getX()>Game.WIDTH-getWidth()){
            setX(Game.WIDTH-getWidth());
        }
        if(getX()<0){
            setX(0);
        }
    }

    /**
     * Checks which key the user presses
     * @param k The key event the user caused
     */
    public void keyPressed(KeyEvent k){
        int key = k.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            leftMove = true;
        }
        if(key == KeyEvent.VK_RIGHT){
            rightMove = true;
        }
    }

    /**
     * Checks when the user releases a key
     * @param k The key event the user triggered
     */
    public void keyReleased(KeyEvent k){
        int key = k.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            leftMove = false;
        }
        if(key == KeyEvent.VK_RIGHT){
            rightMove = false;
        }
    }
}
