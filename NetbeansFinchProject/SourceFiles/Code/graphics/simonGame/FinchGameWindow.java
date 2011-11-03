package Code.graphics.simonGame;

/**
 * @author Lisa Storey
 * @author Joey Gannon
 * @date 2/14/09
 * 
 * The display window for the Finch game. The screen will show an arrow in
 * either gray or black representing the current command. A gray arrow means tilt 
 * the Finch in the direction of the arrow, and do not cover the top of the Finch
 * (light sensors). A black arrow means tilt the finch in the direction of the arrow
 * and cover the light sensors.
 * 
 */

import javax.swing.*;
import java.awt.*;

public class FinchGameWindow extends JFrame
{
	private static final long serialVersionUID = -5183298795855931056L;
	
	private int currentArrow; // The current arrow type being displayed
	
	FinchGameWindow()
	{
		super();
		
		// set up the game display
		this.setTitle("Game!");
		this.setSize(400,420);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.getContentPane().paint(getGraphics());
		currentArrow = 0;
	}
	
	public void setArrow(int arr)
	{
		// set the new arrow and repaint the screen
		currentArrow = arr;
		this.repaint();
	}
	
	public void paint(Graphics g){
		// fill in the background in white
		g.setColor(Color.white);
		g.fillRect(0, 0, 400, 420);
		
		// display the arrow, gray for uncovered and black for covered
		g.setColor(Color.gray);
		switch(currentArrow)
		{
			case FinchGame.FLAT_COVERED:
				g.setColor(Color.black);
			case FinchGame.FLAT_UNCOVERED:
			{
				g.fillRect(180, 40, 40, 240);
				int[] xs = {120, 200, 280};
				int[] ys = {280, 360, 280};
				g.fillPolygon(xs, ys, 3);
				break;
			}
			case FinchGame.LEFT_COVERED:
				g.setColor(Color.black);
			case FinchGame.LEFT_UNCOVERED:
			{
				g.fillRect(120, 180, 240, 40);
				int[] xs = {120, 40, 120};
				int[] ys = {120, 200, 280};
				g.fillPolygon(xs, ys, 3);
				break;
			}
			case FinchGame.RIGHT_COVERED:
				g.setColor(Color.black);
			case FinchGame.RIGHT_UNCOVERED:
			{
				g.fillRect(40, 180, 240, 40);
				int[] xs = {280, 360, 280};
				int[] ys = {120, 200, 280};
				g.fillPolygon(xs, ys, 3);
				break;
			}
			case FinchGame.UP_COVERED:
				g.setColor(Color.black);
			case FinchGame.UP_UNCOVERED:
			{
				g.fillRect(180, 120, 40, 240);
				int[] xs = {120, 200, 280};
				int[] ys = {120, 40, 120};
				g.fillPolygon(xs, ys, 3);
				break;
			}
		}
	}
	
	
}