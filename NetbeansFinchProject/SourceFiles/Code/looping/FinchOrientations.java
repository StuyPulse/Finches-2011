package Code.looping;
/*
 * FinchOrientations.java
 * This program just tells you the current orientation of the Finch
 * Author:  Tom Lauwers
 */
import java.awt.Color;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

public class FinchOrientations
{

   public static void main(final String[] args)
   {
	  // Instantiating the Finch object
      Finch myFinch = new Finch();

      // Loop the program while the left light sensor value is above 20
      while(myFinch.getLeftLightSensor() > 20) {
    	  if(myFinch.isFinchUpsideDown()) {
    		  System.out.println("Upside Down");
    		  myFinch.setLED(Color.BLUE);
    	  }
    	  if(myFinch.isLeftWingDown()) {
    		  System.out.println("Left Wing Down");
    		  myFinch.setLED(Color.RED);
    	  }
    	  if(myFinch.isRightWingDown()) {
    		  System.out.println("Right Wing Down");
    		  myFinch.setLED(Color.GREEN);
    	  }
    	  if(myFinch.isFinchLevel()) {
    		  System.out.println("Finch Level");
    		  myFinch.setLED(Color.YELLOW);
    	  }
    	  if(myFinch.isBeakUp()) {
    		  System.out.println("Beak Up");
    		  myFinch.setLED(Color.WHITE);
    	  }
    	  if(myFinch.isBeakDown()) {
    		  System.out.println("Beak Down");
    		  myFinch.setLED(Color.BLACK);
    	  }
    	  
    	  
      }


      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
    }
}

