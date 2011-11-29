package Code;
// Needs a package declaration to move to another folder

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 * Created by:
 * Date:
 * A starter file to use the Finch
 */

public class Archie1
   {
   public static void main(final String[] args)
      {
      // Instantiating the Finch object
      Finch myFinch = new Finch();

      // Write some code here!
      int sides=4;
      int fullturn=1135;
      //fullturn stands for coefficient of rotation
      //these values are for the expression representing the time spent turning
      for(int n=0; n<sides; n++){
        myFinch.setWheelVelocities(255, -255, (int)(fullturn/sides));
        myFinch.setWheelVelocities(255, 245,/*this is to compensate for the
         fact that the right motor is faster than the left motor*/ 500);
        //This moves forward
    }

    
      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
      }
   }

/*11/29/11 */