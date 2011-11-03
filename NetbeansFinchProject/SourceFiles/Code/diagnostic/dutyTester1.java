/*
 * dutyTester1.java
 * This program stress-tests and graphs data from a single Finch
 * Author:  Tom Lauwers
 */

package Code.diagnostic;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.util.Random;
import java.lang.System;

public class dutyTester1 {

	public static void main(String[] args) {
                // Create a new Finch object and open connection to the Finch
		Finch testMonkey = new Finch();

                // Print graphs to show sensor data
		testMonkey.showAccelerometerGraph();
		testMonkey.showLightSensorGraph();
		testMonkey.showTemperatureGraph();

                // Variables to store sensor data
		double xAccel, yAccel, zAccel;
		double temperature;
		int leftLight, rightLight;
		boolean leftIR = false, rightIR = false;

                // Variable to determine if we should flip motors back and forth
		boolean flip = true;
		
		int count = 0; // General purpose counter

                // Random object
		Random random = new Random();

                // Log system time
		long beginTime = System.currentTimeMillis();
		long currentTime = 0;
		long pastTime;
		long cycleTime;

                // Do this so long as the Finch's beak is not pointing down at the floor
		while(!testMonkey.isBeakDown()) {
			count++;
                        // If count is more than 20, flip the direction of the motors, print to console the obstacle sensors, and set the LED
			if(count > 20) {
				pastTime = currentTime;
				currentTime = System.currentTimeMillis() - beginTime;
				cycleTime = currentTime-pastTime;
				count = 0;
				flip = !flip;
				testMonkey.setLED(random.nextInt(255), random.nextInt(255),random.nextInt(255));
				System.out.println("Left obstacle is " + leftIR + " Right obstacle is " + rightIR);
				System.out.println("Cycle time:  " + cycleTime + "ms.  Elapsed since program start:  " + currentTime);
			}
			if(flip) {
				testMonkey.setWheelVelocities(255,255);
			}
			else {
				testMonkey.setWheelVelocities(-255,-255);
			}
                        // Get sensor values
			xAccel= testMonkey.getXAcceleration();
			yAccel= testMonkey.getYAcceleration();
			zAccel= testMonkey.getZAcceleration();
			temperature = testMonkey.getTemperature();
			
			leftLight = testMonkey.getLeftLightSensor();
			rightLight = testMonkey.getRightLightSensor();
			
			leftIR = testMonkey.isObstacleLeftSide();
			rightIR = testMonkey.isObstacleRightSide();

                        // Update graphs with new sensor values
			testMonkey.updateAccelerometerGraph(xAccel, yAccel, zAccel);
			testMonkey.updateLightSensorGraph(leftLight, rightLight);
			testMonkey.updateTemperatureGraph(temperature);
		}
                // Close Finch connection
                testMonkey.quit();
		
	}

}
