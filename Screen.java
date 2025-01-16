import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;


public class Screen extends JPanel implements KeyListener{

    private MyHashTable<Integer, Value> hash;
    private Player player;
    DLList<Value> valueList = new DLList<Value>();
    DLList<Key> keyList = new DLList<Key>();
    DLList<NPC> npc = new DLList<NPC>();
    DLList<Tree> tree = new DLList<Tree>();
    private int width;
    private int height;
    private int nX;
    private int nY;
    private int taskNum;
    private int rc;
    private String row;
    private String col;
    private String questLine;
    private int x;
    private int y;
    private int questNum;
    private int playerX;
    private int playerY;
    private Color color;
    private boolean endGame;
    private boolean startGame;
    private boolean walk;
    private boolean capTalk;
    private BufferedImage background;
    private BufferedImage winScreen;
    private DLList<AnimatedObject> obj;

	public Screen() {
        hash = new MyHashTable<Integer, Value>();
        player = new Player(708,504);
        width = 12;
        height = 8;
        row = "";
        col = "";
        x = 0;
        y = 0;
        nX = 0;
        nY = 0;
        taskNum = 1;
        questNum = 1;
        questLine = "";
        capTalk = false;
        endGame = false;
        startGame = true;
        color = null;
        obj = new DLList<AnimatedObject>();
        obj.add(new AnimatedObject(1056,96,1));
        obj.add(new AnimatedObject(96,768,2));
        obj.add(new AnimatedObject(96,8,2));

        try{
            background = ImageIO.read(new File("background.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            winScreen = ImageIO.read(new File("winScreen.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        setFocusable(true);
		addKeyListener(this);
    }

    public Dimension getPreferredSize() {
    	//Sets the size of the panel
        return new Dimension(1200,800);
	}


    public void makeMap(){
        
        try{
            Scanner scan = new Scanner(new FileReader("map.txt"));
            while(scan.hasNextLine()){
                x = 0;
                String[] stringList = scan.nextLine().split(" ");
                for(String each: stringList){
                    int block = Integer.parseInt(each);
                    
                    if(block == 3){
                        color = Color.blue;
                    } else if(block == 1 || block == 7 || block == 8){
                        color = new Color(0,196,255);
                    } else if(block == 2){
                        color = new Color(101,52,0);
                    } 

                    valueList.add(new Value(x,y,color,block));
                    hash.put(y, new Value(x,y,color,block));
                    x+=12;
                }
                y+=8;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void makeNPC(){
        for(int i = 0; i<valueList.size();i++){
            if(valueList.get(i).getText() == 8){
                npc.add(new NPC(valueList.get(i).getX(),valueList.get(i).getY()));
            }
        }
    }

    public void makeTrees(){
        for(int i = 0; i<valueList.size();i++){
            if(valueList.get(i).getText() == 7){
                tree.add(new Tree(valueList.get(i).getX(),valueList.get(i).getY()));
            }
        }
    }

    public void moveObjects(){
		for(int i=0; i<obj.size(); i++){
			Thread t1 = new Thread(obj.get(i));
			t1.start();
		}
	}

    public void animate(){
        while(true){
            moveObjects();

            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            if(!endGame){
                repaint();
            } else {
                break;
            }
    
		}
    }
    

    public void paintComponent(Graphics g) {
    	super.paintComponent(g);

         // draw me
        for(int i = 0; i<valueList.size(); i++){
            valueList.get(i).drawMe(g);
        }

        for(int i = 0; i<obj.size(); i++){
            obj.get(i).drawMe(g);
        }

        npc.get(0).drawMe1(g);
        npc.get(1).drawMe2(g);
        

        for(int i = 0; i<tree.size(); i++){
            tree.get(i).drawMe(g);
        }

        //draw grid
        g.setColor(Color.black);
        for(int r=0; r<100; r++){
            for(int c=0; c<100; c++){
                g.drawRect(r*width, c*height, width, height);
            }
        }

        if(taskNum<3){
            g.setColor(new Color(255,144,149));
            g.fillOval(147,113,5,5);
        }

        player.drawMe(g);
        
       // System.out.println(player.getX()+":"+player.getY());

        g.setColor(Color.white);
        Font font = new Font("TimesRoman",Font.PLAIN,25);
        g.setFont(font);
        if(taskNum == 1){
            questLine = "Talk to the Captain";
        } else if(taskNum == 2){
            questLine = "Captain - \"Collect the Special Pink Coconut\"";
        } else if(taskNum == 3){
            questLine = "Captain - \"Give the Coconut to the Parrot\"";
        } else if(taskNum == 4){
            questLine = "Parrot - \"Cacaw Cacaw! Go to the Captain for Some Gold!\"";
        } else if(taskNum == 5){
            endGame = true;
        }

        g.drawString("Quest "+questNum+": "+questLine,500,50);

        g.setFont(new Font("TimesRoman",Font.PLAIN,10));
        g.drawString("Captain",855,445);
        g.drawString("Parrot",175,668);
        g.drawString("You",player.getX()-5, player.getY()-2);

        if(endGame){
            g.drawImage(winScreen,0,0,null);
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman",Font.PLAIN,50));
            g.drawString("YOU WIN!",480,600);
            winGame();
        }
        
        if(startGame){
            g.drawImage(background,0,0,null);
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman",Font.PLAIN,50));

            g.drawString("\'WASD\' to Move",400,200);
            g.drawString("\'F\' to Interact with Objects and Characters",180,280);
            g.drawString("Press Space Bar to Start",330,360);
        }
        
	}

    public void collectCoco(){
        try {
            URL url = this.getClass().getClassLoader().getResource("Collect_Sound.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    public void talkNPC(){
        try {
            URL url = this.getClass().getClassLoader().getResource("Talk_Sound.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    public void winGame(){
        try {
            URL url = this.getClass().getClassLoader().getResource("Win_Sound.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode() == 87){
            int pX = player.getX();
            int pY = player.getY();
            LinkedList<Value> temp = hash.get(pY-8);
            if(temp.get(pX/12).isWalkable()){
                player.moveUp();
            }
        } else if(e.getKeyCode() == 83){
            int pX = player.getX();
            int pY = player.getY();
            LinkedList<Value> temp = hash.get(pY+8);
            if(temp.get(pX/12).isWalkable()){
                player.moveDown();
            }
        } else if(e.getKeyCode() == 68){
            int pX = player.getX();
            int pY = player.getY();
            LinkedList<Value> temp = hash.get(pY);
            if(temp.get((pX/12)+1).isWalkable()){
                player.moveRight();
            }
        } else if(e.getKeyCode() == 65){
            int pX = player.getX();
            int pY = player.getY();
            LinkedList<Value> temp = hash.get(pY);
            if(temp.get((pX/12)-1).isWalkable()){
                player.moveLeft();
            }

        } else if(e.getKeyCode() == 70){
            
            if(taskNum == 1){
                if(player.getX()>=852 && player.getX()<=876){
                    if(player.getY()>=400 && player.getY()<=456){
                        taskNum++;
                        talkNPC();
                    }
                }
            }

            if(taskNum == 2){
                if(player.getX() >= 132 && player.getX() <= 156){
                   if(player.getY()>=100 && player.getY()<= 124){
                        taskNum++;
                        questNum++;
                        collectCoco();
                   }
                }
            }

            if(taskNum == 3){
                if(player.getX()>=168 && player.getX()<=192){
                    if(player.getY()>=664 && player.getY()<=680){
                        taskNum++;
                        talkNPC();
                    }
                }
            }

            if(taskNum == 4){
                if(player.getX()>=852 && player.getX()<=876){
                    if(player.getY()>=400 && player.getY()<=456){
                        taskNum++;
                    }
                }
            }
            
        } else if(e.getKeyCode() == 67){
                if(!startGame){
                    taskNum++;
                if(taskNum == 3){
                    questNum++;
                }
            }
        } else if(e.getKeyCode() == 32){
            if(startGame){
                startGame = false;
            }
        }

        if(!endGame)
            repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    
}