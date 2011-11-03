package Code.arrays;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import edu.cmu.ri.createlab.video.VideoHelper;

/*
* ControlWithVideo.java - this program controls how the Finch moves using the camera.  The camera
* will look for an object, and if it is on the left side of the image, the Finch will turn left,
* while on the right side it will turn right.  The larger the object, the faster the Finch will drive.
*
* Author:  Tom Lauwers
*/

public class ControlWithVideo
   {
   public static void main(final String[] args)
      {
      // Instantiating the Finch object
      final Finch myFinch = new Finch();

      // Instantiating the object that gives us webcam access
      final VideoHelper videoHelper = new VideoHelper();

      // Initializing the camera
      videoHelper.initVideo();

      // Showing the camera image and label it
      videoHelper.showVideoScreen("Move the ball to control the Finch");

      // Calibrating to a given object
      System.out.println("Put something in the center of the Finch video for Finch to track");
      final int[] calibrationVals = videoHelper.blobCalibration();

      // Printing out the RGB values of the object it calibrated to
      System.out.println("Calibration R" + calibrationVals[0] + " G" + calibrationVals[1] + " B" + calibrationVals[2]);

      // Continue program so long as the left light sensor is more than 50
      while (myFinch.getLeftLightSensor() > 50)
         {
         // Update the video window and image data with the most recent camera image
         videoHelper.updateVideoScreen();

         // Get the center and edges of the blob
         final int[] blobCornersAndCenter = videoHelper.blobDetector(calibrationVals, 15);

         if (blobCornersAndCenter != null)
            {
            // Determine the magnitude of the velocity by the area of the blob
            final int magnitude = (blobCornersAndCenter[3] - blobCornersAndCenter[2]) * (blobCornersAndCenter[5] - blobCornersAndCenter[4]);

            // Determine the direction by the difference between the center x coordinate and the center line
            final int direction = blobCornersAndCenter[0] - videoHelper.getImageWidth() / 2;

            // Set the left and right motor velocity based on these values
            int leftMotor = (magnitude / 100 + direction) / 5;
            int rightMotor = (magnitude / 100 - direction) / 5;

            // Print it out
            System.out.println("Mag " + magnitude + " Dir " + direction + " left " + leftMotor + " right " + rightMotor);

            // Make sure not to exceed maximum velocities
            if (leftMotor > 35)
               {
               leftMotor = 35;
               }
            if (leftMotor < -35)
               {
               leftMotor = -35;
               }
            if (rightMotor > 35)
               {
               rightMotor = 35;
               }
            if (rightMotor < -35)
               {
               rightMotor = -35;
               }

            // Set the Finch wheel velocities
            myFinch.setWheelVelocities(leftMotor, rightMotor);

            // Print out the center and edges of the blob and draw a rectangle around it
            System.out.println("Center: " + blobCornersAndCenter[0] + "," + blobCornersAndCenter[1] + " Min x,y: " + blobCornersAndCenter[2] + "," + blobCornersAndCenter[4] + " Max x,y " + blobCornersAndCenter[3] + "," + blobCornersAndCenter[5]);
            videoHelper.drawRectangle(blobCornersAndCenter[2], blobCornersAndCenter[4], blobCornersAndCenter[3], blobCornersAndCenter[5]);
            }
         // If no object is seen, break out of the loop
         else
            {
            videoHelper.drawNothing();
            break;
            }
         }

      // Close the video window and disconnect from the Finch
      videoHelper.closeVideoScreen();
      videoHelper.closeVideo();
      myFinch.quit();
      System.exit(0);
      }

   private ControlWithVideo()
      {
      // private to prevent instantiation
      }
   }
