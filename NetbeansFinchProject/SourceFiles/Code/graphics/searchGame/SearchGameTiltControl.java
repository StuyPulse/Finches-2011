package Code.graphics.searchGame;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 * @author Eric Cheek (echeek@andrew.cmu.edu)
 *
 */
final class SearchGameTiltControl
   {
   Finch myFinch;
   private int minX, minY, maxX, maxY, xPosition, yPosition;
   private double xScale = 1, yScale = 1;

   SearchGameTiltControl(Finch f)
      {
      myFinch = f;

      minX = minY = -100;
      maxX = maxY = 100;

      xPosition = 0;
      yPosition = 0;
      }

   public void setLimits(int minX, int minY, int maxX, int maxY)
      {
      this.minX = minX;
      this.minY = minY;
      this.maxX = maxX;
      this.maxY = maxY;
      }

   public void setScales(double x, double y)
      {
      xScale = x;
      yScale = y;
      }

   public void setPosition(int x, int y)
      {
      xPosition = x;
      yPosition = y;
      }

   public void centerPosition()
      {
      xPosition = (minX + maxX) / 2;
      yPosition = (minX + maxX) / 2;
      }

   public int getX()
      {
      return xPosition;
      }

   public int getY()
      {
      return yPosition;
      }

   /**
    * Fetch finch tilt and move x, y positions appropriately
    */
   public void update()
      {
      //X,Y axis switched for more intuitive control
      //note that x movement is inverted.
      int dx = (int)(-xScale * myFinch.getYAcceleration());

      int dy = (int)(yScale * myFinch.getXAcceleration());

      //update x and y positions
      xPosition += dx;
      yPosition += dy;

      //test to prevent x/y from leaving set bounds
      if (xPosition < minX)
         {
         xPosition = minX;
         }
      if (yPosition < minY)
         {
         yPosition = minY;
         }
      if (xPosition > maxX)
         {
         xPosition = maxX;
         }
      if (yPosition > maxY)
         {
         yPosition = maxY;
         }
      }
   }
