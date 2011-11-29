package Code;
// Needs a package declaration to move to another folder

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.awt.Color;

/**
 * Created by:
 * Date:
 * A starter file to use the Finch
 */
public class Geometry {

    public static void main(final String[] args) {
        // Instantiating the Finch object
        Finch myFinch = new Finch();
        Geometry myGeometry = new Geometry();
        myGeometry.square(myFinch, 9);

        // Always end your program with finch.quit()
        myFinch.quit();
        System.exit(0);
    }

    private void square(Finch myFinch, int loop) {
        int lasers = 125;
        int time = 500;
        for (int i = 0; i < loop; i++) {
            myFinch.setWheelVelocities(lasers, lasers, time);
            myFinch.setWheelVelocities(lasers, -lasers, 4*time/loop);
            if (i % 2 == 1) {
                myFinch.setLED(Color.CYAN);
            } else {
                myFinch.setLED(Color.PINK);
            }
        }
    }
}
