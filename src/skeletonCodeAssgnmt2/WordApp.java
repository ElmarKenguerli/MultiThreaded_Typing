package skeletonCodeAssgnmt2;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.IOException;


import java.util.Scanner;
//model is separate from the view.

/* 
    This class is the view class which displays the Swing GUI and game to the player.
*/
public class WordApp {
//shared variables
	static int noWords=4;
	static int totalWords;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static volatile boolean done;  //must be volatile
	static 	Score score = new Score();
	static WordPanel w;	
	
	//Jframe components
        static JTextField textEntry;
        static JButton startB, endB, quitB;
        static JLabel caught, missed, scr; 
	public static void setupGUI(int frameX,int frameY,int yLimit) 
        {
            // Frame init and dimensions
            JFrame frame = new JFrame("WordGame"); 
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(frameX, frameY);
            JPanel g = new JPanel();
            g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
            g.setSize(frameX,frameY);

            w = new WordPanel(words,yLimit, false, score, totalWords);
            
            w.setSize(frameX,yLimit+100);
            g.add(w); 

            JPanel txt = new JPanel();
            txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
            caught =new JLabel("Caught: " + score.getCaught() + "    ");
            missed =new JLabel("Missed:" + score.getMissed()+ "    ");
            scr =new JLabel("Score:" + score.getScore()+ "    ");    
            txt.add(caught);
            txt.add(missed);
            txt.add(scr);
            

            textEntry = new JTextField("",20);
            

            txt.add(textEntry);
            txt.setMaximumSize( txt.getPreferredSize() );
            g.add(txt);

            JPanel b = new JPanel();
            b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS)); 
            
            //buttons
            startB = new JButton("Start");;
         
            endB = new JButton("End");;

            quitB = new JButton("Quit");;

            //adding buttons to form
            b.add(startB);
            b.add(endB);
            b.add(quitB);

            g.add(b);

            frame.setLocationRelativeTo(null);  // Center window on screen.
            frame.add(g); //add contents to window
            frame.setContentPane(g);     
            frame.setVisible(true);
	}

        public static String[] getDictFromFile(String filename) 
        {
            String [] dictStr = null;
            try {
                Scanner dictReader = new Scanner(new FileInputStream(filename));
                int dictLength = dictReader.nextInt();

                dictStr=new String[dictLength];
                for (int i=0;i<dictLength;i++) {
                    dictStr[i]=new String(dictReader.next());
                }
                dictReader.close();
            } 
            catch (IOException e) 
            {
                System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
            return dictStr;
	}

	
}