
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class SpeedReader {
	
	public boolean snext;
	public String nextWord;
	public int wordCount;
	public int sentenceCount;
	public String filename;
	
	public Scanner text;
	
	public SpeedReader(String filename) throws IOException {
		this.text = new Scanner(new File(filename));
		this.snext = true;
		this.nextWord = "";
		this.wordCount = 0;
		this.sentenceCount = 0;
	}
	
	public boolean hasNext() {
		return this.text.hasNext();
	}
	
	public String snext() {
		this.nextWord = this.text.next();
		return this.nextWord;
	}
	
	public int getWordCount() {
		this.wordCount += 1;
		return this.wordCount;
	}
	
	public int getSentenceCount() {
		String last = this.nextWord.substring(nextWord.length() - 1);
		if(last.equals(".") || last.equals("!") || last.equals("?")) {
			this.sentenceCount += 1;
		}
		return this.sentenceCount;
	}
	
	public static void demonstratePanel(String show, DrawingPanel panel, long wpm) throws InterruptedException {
	    Graphics g = panel.getGraphics();
	    
	    /* 'Clears' window */
	    g.drawRect(0, 0, 400, 300);
	    g.setColor(Color.white);
	    g.fillRect(0, 0, 400, 300);
	    
	    /* Prints word to screen in window */
	    g.setColor(Color.black);
	    Font f = new Font("Courier", Font.BOLD, 46);
	    g.setFont(f);
	    g.drawString(show, 100, 100);
	    Thread.sleep(wpm);
	}
	
	public static void printStaggered() throws InterruptedException {
	    while(true) {
	        System.out.println("Hello World!");
	        Thread.sleep(1000);
	    }
	}
	
//	public static int countTotalWords(SpeedReader speedfile) {
//		int count = 0;
//		while (speedfile.hasNext()) {
//			count = speedfile.getWordCount();
//		}
//		return count;
//	}
	
	public static void printFileInfo(DrawingPanel panel, SpeedReader speedfile, long wpm) throws InterruptedException {
		while (speedfile.hasNext()) {
			demonstratePanel(speedfile.snext(), panel, wpm);
			System.out.println("No of Words: " + speedfile.getWordCount());
			System.out.println("No of Sentences: " + speedfile.getSentenceCount());
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		String file = "/home/zizzachi/test.txt";
		SpeedReader speedfile = new SpeedReader(file);
		DrawingPanel panel = new DrawingPanel(400, 300); //window words appear on
		
		//int x = countTotalWords(speedfile);
		//System.out.println(x);
		
		//long is an int, so doubles can't be used.
		long wordspeed = 45;
		long wpm = 300; //((speedfile.getWordCount()/wordspeed) * 1000);//*wordspeed); //keep *1000
		
		//System.out.println(wordspeed);
		
		printFileInfo(panel, speedfile, wpm);
	}
}
