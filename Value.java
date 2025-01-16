import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;


public class Value{
    private int x;
    private int y;
    private Color color;
    private int walk;

    public Value(int x, int y, Color color, int walk){
        this.x = x;
        this.y = y;
        this.color = color;
        this.walk = walk;
    }

    public void drawMe(Graphics g){
        g.setColor(color);
        g.fillRect(x,y,12,8);
    }

    public boolean isWalkable(){
        if(walk == 3|| walk == 7|| walk == 8){
            return false;
        }
        return true;
    }

    public String toString(){
        if(color.equals(Color.blue)){
            return "blue";
        }
        return "yellow";
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getText(){
        return walk;
    }
}
