import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Runner {
	public static void main(String args[]) {

    	JFrame frame = new JFrame();

        Screen sc = new Screen();
        frame.add(sc);

        sc.makeMap();
        sc.makeNPC();
        sc.makeTrees();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        sc.animate();
	}
}