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

      // Write some code here

      int powerl = 0;
      double powerCo = .04;
      long lastTime=System.currentTimeMillis();
      while(powerl<255){
          int timeChange=(int)(System.currentTimeMillis()-lastTime);
          powerl +=powerCo*timeChange;
          myFinch.setWheelVelocities(powerl,255-powerl);
          lastTime=System.currentTimeMillis();
          }

      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
      }
   }

