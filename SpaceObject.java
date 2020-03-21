import java.awt.*;
import javax.swing.*;

/**
 * @author Daniel Tyebkhan
 */
abstract class SpaceObject{
    private int y;
    private int x;
    private int speed;
    private int width;
    private int height;
    private Image objecticon;

    /**
     * Constructor
     * @param x The initial x position of the object
     * @param y The initial y position of the object
     * @param speed The object's speed
     * @param spritename The file containing the object's sprite
     */
    public SpaceObject(int x, int y, int speed, String spritename){
        this.x = x;
        this.y = y;
        this.speed = speed;
        ImageIcon objectsprite = new ImageIcon(spritename);
        width = objectsprite.getIconWidth();
        height = objectsprite.getIconHeight();
        objecticon = objectsprite.getImage();
    }

    /**
     * Gets the image associated with the object
     * @return The object's image
     */
    public Image getImage(){
        return objecticon;
    }

    /**
     * Gets the y position
     * @return the current y position
     */
    public int getY(){
        return y;
    }

    /**
     * Sets the y position
     * @param y The new y position
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Gets the x position
     * @return The current x position
     */
    public int getX(){
        return x;
    }

    /**
     * Sets the x position
     * @param x The new x position
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Gets the width
     * @return The width
     */
    public int getWidth(){
        return width;
    }

    /**
     * Gets the height
     * @return The height
     */
    public int getHeight(){
        return height;
    }

    /**
     * Gets the speed
     * @return The current speed
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * Sets the speed
     * @param speed The new speed
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
}