/**
 * Created by: Tom Lauwers
 * Date:  11/22/2010
 * Simple program which asks for user input and sets the LED based on that input
 */

package Code.simpleInput;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.util.Scanner;

public class LEDSetter
   {
   public static void main(final String[] args)
      {
      // Instantiating the Finch object
      Finch myFinch = new Finch();

      // Creating a scanner to read input from the console
      Scanner sc = new Scanner(System.in);

      // Providing instructions to the user
      System.out.println("Enter the red, green, and blue intensity for the LED (values from 0 to 255)");

      // Reading in the three integers
      System.out.print("Red:  ");
      int red = sc.nextInt();
      System.out.print("Green:  ");
      int green = sc.nextInt();
      System.out.print("Blue:  ");
      int blue = sc.nextInt();

      /* Potential improvement here - check the user input to make sure that it is in range (0-255) */

      // Setting the LED
      System.out.println("Thanks, the beak will now glow for 8 seconds according to your specifications");
      myFinch.setLED(red,green,blue);
      myFinch.sleep(8000);

      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
      }
   }

