package Code.looping;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.awt.Color;

/*
 * Created by: Tom Lauwers
  Date:  11/22/2010
 * This program uses two Finches - the accelerometer from one is fed to the wheel velocities of the other.
 */

public class FinchControlsFinch
   {
   public static void main(final String[] args)
      {
      // Instantiating the Finch objects
      Finch controlFinch = new Finch();
      Finch finchFollower = new Finch();

      // Set their LEDs so we know which is which
      controlFinch.setLED(Color.blue);
      finchFollower.setLED(Color.green);

      // The program continues until the Finch that is driving around detects its beak is up
      while(!finchFollower.isBeakUp()) {
          // Get the X and Y acceleration from the control Finch
          double xAccel = controlFinch.getXAcceleration();
          double yAccel = controlFinch.getYAcceleration();

          // Set the wheel speeds to be some combination of x and y acceleration
          int leftSpeed = (int)(xAccel*255 - yAccel*255);
          int rightSpeed = (int)(xAccel*255 + yAccel*255);

          // Keep the velocities in range

          if(leftSpeed > 255) {
              leftSpeed = 255;
          }
          else if (leftSpeed < -255) {
              leftSpeed = -255;
          }
          if(rightSpeed > 255) {
              rightSpeed = 255;
          }
          else if (rightSpeed < -255) {
              rightSpeed = -255;
          }

          // Set wheel velocities
          finchFollower.setWheelVelocities(leftSpeed, rightSpeed);

       }
      // Always end your program with finch.quit()
      controlFinch.quit();
      finchFollower.quit();
      System.exit(0);
      }
   }

