/*
 * MultiFinchTester.java
 * This program stress-tests and logs data from multiple Finches at the same time.
 * It was used to run 6 Finches from one computer for 48 hours as part of a pre-production
 * stress test.
 * Author:  Tom Lauwers
 */

package Code.diagnostic;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.io.BufferedReader;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.System;

public class MultiFinchTester {

	public static void main(String[] args) {
                // Sets the number of Finches used in the test
                // Note that you must have at least this many Finches plugged in or program will hang
                int numFinches = 6;
                // Sets whether we'll stress-test motors on this run (makes accelerometer data noisier)
                boolean runMotors = true;
                // String to log Finch data to
                String fileName = "testtemp.txt";

                // Instantiate an array of Finch objects
                Finch testMonkeys[] = new Finch[numFinches];

                for(int i = 0; i < numFinches; i++) {
                    testMonkeys[i] = new Finch();
                }

                // Variables used to log sensor data
		double xAccel, yAccel, zAccel;
		double temperature;
		int leftLight, rightLight;
		boolean leftIR = false, rightIR = false;

                // Used to flip motors from drive forward to drive backward
		boolean flip = true;

		int count = 0; // general counter

		Random random = new Random();

		long beginTime = System.currentTimeMillis();
		long currentTime = 0;
		long pastTime;
		long cycleTime;

                // Open a file for writing
		try {
			FileWriter file = new FileWriter(fileName);
			BufferedWriter output = new BufferedWriter(file);

		      final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                      System.out.println("");
                      System.out.println("Press ENTER to quit.");
		while(true) {
                        // Check if enter has been pressed and if so, break out of loop
                        if(in.ready()) {
                            break;
                        }
                        // For each Finch, do the following:
                        for(int i = 0; i < numFinches; i++) {
                            // Get system time for logging and analysis of control frequency
                            pastTime = currentTime;
                            currentTime = System.currentTimeMillis() - beginTime;
                            cycleTime = currentTime-pastTime;

                            // Get accelerometer data
                            xAccel= testMonkeys[i].getXAcceleration();
                            yAccel= testMonkeys[i].getYAcceleration();
                            zAccel= testMonkeys[i].getZAcceleration();

                            // Get temperature
                            temperature = testMonkeys[i].getTemperature();

                            // Get light sensors and obstacle sensors
                            leftLight = testMonkeys[i].getLeftLightSensor();
                            rightLight = testMonkeys[i].getRightLightSensor();
                            leftIR = testMonkeys[i].isObstacleLeftSide();
                            rightIR = testMonkeys[i].isObstacleRightSide();

                            // Write sensor vals to the file, with time stamp
                            output.write("Finch " + i + "; " + cycleTime + "; " + currentTime + "; " + xAccel + "; " + yAccel + "; " + zAccel + "; " + leftLight + "; " + rightLight + "; " + temperature + "; " + (leftIR?1:0) + "; " + (rightIR?1:0) + "\n");
                        }

			count++;
                        // Every five cycles of the loop, switch the motors from forward to back or vice verca
			if(count > 5) {
				count = 0;
				flip = !flip;
                                // Set the LED to a new random color
                                int redLED = 64*random.nextInt(4);
                                int greenLED = 64*random.nextInt(4);
                                int blueLED = 64*random.nextInt(4);
                                for(int i = 0; i < numFinches; i++) {
        				testMonkeys[i].setLED(redLED, greenLED, blueLED);
                                }
                                if(runMotors) {
                                    for(int i = 0; i < numFinches; i++) {
                                        if(flip) {
                                                testMonkeys[i].setWheelVelocities(255,255);
                                        }
                                        else {
                                                testMonkeys[i].setWheelVelocities(-255,-255);
                                        }
                                    }
                                }
			}

		}
                // Close the file
		output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                // Quit all Finches
                for(int i = 0; i < numFinches; i++) {
                    testMonkeys[i].quit();
                }
	}

}
