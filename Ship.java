import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Ship extends SpaceObject{
    boolean leftmove = false;
    boolean rightmove = false;
    public Ship(int ix, int iy){
        super(ix,  iy,  2, "shipicon.png");
    }
    //moves based on variable returned by keypressed
    public void move(){
        if(leftmove){
            x += -speed;
            if(x>1000-width){
                x=1000-width;
            }
            if(x<0){
                x=0;
            }
        }
        if(rightmove){
            x += speed;
            if(x>1000-width){
                x=1000-width;
            }
            if(x<0){
                x=0;
            }
        }

    }
    //returns the direction the ship should move
    public void keyPressed(KeyEvent k){
        int key = k.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            leftmove = true;
        }
        if(key == KeyEvent.VK_RIGHT){
            rightmove = true;
        }
    }
    //sets the variable telling the ship to move to falsse
    public void keyReleased(KeyEvent k){
        int key = k.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            leftmove = false;
        }
        if(key == KeyEvent.VK_RIGHT){
            rightmove = false;
        }
    }
}
