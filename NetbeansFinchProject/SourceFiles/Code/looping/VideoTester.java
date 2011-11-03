package Code.looping;

/*
 * VideoTester.java - a simple example using video.  The program starts up the video
 * window, and then in a loop it updates the image in the display, and gets the average
 * color of the center rectangle of the image.  It draws a rectangle in the center of the
 * image and sets it to the average color of that area.
 *
 * Author:  Tom Lauwers
 */

import java.awt.Color;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import edu.cmu.ri.createlab.video.VideoHelper;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public class VideoTester
   {
   public static void main(final String[] args)
      {
      Finch myFinch = new Finch();
      VideoHelper videoHelper = new VideoHelper();

      System.out.println("Finch connecting");

      // Initializing the video
      videoHelper.initVideo();
      System.out.println("Init video");

      // Display the video screen
      videoHelper.showVideoScreen("I'm on TV");
      System.out.println("Video drawing");

      // Start by drawing a circle in the center of the image
      videoHelper.setPolygonColor(Color.MAGENTA);
      videoHelper.drawCircle(30, 160, 120);

      // Continue doing this as long as the left light sensor is above 80
      while (myFinch.getLeftLightSensor() > 80)
         {

         // Update the video screen with the most recent image
         videoHelper.updateVideoScreen();

         // Get the average color value of the rectangle bounded by (100,70) and (220, 170)
         final Color areaColor = videoHelper.getAreaColor(videoHelper.getImageWidth() / 2 - 20, videoHelper.getImageHeight() / 2 - 20, videoHelper.getImageWidth() / 2 + 20, videoHelper.getImageHeight() / 2 + 20);

         // Print out the color
         System.out.println("Color is " + areaColor);

         // If the right light sensor is greater than 180, draw a circle
         if (myFinch.getRightLightSensor() > 180)
            {
            videoHelper.drawCircle(50, 160, 120);
            // Set the circle color to Magenta
            videoHelper.setPolygonColor(Color.MAGENTA);
            // Set the circle to be an outline
            videoHelper.setFillPolygon(false);
            }

         // Else draw a filled in rectangle and set its color to the average color of the
         // image in the rectangle.
         else
            {
            videoHelper.drawRectangle(videoHelper.getImageWidth() - 50, videoHelper.getImageHeight() - 80, videoHelper.getImageWidth() - 1, videoHelper.getImageHeight() - 1);
            videoHelper.setPolygonColor(areaColor);
            videoHelper.setFillPolygon(true);
            }
         }
      // Close the video screen and disconnect from the Finch
      videoHelper.closeVideoScreen();
      videoHelper.closeVideo();
      myFinch.quit();
      System.exit(0);
      }

   private VideoTester()
      {
      // private to prevent instantiation
      }
   }
