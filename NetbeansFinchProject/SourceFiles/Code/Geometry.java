package Code;
// Needs a package declaration to move to another folder

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        /*if (!myFinch.isBeakUp()) {
        myGeometry.square(myFinch, 2);
        }*/

        boolean rightSideObstacle = false;
        boolean leftSideObstacle = false;
        while (!myFinch.isTapped()) {
            if (myFinch.isObstacleRightSide() || rightSideObstacle) {
                rightSideObstacle = true;
                myFinch.setLED(Color.RED);
            }
            myFinch.sleep(250);
            if (myFinch.isObstacleLeftSide() || leftSideObstacle) {
                leftSideObstacle = true;
                myFinch.setLED(Color.GREEN);
            }
            myFinch.sleep(250);
            myFinch.setWheelVelocities(100, 100);
            System.out.println("loop: " + myFinch.isObstacleRightSide() + myFinch.isObstacleLeftSide());
        }
        System.out.println("after loop: " + myFinch.isObstacleRightSide() + myFinch.isObstacleLeftSide());


        // Always end your program with finch.quit()
        myFinch.quit();
        System.exit(0);
    }

    private void square(Finch myFinch, int loop) {
        int lasers = 125;
        int timeForward = 1000;
        int timeTurn = 2000;
        for (int i = 0; i < loop; i++) {
            myFinch.setWheelVelocities(lasers, lasers, timeForward);
            myFinch.setWheelVelocities(lasers, -lasers, timeTurn / loop);
            if (i % 2 == 1) {
                myFinch.setLED(Color.CYAN);
            } else {
                myFinch.setLED(Color.PINK);
            }
        }
    }
}
