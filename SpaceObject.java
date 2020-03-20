//superclass for spawns
import java.awt.*;
import javax.swing.*;
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

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getSpeed(){
        return speed;
    }
}