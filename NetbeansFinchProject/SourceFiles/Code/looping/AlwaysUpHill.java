
package Code.looping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import edu.cmu.ri.createlab.userinterface.component.DatasetPlotter;
import org.jdesktop.layout.GroupLayout;

/**
 * @author Erik Pasternak (epastern@andrew.cmu.edu)
 *
 * This code is designed to test the accelerometers and the wheels together by
 * changing the direction in which the wheels drive based on readings from the
 * accelerometers. Currently, it should drive to head uphill whenever not on a
 * flat surface.
 *
 * This program could be simplified significantly, but is complex because it
 * graphs the accelerometer values and because it allows other cases (like
 * AlwaysDownHill, or TurnRightUpHills)
 */
public class AlwaysUpHill
{
   Finch finch;
   DatasetPlotter<Double> accelerometerPlotter;
   DatasetPlotter<Double> xPlot;
   DatasetPlotter<Double> yPlot;
   DatasetPlotter<Double> zPlot;

   public AlwaysUpHill() throws IOException
   {
      //create a new finch
      finch = new Finch();
      //create an input for std input
      final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      final int FLAT = 0;
      final int FORW = 1;
      final int BACK = 2;
      final int LEFT = 3;
      final int RIGHT = 4;
      double[] flat = {0,0,0};
      double[] forward = {0,0,0};
      double[] back = {0,0,0};
      double[] left = {0,0,0};
      double[] right = {0,0,0};
      double[] diffs = new double[5];
      int state = FLAT;
      boolean changed = false;

      System.out.println("Hold the finch flat and press return.");
      in.readLine();
      flat = finch.getAccelerations();
      System.out.println("Tilt the finch forward and press return.");
      in.readLine();
      forward = finch.getAccelerations();
      System.out.println("Tilt the finch backward and press return.");
      in.readLine();
      back = finch.getAccelerations();
      System.out.println("Tilt the finch to the left and press return.");
      in.readLine();
      left = finch.getAccelerations();
      System.out.println("Tilt the finch to the right and press return.");
      in.readLine();
      right = finch.getAccelerations();
      System.out.println("flat = "+flat[0]+" : "+flat[1]+": "+flat[2]);
      System.out.println("forward = "+forward[0]+" : "+forward[1]+": "+forward[2]);
      System.out.println("back = "+back[0]+" : "+back[1]+": "+back[2]);
      System.out.println("left = "+left[0]+" : "+left[1]+": "+left[2]);
      System.out.println("right = "+right[0]+" : "+right[1]+": "+right[2]);

      HUD();
      System.out.println("Press any key to exit");

      while(!in.ready())
      {
         //get the current accelerometer values
         double[] currAccel = finch.getAccelerations();
         int minIndex = 0;

         update_HUD(currAccel);
         //The max difference on either forward/back or left/right is the one we'll
         //use to compare against flat.
         diffs[FLAT] = Math.max(Math.abs(flat[0]-currAccel[0]),Math.abs(flat[1]-currAccel[1]));
         diffs[FORW] = Math.abs(forward[0]-currAccel[0]);
         diffs[BACK] = Math.abs(back[0]-currAccel[0]);
         diffs[LEFT] = Math.abs(left[1]-currAccel[1]);
         diffs[RIGHT] = Math.abs(right[1]-currAccel[1]);
         for(int i = 0; i < diffs.length; i++)
         {
            if(diffs[i] < diffs[minIndex])
            {
               minIndex = i;
            }
         }
         if(minIndex != state)
         {
            if(changed)
            {
               state = minIndex;
               finch.setWheelVelocities(0,0);
               changed = false;
            }
            else
            {
               changed = true;
            }
         }
         else
         {
            switch(minIndex)
            {
               case FLAT:
                  finch.setWheelVelocities(0,0);
                  break;
               case FORW:
                  finch.setWheelVelocities(-255,-255);
                  break;
               case BACK:
                  finch.setWheelVelocities(255,255);
                  break;
               case LEFT:
                  finch.setWheelVelocities(-255,255);
                  break;
               case RIGHT:
                  finch.setWheelVelocities(255,-255);
                  break;
               default:
                  System.out.println("I shouldn't have come to this conclusion. minIndex = "
                                       + minIndex);
                  break;
            }
         }
      }
      finch.quit();
      System.exit(0);
   }

   public void update_HUD(double[] accelerometerState)
   {
      accelerometerPlotter.setCurrentValues(accelerometerState[0],
                                               accelerometerState[1],
                                               accelerometerState[2]);

      xPlot.setCurrentValues(accelerometerState[0]);
      yPlot.setCurrentValues(accelerometerState[1]);
      zPlot.setCurrentValues(accelerometerState[2]);
   }

   public void HUD()
   {
      accelerometerPlotter = new DatasetPlotter<Double>(-1.5, 1.5, 610, 610, 10, TimeUnit.MILLISECONDS);
      accelerometerPlotter.addDataset(Color.RED);
      accelerometerPlotter.addDataset(Color.GREEN);
      accelerometerPlotter.addDataset(Color.BLUE);

      xPlot = new DatasetPlotter<Double>(-1.5, 1.5, 200, 200, 10, TimeUnit.MILLISECONDS);
      xPlot.addDataset(Color.RED);

      yPlot = new DatasetPlotter<Double>(-1.5, 1.5, 200, 200, 10, TimeUnit.MILLISECONDS);
      yPlot.addDataset(Color.GREEN);

      zPlot = new DatasetPlotter<Double>(-1.5, 1.5, 200, 200, 10, TimeUnit.MILLISECONDS);
      zPlot.addDataset(Color.BLUE);

      //Schedule a job for the event-dispatching thread: creating and showing this application's GUI.
      SwingUtilities.invokeLater(
            new Runnable()
            {
            public void run()
               {

               final Component plotComponent = accelerometerPlotter.getComponent();
               final Component xPlotComponent = xPlot.getComponent();
               final Component yPlotComponent = yPlot.getComponent();
               final Component zPlotComponent = zPlot.getComponent();

               final JPanel panel = new JPanel();
               final GroupLayout groupLayout = new GroupLayout(panel);
               panel.setLayout(groupLayout);
               panel.setBackground(Color.WHITE);
               panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1),
                                                                  BorderFactory.createEmptyBorder(1, 1, 1, 1)));

               groupLayout.setHorizontalGroup(
                     groupLayout.createSequentialGroup()
                           .add(plotComponent)
                           .add(
                           groupLayout.createParallelGroup(GroupLayout.CENTER)
                                 .add(xPlotComponent)
                                 .add(yPlotComponent)
                                 .add(zPlotComponent))
               );

               groupLayout.setVerticalGroup(
                     groupLayout.createParallelGroup(GroupLayout.CENTER)
                           .add(plotComponent)
                           .add(
                           groupLayout.createSequentialGroup()
                                 .add(xPlotComponent)
                                 .add(yPlotComponent)
                                 .add(zPlotComponent))
               );

               // create the main frame
               final JFrame jFrame = new JFrame("Accelerometer HUD");

               // add the root panel to the JFrame
               jFrame.add(panel);

               // set various properties for the JFrame
               jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
               jFrame.addWindowListener(
                     new WindowAdapter()
                     {
                     @Override
                     public void windowClosing(final WindowEvent e)
                        {
                        finch.quit();
                        System.exit(0);
                        }
                     });
               jFrame.setBackground(Color.WHITE);
               jFrame.setResizable(false);
               jFrame.pack();
               jFrame.setLocationRelativeTo(null);// center the window on the screen
               jFrame.setVisible(true);
               }
            });
   }

    public static void main(final String[] args) throws IOException
   {
      new AlwaysUpHill();
      System.exit(0);

   }
}
