package Code;
// Needs a package declaration to move to another folder

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.io.*;

/**
 * Created by:
 * Date:
 * A starter file to use the Finch
 */
public class TeamLarry {

    public static void main(final String[] args) {
        // Instantiating the Finch object

        Finch myFinch = new Finch();

        // Write some code here!
        //myFinch.buzz(262,100000);

        
        myFinch.setWheelVelocities(255,255,0);
        while (myFinch.isObstacle()){
            myFinch.setWheelVelocities(255,255,1000);
        }


        // Always end your program with finch.quit()
        myFinch.quit();
        System.exit(0);
    }
}

