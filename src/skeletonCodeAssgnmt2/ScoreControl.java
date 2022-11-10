/*
 * Elmar Kenguerli
 * 06/09/2021
 */
package skeletonCodeAssgnmt2;

import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * This is a model class used to control the score, it adds the words caught and missed to the score object and displays it on the view/GUI
 */
public class ScoreControl extends JPanel implements Runnable
{
    static WordRecord[] words;
    static int noWords;
    static Score score;
    static String typedText = "";
    static WordApp gui;
    static boolean exit;
    public ScoreControl(WordRecord[] words, Score score, boolean exit, WordApp gui, int tot)
    {
        this.words = words;
        this.noWords = tot;
        this.score = score;
        this.gui = gui;
        this.exit = exit;
    }

    public synchronized void  setWord(String typedText)
    {
        this.typedText = typedText;
    }
    
    public synchronized void exit(boolean exit)
    {
        this.exit = exit;
    }
    
    @Override
    public void run() 
    {
        
        int total = score.getTotal();
        score.resetScore();
        while(!exit && total< noWords)
        {
            
            for (WordRecord word : words) 
            {
                String temp = word.getWord();
                if(word.matchWord(typedText) && total< noWords )
                {
                    score.caughtWord(temp.length());
                    total = score.getTotal(); 
                    System.out.println("MATCH"); //print "Match" to console when word is found
                }
            total = score.getTotal(); 
            }
            gui.caught.setText("Caught: " + score.getCaught() + "    ");
            gui.missed.setText("Missed:" + score.getMissed()+ "    ");
            gui.scr.setText("Score:" + score.getScore()+ "    ");    
            repaint();
        }
        
    }
}
