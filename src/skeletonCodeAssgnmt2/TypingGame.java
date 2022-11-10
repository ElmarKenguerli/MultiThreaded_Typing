
package skeletonCodeAssgnmt2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import static skeletonCodeAssgnmt2.WordApp.w;


/* 
    @author Elmar Kenguerli, KNGELM003
    This is the controller class for the typing game, It instantiates the class objects for the view and model classes
*/
public class TypingGame 
{
    static WordApp wordApp = new WordApp();
    static ScoreControl scoreC;
    static  Thread t1, t2;
    
    public static void main(String[] args) 
    {
    	//deal with command line arguments
        wordApp.totalWords=Integer.parseInt(args[0]);  //total words to fall
        wordApp.noWords=Integer.parseInt(args[1]); // total words falling at any point
        assert(wordApp.totalWords>=wordApp.noWords); // this could be done more neatly
        String[] tmpDict=wordApp.getDictFromFile(args[2]); //file of words
        if (tmpDict!=null)
            wordApp.dict= new WordDictionary(tmpDict);

        WordRecord.dict=wordApp.dict; //set the class dictionary for the words.

        wordApp.words = new WordRecord[wordApp.noWords];  //shared array of current words

        //Run Gui in the Event Dispatching Thread for safety and avoiding Race condition
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() 
            {
                wordApp.setupGUI(wordApp.frameX, wordApp.frameY, wordApp.yLimit); 
                scoreC = new ScoreControl(wordApp.words, wordApp.score, wordApp.w.exitStatus(), wordApp, wordApp.totalWords);
                
                //actionListener event for Start Button
                wordApp.startB.addActionListener(new ActionListener()
                {
                   public void actionPerformed(ActionEvent e)
                   {
                        wordApp.textEntry.setText("");
                        scoreC.setWord("");
                        if(!w.exitStatus())
                        {
                            wordApp.textEntry.requestFocus();  //return focus to the text entry field
                            
                             //Start WordPanel thread - for redrawing animation
                            t1 = new Thread(w);
                            t1.start(); 

                            //start ScoreControl thread - for keeping score and updating it
                            t2 = new Thread(scoreC);
                            t2.start();
                        }
                        else
                        {
                            
                            wordApp.textEntry.requestFocus();
                            
                            w.exit(false);
                            scoreC.exit(false);
                            scoreC.score.resetScore();
                            
                            //Start WordPanel thread - for redrawing animation                             
                            t1 = new Thread(w);
                            t1.start(); 

                            //start ScoreControl thread - for keeping score and updating it
                            t2 = new Thread(scoreC);
                            t2.start();
                            
                        }

                   }
                });
                
                //actionListener event for End Button
                wordApp.endB.addActionListener(new ActionListener()
                {
                   public void actionPerformed(ActionEvent e)
                   {
                        
                        w.exit(true);
                        scoreC.exit(true);
                        wordApp.textEntry.setText("");
                        scoreC.setWord("");
                   }
                });
                
                //actionListener event for Quit Button
                wordApp.quitB.addActionListener(new ActionListener()
                {
                   public void actionPerformed(ActionEvent e)
                   {
                        
                         System.exit(0);
                   }
                });
                
                //actionListener event for te Text Box
                wordApp.textEntry.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent evt) {
                        String text = wordApp.textEntry.getText();
                        scoreC.setWord(text);

                        wordApp.textEntry.setText("");
                        wordApp.textEntry.requestFocus();
                   }
                });
                
                
            }
        });


       

        int x_inc=(int)wordApp.frameX/wordApp.noWords;
        //initialize shared array of current words

        for (int i=0;i<wordApp.noWords;i++) {
                wordApp.words[i]=new WordRecord(wordApp.dict.getNewWord(),i*x_inc,wordApp.yLimit);
        }

    }
}
