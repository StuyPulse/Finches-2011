package Code.looping;

/**
 * Created by: Zhiquan Yeo, Meng Yee Chuah
 * Date: Feb 15, 2009
 * AfraidFinch: Poor Finch-y gets picked on a lot. In fact, he's become
 * kind of a coward and runs away. He turns and runs based on the direction
 * that he was hit, but this program could be improved and the Finch doesn't
 * always turn in the right direction.
 *
 * It uses Z-axis acceleration to determine if it was hit and X and Y acceleration
 * to determine where it was hit from.
 */

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

import java.awt.Color;

public class AfraidFinch
   {

   public static void main(final String[] args)
      {
      // Instantiating the Finch object
      Finch myFinch = new Finch();

      // Initial vertical acceleration (Z-Axis) reading 
      double standstillValue = 0.0;

      // Offset from 1G
      double offsetZ = 0.0;

      // Offset from 0G
      double offsetY = 0.0;

      // Offset from 0G
      double offsetX = 0.0;

      // The current accelerometer readings
      double currentZ = 0.0;
      double currentY = 0.0;
      double currentX = 0.0;

      System.out.println("Connected to Finch");
      System.out.println("Calibrating accelerometers...");

      // Get the acceleration and calculate offset from 1G
      // Subtracting offset from accelerometer readings should
      // give around 0 for x and y and 1 for z when the finch
      // is not moving
      offsetZ = myFinch.getZAcceleration();
      offsetY = myFinch.getYAcceleration();
      offsetX = myFinch.getXAcceleration();
      System.out.println("StandStillValue = " + standstillValue);
      System.out.println("offset = " + offsetZ);

	  myFinch.showAccelerometerGraph();

      // Main program loop, goes unless Finch is placed vertical
      while (!myFinch.isBeakUp())
         {
         // Get the current accelerometer reading
         // Also need to subtract the offset and 1.0G, to get the change in readings
         currentZ = myFinch.getZAcceleration() - offsetZ;
         currentY = myFinch.getYAcceleration() - offsetY;
         currentX = myFinch.getXAcceleration() - offsetX;

         myFinch.updateAccelerometerGraph(currentX, currentY, currentZ);


            //Measure which side a knock came from. If its positive,
            //it means we're heading left, which indicates we got hit from the right
            // Only do this if our Y acceleration is greater than the magnitude of the X acceleration
            // so we don't mistake a front hit for a side hit
            if (currentY > 0.3 && currentY > Math.abs(currentX))
               {
               System.out.println("Right Hit");
               myFinch.setLED(Color.RED);
               myFinch.setWheelVelocities(-100, 100,1000);
               myFinch.setWheelVelocities(150, 150, 1500);
               // Sleep for a while so that movement vibrations don't trigger a 'hit'
               myFinch.sleep(1000);
               }
            //Otherwise, we got are moving right, which indicates we got hit from the left
            else if (currentY < -0.3 && currentY < -Math.abs(currentX))
               {
               System.out.println("Left Hit");
               myFinch.setLED(Color.BLUE);
               myFinch.setWheelVelocities(100, -100,1000);
               myFinch.setWheelVelocities(150, 150,1500);
               // Sleep for a while so that movement vibrations don't trigger a 'hit'
               myFinch.sleep(1000);
               }
            //Otherwise, if we don't get any significant Y axis movement, we got hit from either
            //the front or back
            else if (currentX < -0.3)
               {
               System.out.println("Forward Hit");
               //Turn ourselves around and hightail it out of here
               myFinch.setWheelVelocities(-200, 200,800);
               myFinch.setWheelVelocities(150, 150, 1500);
               // Sleep for a while so that movement vibrations don't trigger a 'hit'
               myFinch.sleep(1000);
               }
            else if (currentX > 0.3)
               {
               System.out.println("Backward Hit");
               // Just run!
               myFinch.setWheelVelocities(150, 150, 1500);
               // Sleep for a while so that movement vibrations don't trigger a 'hit'
               myFinch.sleep(1000);
               }
              // No hits, so keep the LED green
            else {
            	myFinch.setLED(0,255,0);
            }
         }
         myFinch.quit();
         System.exit(0);
      }
   }

