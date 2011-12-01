package Code;
// Needs a package declaration to move to another folder

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.awt.Color;

/**
 * Created by:
 * Date:
 * A starter file to use the Finch
 */
public class carl {

    public static void main(final String[] args) {
        // Instantiating the Finch object
        Finch myFinch = new Finch();
        //square(myFinch, 800);

        face(myFinch);


        // Always end your program with finch.quit()
        myFinch.quit();
        System.exit(0);
    }


    
    public static void face(Finch f) {
        while (true) {

            if (f.isTapped()) {
                f.setLED(Color.red);
                f.setWheelVelocities(-255, -255, 1000);
                f.setWheelVelocities(-100, 255, 300);
            }
            else {
                f.setLED(Color.BLACK);
                f.setWheelVelocities(255, 255);
            }
        }
    }
    
    public static void square(Finch f, int time) {
        for (int i = 0; i < 4; i++) {
            f.setWheelVelocities(255, -255, 420);
            f.setWheelVelocities(255, 255, time);
        }
    }
}

