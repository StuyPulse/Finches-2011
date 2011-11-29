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
      // Instantiating the Finch object
      Finch myFinch = new Finch();
      TeamLarry teamLarry = new TeamLarry();
      
      teamLarry.darwin(myFinch, 5);
      
      
      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
      }
   
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

