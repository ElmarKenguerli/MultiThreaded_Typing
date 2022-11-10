package skeletonCodeAssgnmt2;

import java.util.concurrent.atomic.AtomicInteger;

/* 
    This class contains the score and methods to alter the score
*/
public class Score {
	private AtomicInteger missedWords;
	private AtomicInteger caughtWords;
	private AtomicInteger gameScore;
	
	Score() {
		missedWords=new AtomicInteger(0);
		caughtWords=new AtomicInteger(0);
		gameScore=new AtomicInteger(0);
	}
		
	// all getters and setters return Atomic variables
	
	public int getMissed() {
            return missedWords.get();
	}

	public int getCaught() {
            return caughtWords.get();
	}
	
	public int getTotal() {
            int intTot = missedWords.get()+caughtWords.get();
            AtomicInteger total = new AtomicInteger(intTot);
            return total.get();
	}

	public int getScore() {
		return gameScore.get();
	}
	
	public void missedWord() {
		missedWords.getAndIncrement();
	}

	public void caughtWord(int length) {
		caughtWords.getAndIncrement();
		gameScore.getAndAdd(length);
	}

	public void resetScore() {
		caughtWords.set(0);
		missedWords.set(0);
		gameScore.set(0);
	}
}
