//superclass for spawns
import java.awt.*;
import javax.swing.*;
class SpaceObject{
    public int y;
    public int x;
    public int speed;
    int width;
    int height;
    Image objecticon;
    public SpaceObject(int ix, int iy, int ispeed, String spritename){
        x = ix;
        y = iy;
        speed = ispeed;
        ImageIcon objectsprite = new ImageIcon(spritename);
        width = objectsprite.getIconWidth();
        height = objectsprite.getIconHeight();
        objecticon = objectsprite.getImage();
    }
    public Image getImage(){
        return objecticon;
    }
}