package Code.rss;

/**
 * Created by: Tom Lauwers
 * Date: 2/16/2009
 * Have the Finch respond to the most recent earthquake that occured in the world.  Most 
 * earthquakes in this database have magnitudes of 5 to 6, and usually there's a new one about once/hour.
 */

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import edu.cmu.ri.createlab.rss.readers.EarthquakeReader;

public class EarthquakeFinch
{

   public static void main(final String[] args)
   {
	  // Instantiating the Finch object
      Finch myFinch = new Finch();

      // Instantiating the earthquake reader
      EarthquakeReader reader = new EarthquakeReader();
      
      // Reading in the magnitude of the most recent earthquake
      double magnitude = reader.getMagnitude();
      
      // Have the Finch say what the most recent earthquake was, print it out so you can also read it
      myFinch.saySomething("An earthquake of magnitude " + magnitude + " just struck " + reader.getLocation());
      System.out.println("An earthquake of magnitude " + magnitude + " just struck " + reader.getLocation());
      // set the LED so that lower magnitudes tend towards green and higher ones towards red
      myFinch.setLED((int)((magnitude-4)*50), 250 - (int)((magnitude-4)*50),0);
      
      // have the Finch move back and forth at a speed based on the magnitude
      for(int i = 0; i < 6; i++)
      {
      	myFinch.setWheelVelocities((int)(magnitude*25), (int)(magnitude*25));
      	myFinch.sleep(700);
      	
      	myFinch.setWheelVelocities((int)(-magnitude*25), (int)(-magnitude*25));
      	myFinch.sleep(700);
      }

      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
    }
}

