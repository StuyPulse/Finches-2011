package Code.customClasses.DancingFinch;

import java.io.*;
import javax.sound.midi.*;

/**
 * Allows the user to play MIDI files. Taken from http://www.brackeen.com/javagamebook/
 * @author David Brackeen
 *
 */
public class MidiPlayer implements MetaEventListener {
	
	/** Midi meta event */
	public static final int END_OF_TRACK_MESSAGE = 47;
	
	private Sequencer sequencer;
	private boolean loop;
	private boolean paused;
	
	/**
	 * Creates a new MidiPlayer object
	 *
	 */
	public MidiPlayer() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addMetaEventListener(this);
		}
		catch (MidiUnavailableException e){
			sequencer = null;
		}
	}
	
	/**
	 * Loads a sequence from the file system. Returns null if an error occurs
	 * @param filename The filename of the MIDI file
	 * @return A Sequence object corresponding to this MIDI file
	 */
	public Sequence getSequence (String filename) {
		try {
			return MidiSystem.getSequence(new File(filename));
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Plays a sequence, optionally looping. This method returns immediately. The sequence is
	 * not played if it is invalid
	 * @param sequence The Sequence to play
	 * @param loop Set to true to loop forever, false to terminate after the end of the sequence
	 */
	public void play(Sequence sequence, boolean loop) {
		if (sequencer != null && sequence != null) {
			try {
				sequencer.setSequence(sequence);
				sequencer.start();
				System.out.println("Playing MIDI");
				this.loop = loop;
			}
			catch (InvalidMidiDataException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method is called by the sound system when a meta event occurs. In this case,
	 * when the end-of-track meta event is received, the sequence is restarted if looping is on.
	 */
	public void meta(MetaMessage event) {
		if (event.getType() == END_OF_TRACK_MESSAGE) {
			if (sequencer != null && sequencer.isOpen() && loop) {
				sequencer.start();
			}
		}
	}
	
	/**
	 * Stops the sequencer and resets its position to 0
	 *
	 */
	public void stop() {
		if (sequencer != null && sequencer.isOpen()) {
			sequencer.stop();
			sequencer.setMicrosecondPosition(0);
		}
	}
	
	/**
	 * Closes the sequencer
	 *
	 */
	public void close() {
		if (sequencer != null && sequencer.isOpen()) {
			sequencer.close();
		}
	}
	
	/**
	 * Gets the sequencer
	 * @return The Sequencer object
	 */
	public Sequencer getSequencer() {
		return sequencer;
	}
	
	/**
	 * Sets the paused state. Music may not immediately pause
	 * @param paused Set to true to pause the sequencer, false to restart
	 */
	public void setPaused(boolean paused) {
		if (this.paused != paused && sequencer != null) {
			this.paused = paused;
			if (paused) 
				sequencer.stop();
			else
				sequencer.start();
		}
	}
	
	/**
	 * Gets the paused state
	 * @return true if the sequencer is paused, false otherwise
	 */
	public boolean isPaused() {
		return paused;
	}
	
}
