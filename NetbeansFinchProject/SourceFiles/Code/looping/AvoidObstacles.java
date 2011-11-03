/*
 * AvoidObstacles.java
 * This program just uses the obstacle sensors to avoid hitting obstacles.
 * Author:  Tom Lauwers
 */

package Code.looping;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.awt.Color;

/**
 *
 * @author tlauwers
 */
public class AvoidObstacles {
   public static void main(final String[] args)
   {
	  // Instantiating the Finch object
      Finch myFinch = new Finch();

      // Run so long as the Finch is not pointed beak down
      while(!myFinch.isBeakDown()) {
          // If there's an obstacle on the left, turn LED red, back up for 750 ms
          // and turn for 500 ms
        if(myFinch.isObstacleLeftSide()) {
            myFinch.setLED(255,0,0);
            myFinch.setWheelVelocities(-255,-255,750);
            myFinch.setWheelVelocities(100,-255, 500);
            myFinch.buzz(440, 500);
        }
        // If there's an obstacle on the right, set LED blue, back up for 750 ms
        // and turn for 500 ms
        else if(myFinch.isObstacleRightSide()) {
            myFinch.setLED(0,0,255);
            myFinch.setWheelVelocities(-255,-255,750);
            myFinch.setWheelVelocities(-255, 100, 500);
            
            myFinch.buzz(880, 500);
        }
        // Else, robot goes straight, LED is green
        else {
            myFinch.setWheelVelocities(255,255);
            myFinch.setLED(0,255,0);
        }
       }
           // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
    }

}
