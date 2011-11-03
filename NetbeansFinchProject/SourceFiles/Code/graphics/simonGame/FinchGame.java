package Code.graphics.simonGame;

/**
 * @author Lisa Storey
 * @author Joey Gannon
 * @date 2/14/09
 * 
 * A fast paced 'Simon Says' game for the Finch.
 * 
 * Instructions: An arrow will display on the screen representing a command. 
 * If the arrow is pointing left or right, turn the Finch on its left or right 
 * side. If the arrow points up, point the Finch straight up. If the arrow 
 * points down, hold the Finch level. If the arrow is black, cover the Finch's light
 * sensors with your hand. If it is gray, uncover them. Respond quickly!
 */

import java.awt.Color;
import java.util.Random;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

public class FinchGame
{
	// Commanded Finch states
	public static final int FLAT_COVERED = 0;
	public static final int LEFT_COVERED = 1;
	public static final int RIGHT_COVERED = 2;
	public static final int UP_COVERED = 3;
	public static final int FLAT_UNCOVERED = 4;
	public static final int LEFT_UNCOVERED = 5;
	public static final int RIGHT_UNCOVERED = 6;
	public static final int UP_UNCOVERED = 7;
	
	private static Finch myFinch;
	private int[] lightBias;  // Light sensor calibration values
	
	private static final int RESPONSE_TIME = 2500; // amount of time to follow the command




	public FinchGame()
	{
		myFinch = new Finch();
		System.out.println("Instructions - point the Finch in the direction of the arrow.  If the arrow is black, cover the light sensors, if gray, don't cover them");
                System.out.println("Up arrow - Finch pointing with beak up");
		System.out.println("Left arrow - Finch left wing down");
                System.out.println("Right arrow - Finch right wing down");
                System.out.println("Down arrow - Finch LEVEL");
                System.out.println("Calibrating to light levels.");

                lightBias = myFinch.getLightSensors(); // get light level readings
		
		// Make the covered light values about 40 less than uncovered
		lightBias[0] -= 40;
		lightBias[1] -= 40;
		
		// Ensure they don't get too low
		if(lightBias[0] < 30)
			lightBias[0] = 30;
		if(lightBias[1] < 30)
			lightBias[1] = 30;
		
		System.out.println("Done calibrating!");
		System.out.println("Pick up the Finch now");
		myFinch.saySomething("Pick up the Finch now");
		myFinch.sleep(3000);
	}

	public static void main(final String[] args) throws InterruptedException
	{
		FinchGame finchGame = new FinchGame();
		Random random = new Random();
		long startTime; // ms system time when this iteration was started
		boolean didIt; // flag for whether or not the action was completed in time

                // Create a new GUI window
		FinchGameWindow window = new FinchGameWindow();
		window.repaint();

		int winCount = 0;

		for(int i = 0; i < 10; i++)
		{
			/* For each iteration, choose a random command,
			 * turn the beak red until the command is completed,
			 * and turn the beak green once it has been completed.
			 * If the command was not done in time, beep.
			 */
			
			finchGame.getFinch().setLED(Color.RED); // turn the beak red until the command is completed
			startTime = System.currentTimeMillis(); // set the trial start time
			didIt = false;
			int currentArrow = random.nextInt(8); // choose a random command type
			window.setArrow(currentArrow); // inform the display window of the chosen command and display it
			switch (currentArrow)
			{
                            case FLAT_COVERED:
                                while(System.currentTimeMillis() - startTime < RESPONSE_TIME)
                                {
                                        if(myFinch.isFinchLevel() && finchGame.isCovered())
                                        {
                                                finchGame.getFinch().setLED(Color.GREEN);
                                                didIt = true;
                                        }
                                }
                                break;
                            case LEFT_COVERED:
				while(System.currentTimeMillis() - startTime < RESPONSE_TIME)
                                {
                                        if(myFinch.isLeftWingDown() && finchGame.isCovered())
                                        {
                                                finchGame.getFinch().setLED(Color.GREEN);
                                                didIt = true;
                                        }
                                }
                                break;
                            case RIGHT_COVERED:
                                while(System.currentTimeMillis() - startTime < RESPONSE_TIME)
                                {
                                        if(myFinch.isRightWingDown() && finchGame.isCovered()){
                                                finchGame.getFinch().setLED(Color.GREEN);
                                                didIt = true;
                                        }
                                }
                                break;
                            case UP_COVERED:
                                while(System.currentTimeMillis() - startTime < RESPONSE_TIME)
                                {
                                        if(myFinch.isBeakUp() && finchGame.isCovered()){
                                                finchGame.getFinch().setLED(Color.GREEN);
                                                didIt = true;
                                        }
                                }
                                break;
                            case FLAT_UNCOVERED:
                                while(System.currentTimeMillis() - startTime < RESPONSE_TIME)
                                {
                                        if(myFinch.isFinchLevel() && !finchGame.isCovered())
                                        {
                                                finchGame.getFinch().setLED(Color.GREEN);
                                                didIt = true;
                                        }
                                }
                                break;
                            case LEFT_UNCOVERED:
                                while(System.currentTimeMillis() - startTime < RESPONSE_TIME)
                                {
                                        if(myFinch.isLeftWingDown() && !finchGame.isCovered())
                                        {
                                                finchGame.getFinch().setLED(Color.GREEN);
                                                didIt = true;
                                        }
                                }
                                break;
                            case RIGHT_UNCOVERED:
                                while(System.currentTimeMillis() - startTime < RESPONSE_TIME)
                                {
                                        if(myFinch.isRightWingDown() && !finchGame.isCovered()){
                                                finchGame.getFinch().setLED(Color.GREEN);
                                                didIt = true;
                                        }
                                }
                                break;
                            case UP_UNCOVERED:
                                while(System.currentTimeMillis() - startTime < RESPONSE_TIME)
                                {
                                        if(myFinch.isBeakUp() && !finchGame.isCovered()){
                                                finchGame.getFinch().setLED(Color.GREEN);
                                                didIt = true;
                                        }
                                }
                                break;
                        }
			
			if(!didIt) {
                            myFinch.saySomething("Missed one");
                            finchGame.getFinch().buzz(1000, 200);
                        } // beep if incorrect
			
			// If you succeeded, increase win count by 1
			if(didIt) {
                            myFinch.saySomething("Got it!");
                            winCount++;
			}

		}
		// Say the result - can definitely improve this part to have the Finch congratulate or insult you
		// depending on score.
		myFinch.saySomething("You got " + winCount + " out of 10 tries.");
		System.out.println("You got " + winCount + " out of 10 tries.");
		myFinch.sleep(5000);
		System.exit(0);
		
	}

	private Finch getFinch()
	{
		return myFinch;
	}

	/* These methods check if a given game state is satisfied. */
	
	public boolean isCovered()
	{
		return (myFinch.getLeftLightSensor() < (lightBias[0]) && myFinch.getRightLightSensor() < (lightBias[1]));
	}

}

