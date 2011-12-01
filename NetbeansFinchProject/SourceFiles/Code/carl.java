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
        boolean wasTapped = false;
        int timeSinceBackedUp = 0;
        while (true) {
            if (f.isTapped() && timeSinceBackedUp > 50) {
                f.setLED(Color.red);
                f.setWheelVelocities(-255, -255, 1000);
                f.setWheelVelocities(-100, 255, 300);
                wasTapped = true;
                timeSinceBackedUp = 0;
                f.sleep(250);
            }
            else {
                if (wasTapped) {
                    f.setWheelVelocities(127, 127, 200);
                    wasTapped = false;
                }
                f.setLED(Color.BLACK);
                f.setWheelVelocities(255, 255);
            }
            timeSinceBackedUp++;
        
        }
    }
    
    public static void square(Finch f, int time) {
        for (int i = 0; i < 4; i++) {
            f.setWheelVelocities(255, -255, 420);
            f.setWheelVelocities(255, 255, time);
        }
    }
}

