package Code.customClasses.DancingFinch;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.awt.Color;
import java.util.Random;

/**
 * The DancingFinch is a controller for the finch which allows the finch to move to
 * a certain beat. It uses a simple state machine which stores the last motion, and
 * based on whether the finch is moving clockwise or counterclockwise, determines its
 * next motion. The finch also changes to a random color after every motion.
 * 
 * The DancingFinch provides methods to check if the finch has been tapped sharply,
 * and a method which reverses the direction of the finch.
 * 
 * @author Celestine Lau
 *
 */
public class DancingFinch {
	
	/** The current state of motion of the finch */
	private int state;
	/** The Finch object to control */
	private Finch finch;
	/** The direction the finch will "dance" in */
	private boolean clockwise;
	/** The distance the finch moves on every step, this is related to how often it
	 * has to move
	 */
	private double distance;
	/** The current color of the finch */
	private int color;
	
	private static Random rng = new Random();
	/* Constants defining the state of the motion */
	private static final int STOP = 0;
	private static final int STRAIGHT = 1;
	private static final int TURN_LEFT = 2;
	private static final int TURN_RIGHT = 3;
	private static final int BACK = 4;
	
	// Calibrates to gravity
	private double gravityOffset;
	
	/** The next direction to move, if the finch is moving clockwise */
	private static final int[] clockwiseTransition = new int[] {
		STRAIGHT, TURN_RIGHT, BACK, STRAIGHT, TURN_RIGHT
	};
	
	/** The next direction to move, if the finch is moving counterclockwise */
	private static final int[] counterclockwiseTransition = new int[] {
		BACK, TURN_LEFT, STRAIGHT, BACK, TURN_LEFT
	};
	
	/** The array of random colors that the finch can take on */
	private Color[] nextColor = new Color[] {
		Color.red, Color.green, Color.blue, Color.cyan, Color.magenta, Color.yellow, Color.pink, Color.white
	};
	
	/**
	 * Creates a new DancingFinch object
	 * @param finch The finch object  
	 * @throws NullPointerException if finch is null
	 */
	public DancingFinch (Finch finch){
		this.finch = finch;
		state = STOP;
		clockwise = true;
		distance = 15;
		color = 0;
		gravityOffset = finch.getZAcceleration();
	}
	
	/**
	 * Causes the dancingFinch to execute its next move
	 *
	 */
	public void nextMove() {
		if (clockwise)
			state = clockwiseTransition[state];
		else 
			state = counterclockwiseTransition[state];
		
		changeColor();
		execute(state);
	}
	
	/**
	 * Causes the dancingFinch to change to a different random color
	 * within the nextColor array
	 */
	private void changeColor(){
		color += rng.nextInt(nextColor.length - 1) + 1;
		if (color >= nextColor.length) color -= nextColor.length;
		finch.setLED(nextColor[color]);
	}
	
	/**
	 * Executes the next motion of the finch, based on the state
	 * @param state The motion to execute. This can be one of STRAIGHT, TURN_LEFT,
	 * TURN_RIGHT or BACK
	 */
	private void execute(int state) {
		switch (state) {
		case STRAIGHT:
			finch.setWheelVelocities(200,200);
			break;
		case TURN_LEFT:
			finch.setWheelVelocities(-100, 100);
			break;
		case TURN_RIGHT:
			finch.setWheelVelocities(100, -100);
			break;
		case BACK:
			finch.setWheelVelocities(-200, -200);
		default:
			break;
		}
		
	}
	
	/**
	 * Sets the array of random colors that the finch can change to
	 * @param colors The array of colors from which random colors are drawn
	 */
	public void setColors(Color[] colors){
		if (colors == null || colors.length == 0) {
			throw new IllegalArgumentException ("At least one color must be specified.");
		}
		this.nextColor = (Color[]) colors.clone();
		color = 0;
	}
	
	/**
	 * Sets the direction of the DancingFinch
	 * @param clockwise Set to true to make the finch go clockwise, false to make it go
	 * counterclockwise
	 */
	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}
	
	/**
	 * Sets the time interval (in ms) between every motion. The distance the finch will move 
	 * will be determined from this value 
	 * @param interval The time between each successive call of nextMove(), in milliseconds
	 */
	public void setInterval(int interval) {
		distance = 0.0075 * interval;
	}
	
	/**
	 * Reverses the direction of the DancingFinch
	 *
	 */
	public void reverse() {
		clockwise = !clockwise;
	}
	
	/**
	 * Check the z-accelerometers to determine if the user has tapped the finch
	 * @return true if the Finch has been tapped, false otherwise
	 */
	public boolean isTapped() {
		double value = ((finch.getZAcceleration()-gravityOffset));
		return value < -0.3;
	}
}
