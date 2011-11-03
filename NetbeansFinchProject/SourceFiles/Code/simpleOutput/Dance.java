package Code.simpleOutput;

/**
 * Created by: Tom Lauwers
 * Date: 10/15/2010
 * Simple program to have the Finch do a dance
 */

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

public class Dance
{

   public static void main(final String[] args)
   {
	  // Instantiating the Finch object
      Finch myFinch = new Finch();

      myFinch.saySomething("Time to do a little dance!");
      // Set LED green, and move forward full speed for one second
      myFinch.setLED(0, 255, 0);
      myFinch.setWheelVelocities(255,255,1000);
      // Set LED yellow and turn for a half second
      myFinch.setLED(255, 255, 0);
      myFinch.setWheelVelocities(-180,180,500);
      // Set LED Red and back up for a second
      myFinch.setLED(255, 0, 0);
      myFinch.setWheelVelocities(-255,-255,1000);
      // Set LED magenta and turn for a half second
      myFinch.setLED(255, 0, 255);
      myFinch.setWheelVelocities(180,-180,500);
      // Set LED blue and move forward for a second
      myFinch.setLED(0, 0, 255);
      myFinch.setWheelVelocities(255,255,1000);

      myFinch.quit();
      System.exit(0);
    }
}

