package skeletonCodeAssgnmt2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static skeletonCodeAssgnmt2.WordApp.w;

import javax.swing.JPanel;
import static skeletonCodeAssgnmt2.TypingGame.scoreC;

/* 
    This is a model class used to control the animation of the words
*/
public class WordPanel extends JPanel implements Runnable {
		public static volatile boolean done;
		private WordRecord[] words;
		private int noWords, tot;
		private int maxY;
                private boolean exit = false;
                static Thread t1;
                static Score score;
                //constructor
		WordPanel(WordRecord[] words, int maxY, boolean exit, Score score, int tot) //added exit, thread and score object
                {
                    this.words=words; //will this work?
                    noWords = words.length;
                    done=false;
                    this.maxY=maxY;	
                    this.exit = exit;
                    this.tot = tot;
                    this.score = score;
                        
		}
                
                public synchronized boolean exitStatus()
                {
                    return exit;
                }
                public synchronized void exit(boolean exit)
                {
                    this.exit = exit;
                }
		public void paintComponent(Graphics g) 
                {
		    int width = getWidth();
		    int height = getHeight();
		    g.clearRect(0,0,width,height);
		    g.setColor(Color.red);
		    g.fillRect(0,maxY-10,width,height);

		    g.setColor(Color.black);
		    g.setFont(new Font("Helvetica", Font.PLAIN, 26));

		    for (int i=0;i<noWords;i++){	    	
		    	g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()-5);	
		    }
		   
                }
		
		
		
		public void run() 
                {
                    int total = score.getTotal();
                    while(!exit && total< tot)
                    {
                        repaint();
                        
                        int fallSpeed;
                        for (int i=0;i<noWords;i++){
                            
                            
                            fallSpeed = words[i].getSpeed();
                            words[i].drop(fallSpeed );  //y-movement
                            
                            if(words[i].dropped())
                            {
                                score.missedWord();
                                words[i].resetWord();
                            }
                        }
                        total = score.getTotal();
                        try 
                        {
                            Thread.sleep(600);
                        } 
                        catch (InterruptedException ex) 
                        {
                            Logger.getLogger(WordPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    for (WordRecord wr : words) 
                    {
                        wr.resetWord();
                        wr.resetPos();
                    }
                    if(score.getTotal()>= tot)
                    {
                        
                        
                        repaint();
                        
                        Graphics g = getGraphics();
                        g.setFont(new Font("Helvetica", Font.PLAIN, 45));
                        g.drawString("Game Over!", getWidth()/3, getHeight()/2);
                        String gameover = "Game Over!" + "\n" + "Score: " + score.getScore();
                        JOptionPane.showMessageDialog(null, gameover, "Word Game", JOptionPane.INFORMATION_MESSAGE, null);
                        score.resetScore();
                        w.exit(true);
                        scoreC.exit(true);
                    }
		}

	}


