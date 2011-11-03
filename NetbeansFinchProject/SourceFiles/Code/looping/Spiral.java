package Code.looping;

/**
 * Created by: Tom Lauwers
 * Date: 2/20/2009
 * Simple program to have the Finch do a spiral out and back in
 */

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

public class Spiral
{
   public static void main(final String[] args)
   {
	  // Instantiating the Finch object
      Finch myFinch = new Finch();


      // Executing the spiral in and out, and also setting the LED to go from blue to red
	  for(int i = 0; i < 255; i+=5)  {
                // Set LED to start at blue and fade to red
	  	myFinch.setLED(i, 0, 255-i);
                // Set wheel velocities to run at a given speed for 400 ms
	  	myFinch.setWheelVelocities(i, 255-i, 400);
	  }

      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
    }
}

