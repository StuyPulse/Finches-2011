package Code.arrays;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import edu.cmu.ri.createlab.video.VideoHelper;

/*
 * VideoTracker.java - Tracks an object using the camera.  The program exits when the
 * object is moved completely off-screen.
 */

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public class VideoTracker
   {
   public static void main(final String[] args)
      {
      // Instantiating the Finch object
      final Finch myFinch = new Finch();
      System.out.println("Finch connecting");

      // Initializing the video
      final VideoHelper videoHelper = new VideoHelper();
      videoHelper.initVideo();
      System.out.println("Init video");

      // Showing the video screen
      videoHelper.showVideoScreen("Look mom, I'm on TV!");
      System.out.println("Video drawing");

      // Calibrating to an object to track later
      System.out.println("Put something in the center of the Finch video for Finch to track");
      final int[] calibrationVals = videoHelper.blobCalibration();

      // Printing out the RGB calibration values
      System.out.println("Calibration R" + calibrationVals[0] + " G" + calibrationVals[1] + " B" + calibrationVals[2]);

      // Loop through this unless your left light sensor is less than 50.
      while (myFinch.getLeftLightSensor() > 50)
         {
         // Update the video window and image data
         videoHelper.updateVideoScreen();

         // Get the center and edges of the blob
         final int[] blobCornersAndCenter = videoHelper.blobDetector(calibrationVals, 15);

         // If a blob was detected, draw a rectangle where it was detected and print out its coordinates
         if (blobCornersAndCenter != null)
            {
            System.out.println("Center: " + blobCornersAndCenter[0] + "," + blobCornersAndCenter[1] + " Min x,y: " + blobCornersAndCenter[2] + "," + blobCornersAndCenter[4] + " Max x,y " + blobCornersAndCenter[3] + "," + blobCornersAndCenter[5]);
            videoHelper.drawRectangle(blobCornersAndCenter[2], blobCornersAndCenter[4], blobCornersAndCenter[3], blobCornersAndCenter[5]);
            // myFinch.setPolygonColor(myFinch.getAreaColor(blobCornersAndCenter[2], blobCornersAndCenter[4], blobCornersAndCenter[3], blobCornersAndCenter[5]));
            }

         // If no blob was detected break out of the loop
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

   private VideoTracker()
      {
      // private to prevent instantiation
      }
   }

