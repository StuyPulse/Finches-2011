package Code.customClasses.DancingFinch;

/**
 * Created by: Celestine Lau
 * Date: 15 Feb 2009
 * Main class that uses methods from the DancingFinch class
 */

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import javax.sound.midi.*;

public class DancingFinchDriver
{
	
	public static void main(final String[] args) throws Exception
	{
		/*
		 * This driver program will demonstrate simple capabilities of the DancingFinch.
		 * It will allow the user to choose a MIDI file to play, and then extract information
		 * about the tempo of the file. It will then load up the finch and play the soundclip
		 * over the computer's speakers. The finch will make a move once every 2 beats in 
		 * the song. 
		 */
		
		/* Allow the user to choose a MIDI file */
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setName("Open a MIDI file to play");
		chooser.setFileFilter(
			new FileFilter() {
				public boolean accept(File file) {
					if (file.isFile() && file.getAbsolutePath().endsWith(".mid"))
						return true;
					return false;
				}

				public String getDescription() {
					return "Midi files";
				}
			}
		);
		
		String filename = null;
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filename = chooser.getSelectedFile().getName();
		}
		else {
			System.out.println("Thank you.");
			System.exit(0);
		}
		
		
		/* Load the MidiPlayer object and get the Sequence object from the MIDI file */
		MidiPlayer mplayer = new MidiPlayer();
		Sequence seq = mplayer.getSequence(filename);
		if (seq == null) System.exit(0);
		
		/* Instantiating the Finch object, flash a red LED to signal that the program has started */
		Finch myFinch = new Finch();
		myFinch.setLED(255, 0, 0);
		Thread.sleep(1000);
		myFinch.setLED(0, 0, 0);
		Thread.sleep(1000);
		mplayer.play(seq, false);
		
		/* Sense the tempo of the sequence, get the time between every beat */
		Sequencer seqr = mplayer.getSequencer();
		int interval = Math.round(120000.0f / seqr.getTempoInBPM());
		System.out.println("Time in between moves: " + interval + " ms.");
		Timer time = new Timer();
		long timeToNextBeat = interval; 
		DancingFinch df = new DancingFinch(myFinch);
		df.setInterval(interval);
		
		boolean tapped = false;
		
		/*
		 * While the music is playing, the DancingFinch will make one move every beat. 
		 * Also, if the Finch has been tapped since the last move, it will reverse its 
		 * direction. The loop will terminate when the song ends.
		 */
		while (true) {
			
			timeToNextBeat -= time.elapsedTime();
			
			if (df.isTapped()) tapped = true;
			if (timeToNextBeat > 0) continue;			
			timeToNextBeat += interval;
			
			if (tapped) {
				df.reverse();
				System.out.println("Tapped");
				tapped = false;
			}
			df.nextMove();
						
			if (!seqr.isRunning()) break;
		}
     
		myFinch.quit();
		System.exit(0);
	}
	
	/**
	 * A simple timer class. The timer starts when the object is created. Subsequently, 
	 * calls to elapsedTime return the time in milliseconds passed since the object was created
	 * @author Celestine Lau
	 *
	 */
	private static class Timer {
		
		private long currentTime;
		
		/**
		 * Creates a timer object. The timer starts the moment the object is created
		 *
		 */
		public Timer () {
			currentTime = System.currentTimeMillis();
		}
		
		/**
		 * Returns the time elapsed since the last time elapsedTime was called, or the time
		 * of creation of the object if this is the first time elapsedTime was called
		 * @return The time elapsed in milliseconds
		 */
		public int elapsedTime() {
			long elapsedTime = System.currentTimeMillis() - currentTime;
			currentTime += elapsedTime;
			return (int) elapsedTime;
		}
	}
	
}

