package skeletonCodeAssgnmt2;

/* 
    This class contains the dictionary of words to be used in the typing game
*/
public class WordDictionary {
	int size;
	static String [] theDict= {"litchi","banana","apple","mango","pear","orange","strawberry",
		"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
		"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
		"boysenberry","loquat","avocado"}; //default dictionary
	
        //constructors
         
	WordDictionary(String [] tmp) 
        {
		size = tmp.length;
		theDict = new String[size];
		for (int i=0;i<size;i++) 
                {
			theDict[i] = tmp[i];
		}	
	}
	//default
	WordDictionary() {
            size=theDict.length;		
	}
	
	public synchronized String getNewWord() 
        {
            int wdPos= (int)(Math.random() * size);
            return theDict[wdPos];
	}
	
}
