package Code.looping;

/*
 * LEDVideoControl.java - A program to control the Finch beak LED with camera.  The LED color is
 * simply set to the color of the central area of the image.
 *
 * Author:  Tom Lauwers
 */

import java.awt.Color;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import edu.cmu.ri.createlab.video.VideoHelper;

public class LEDVideoControl
   {
   public static void main(final String[] args)
      {
      // Instantiating the Finch object
      final Finch myFinch = new Finch();
      final VideoHelper videoHelper = new VideoHelper();

      // Initializing the video stream
      videoHelper.initVideo();

      // Showing the camera image
      videoHelper.showVideoScreen("Look mom, I'm on TV!");

      // Continue running this program so long as the Finch beak is up
      while (myFinch.isBeakUp())
         {
         // Update the video window and image data with the most recent camera image
         videoHelper.updateVideoScreen();

         // Draw a rectangle in the area to be used to set LED color
         videoHelper.drawRectangle(videoHelper.getImageWidth() / 2 - 40, videoHelper.getImageHeight() / 2 - 40, videoHelper.getImageWidth() / 2 + 40, videoHelper.getImageHeight() / 2 + 40);
         videoHelper.setPolygonColor(Color.RED);

         // Get the average color in the center area
         final Color areaColor = videoHelper.getAreaColor(videoHelper.getImageWidth() / 2 - 40, videoHelper.getImageHeight() / 2 - 40, videoHelper.getImageWidth() / 2 + 40, videoHelper.getImageHeight() / 2 + 40);

         // Set the LED to that color
         myFinch.setLED(areaColor);
         }
      // Close the video screen and disconnect from the Finch
      videoHelper.closeVideoScreen();
      videoHelper.closeVideo();
      myFinch.quit();
      System.exit(0);
      }

   private LEDVideoControl()
      {
      // private to prevent instantiation
      }
   }
