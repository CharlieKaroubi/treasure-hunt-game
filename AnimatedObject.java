import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class AnimatedObject implements Runnable{
    
    private int x;
    private int y;
    private int dir;
    private int up;
    private int right;

    private BufferedImage shark;

    public AnimatedObject(int x, int y, int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
        up = 1;
        right = 1;

        try{
            shark = ImageIO.read(new File("shark.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void drawMe(Graphics g){
        g.drawImage(shark,x+1,y-2,null);
    }

    public void moveUp(){
        y-=8;
    }

    public void moveDown(){
        y+=8;
    }

    public void moveLeft(){
        y-=12;
    }

    public void moveRight(){
        y+=12;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void moveUpDown(){
        if(up == 0){
			y = y+8;
			if(y > 600){
				up = 1;
			}
		} else if(up == 1){
			y = y-8;
			if(y < 72){
				up = 0;
			}
		}
	}

    public void moveRightLeft(){
        if(right == 1){
			x = x+12;
			if(x > 1068){
				right = 0;
			}
		} else if(right == 0){
			x = x-12;
			if(x < 60){
				right = 1;
			}
		}
	}

	@Override
	public void run() {
		if(dir == 1){
            moveUpDown();
        } else if(dir == 2){
            moveRightLeft();
        }
        
	}
}