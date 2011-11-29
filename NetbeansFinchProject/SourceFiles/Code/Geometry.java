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
      double powerCo = .2;
      long lastTime=System.currentTimeMillis();
      long totalTime = 0;
      while(powerl<100){
          int timeChange=(int)(System.currentTimeMillis()-lastTime);
          totalTime+=timeChange;
          powerl = (int)(powerCo*totalTime);
          myFinch.setWheelVelocities(powerl,100-powerl);
          lastTime=System.currentTimeMillis();
                  System.out.println(totalTime + powerl);
          }

      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
      }
   }

