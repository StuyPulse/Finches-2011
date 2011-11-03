package Code.looping;
import java.awt.Color;
import java.util.Random;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/*
 * HearingTest.java
 * This program plays successively higher frequency tones to test the upper range
 * of hearing.
 * Author:  Tom Lauwers
 */
public class HearingTest {

	public static void main(String[] args) {
		Finch testMonkey = new Finch();
		
		int frequency = 200;
		int duration = 400;
		
		System.out.println("Place the Finch on its tail");
		testMonkey.sleep(4000);
		System.out.println("Put the Finch down when you can no longer hear the tone");

                // Do this so long as the Finch is sitting on its tail
		while(testMonkey.isBeakUp()) {
                        // Play a tone for 400 ms.  Tone frequency starts at 200 Hz
                        // and increments by 200 Hz each loop cycle
                        // Most people can hear from 15,000-20,000 Hz
			testMonkey.playTone(frequency, duration);
			frequency += 200;
			System.out.println("Frequency is " + frequency + " Hz");
			
		}
		testMonkey.quit();
		
	}

}
