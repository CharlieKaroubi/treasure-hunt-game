import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Tree{
    private int x;
    private int y;

    public Tree(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void drawMe(Graphics g){
        g.setColor(new Color(128,0,255));
        g.fillRect(x+3,y,5,8);
    }
}