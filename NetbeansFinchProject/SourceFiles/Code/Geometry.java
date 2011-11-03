package Code;
// Needs a package declaration to move to another folder

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 * Created by:
 * Date:
 * A starter file to use the Finch
 */

public class Geometry
   {
   public static void main(final String[] args)
      {
      // Instantiating the Finch object
      Finch myFinch = new Finch();

      int lasers=125;
      int time=500;
      int loop=5;

      
      myFinch.setWheelVelocities (lasers, -lasers, time);
      myFinch.sleep (1000);

      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
      }
   }

