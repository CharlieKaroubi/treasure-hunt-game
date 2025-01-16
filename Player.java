import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Player{
    private int x;
    private int y;
    private BufferedImage player;

    public Player(int x, int y){
        this.x = x;
        this.y = y;

        try{
            player = ImageIO.read(new File("player.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void drawMe(Graphics g){
        g.setColor(Color.black);
        g.drawImage(player,x+1,y,null);
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void moveLeft(){
        x = x-12;
    }
    public void moveRight(){
        x = x+12;
    }
    public void moveUp(){
        y = y-8;
    }
    public void moveDown(){
        y = y+8;
    }
}