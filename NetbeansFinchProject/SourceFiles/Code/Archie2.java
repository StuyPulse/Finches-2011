package Code;
// Needs a package declaration to move to another folder

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 * Created by:
 * Date:
 * A starter file to use the Finch
 */

public class Archie2 {

   public static void main(final String[] args)
      {
      // Instantiating the Finch object
      Finch myFinch = new Finch();

      // Write some code here!

      int fullturn=1135;

while (2==2){
if (myFinch.getObstacleSensors()[0] || myFinch.getObstacleSensors()[1]){
        myFinch.setWheelVelocities(255, -255, (int)(fullturn/4));
        }
        myFinch.setWheelVelocities(255, 245);
}
      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
      }
   }

