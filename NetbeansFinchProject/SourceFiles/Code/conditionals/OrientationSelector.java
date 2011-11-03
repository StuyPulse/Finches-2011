/*
 * A simple example showing the use of variables, sensing, and if statements (including nesting)
 * Author:  Tom Lauwers
 */

package Code.conditionals;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;


public class OrientationSelector {

   public static void main(final String[] args)
   {
	  // Instantiating the Finch object
      Finch myFinch = new Finch();

      // If the Finch's beak is pointed up, turn the LED white and play a couple tones
      if(myFinch.isBeakUp()) {
          myFinch.saySomething("I'm flying, whee!  Time to sing");
          myFinch.setLED(255,255,255);
          myFinch.sleep(3000);
          myFinch.playTone(440, 1000);
          myFinch.playTone(880, 500);
      }

      // If the Finch wheels are on the ground, do a dance
      else if(myFinch.isFinchLevel()) {
          myFinch.saySomething("My wheels are on the ground, time to dance!");
          myFinch.sleep(5000);
          myFinch.setLED(0, 255, 0);
          myFinch.setWheelVelocities(255,255,1000);
          myFinch.setLED(255, 255, 0);
          myFinch.setWheelVelocities(-180,180,500);
          myFinch.setLED(255, 0, 0);
          myFinch.setWheelVelocities(-255,-255,1000);
          myFinch.setLED(255, 0, 255);
          myFinch.setWheelVelocities(180,-180,500);
          myFinch.setLED(0, 0, 255);
          myFinch.setWheelVelocities(255,255,1000);
       }
       // If the Finch is upside down, have it ask for help and check if someone
       // helped it out (by flipping it back over)
       else if(myFinch.isFinchUpsideDown()) {
          myFinch.saySomething("Oh no, I'm flipped, please help me!");
          // set LED to red
          myFinch.setLED(255,0,0);
          myFinch.sleep(5000);
          if(myFinch.isBeakUp() || myFinch.isFinchLevel()) {
              myFinch.setLED(0,255,0);
              myFinch.saySomething("Thanks for helping me");
          }
          else {
              myFinch.saySomething("No one helped me, I'm so sad");
          }
          myFinch.sleep(5000);
        }
        // In case none of the conditions were met, say sos
        else {
          myFinch.saySomething("Either one of my wings is down or I'm pointed at the floor");
          myFinch.sleep(6000);
        }
      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
    }
}
