package Code;
// Needs a package declaration to move to another folder

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 * Created by:
 * Date:
 * A starter file to use the Finch
 */

public class TeamLarry

   {
   public static void main(final String[] args)
      {
      //nstantiating the Finch object
      Finch myFinch = new Finch();
      TeamLarry teamLarry = new TeamLarry();
      
     //teamLarry.darwin(myFinch, 5);
     teamLarry.polygon(myFinch, 3);
      
      
      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
      }

   public void polygon(Finch myFinch, int sides){
       int speed = 150;
       int time = 500;
       int timeTurn = 1900/sides;

       for(int i = 0; i < sides; i++){
        myFinch.setWheelVelocities(speed, speed, time);
        myFinch.setWheelVelocities(speed, -speed, timeTurn);

       }
    }
   }
   /*
   public void darwin(Finch myFinch, int loop){
        for(int c = 0; c < loop; c++){
            int speed = 255;
            myFinch.setWheelVelocities(speed, speed, 500);
            myFinch.sleep(500);
        }
        
   
   }
   
   
   public void straight(Finch myFinch){
       myFinch.setWheelVelocities(255, 255, 1000);
   }
   
   public void turnRight(Finch myFinch){
    myFinch.setWheelVelocities(255, 0, 1000);
             }
public void square(Finch myFinch){
    int sides = 4;
    while(sides > 0){
        straight(myFinch);
        turnRight(myFinch);
        sides--;
    }
}  
   }
    *
    */


