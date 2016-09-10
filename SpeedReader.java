
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.awt.*;
import java.awt.font.TextAttribute;

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

	public static void demonstratePanel(String show, DrawingPanel panel, 
		 int fsize, int width, int height, int i) throws InterruptedException {
		Graphics g = panel.getGraphics();

		/* 'Clears' window */
		g.drawRect(0, 0, width, height);
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);

		/* FontMetrics Stuff */
		FontMetrics fm = g.getFontMetrics();
		int stringWidth = fm.stringWidth(show);
		int centerx = (width - stringWidth) / 2;
		int stringHeight = fm.getHeight();
		int centery = (height - stringHeight) / 2;
		if (i == 1) {
			centery -= 20;
		}

		/* Prints word to screen in window */
		g.setColor(Color.black);
		Font f = new Font("Courier", Font.BOLD, fsize);
		g.setFont(f);
		
		focusLettering(show, centerx, centery, g, f);
		
	}

	public static void focusLettering(String show, int centerx, int centery,
			Graphics g, Font f) {

		AttributedString a = new AttributedString(show);
		a.addAttribute(TextAttribute.FONT, f);
		for (int i = 0; i < show.length(); i++) {
			if (show.length() < 2) {
				a.addAttribute(TextAttribute.FOREGROUND, Color.RED, 0, 1);
			} else if (show.length() < 6) {
				a.addAttribute(TextAttribute.FOREGROUND, Color.RED, 1, 2);
			} else if (show.length() < 10) {
				a.addAttribute(TextAttribute.FOREGROUND, Color.RED, 2, 3);
			} else if (show.length() < 14) {
				a.addAttribute(TextAttribute.FOREGROUND, Color.RED, 3, 4);
			} else {
				a.addAttribute(TextAttribute.FOREGROUND, Color.RED, 4, 5);
			}
		}
		g.drawString(a.getIterator(), centerx, centery);
	}

	public static void printFileInfo(DrawingPanel panel, SpeedReader speedfile,
			long speedtime, int fsize, int width, int height) throws InterruptedException {
		int i = 0;
		while (speedfile.hasNext()) {
			i++;
			demonstratePanel(speedfile.snext(), panel, fsize, width, height, i);
			System.out.println("No of Words: " + speedfile.getWordCount());
			System.out.println("No of Sentences: " + speedfile.getSentenceCount());
			System.out.println("WPM : " + speedtime);
			Thread.sleep(speedtime);
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		if (args.length < 5 || args.length > 5) {
			System.out.println("Invalid Number Of Arguments");
		} else {
			String filename = args[0];
			int width = Integer.parseInt(args[1]);
			int height = Integer.parseInt(args[2]);
			int fsize = Integer.parseInt(args[3]);
			int wpm = Integer.parseInt(args[4]);
			SpeedReader speedfile = new SpeedReader(filename);
			DrawingPanel panel = new DrawingPanel(width, height); //window words appear on
			long wps = (long) (wpm / 60);
			long sleeptime = (long) (1000 / wps);	
			printFileInfo(panel, speedfile, sleeptime, fsize, width, height);	
		}
	}
}

//http://www.java2s.com/Code/JavaAPI/java.awt/GraphicsgetFontMetrics.htm
//http://stackoverflow.com/questions/21331221/how-to-change-the-color-of-a-single-character-in-a-string-with-drawstring
