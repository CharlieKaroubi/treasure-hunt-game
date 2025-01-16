import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class NPC{
   private int x;
   private int y;
   private BufferedImage npc;
   private BufferedImage npc2;

   public NPC(int x, int y){
        this.x = x;
        this.y = y;

        try{
            npc = ImageIO.read(new File("mickey.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            npc2 = ImageIO.read(new File("mickey2.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
   }

    public void drawMe1(Graphics g){
        g.setColor(Color.red);
        g.drawImage(npc,x+1,y,null);
    }

    public void drawMe2(Graphics g){
        g.setColor(Color.red);
        g.drawImage(npc2,x+1,y,null);
    }
}