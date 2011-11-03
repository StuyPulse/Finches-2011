package Code.graphics.GestureProgrammer.GestureProgrammerUtils;

import java.util.concurrent.TimeUnit;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.border.LineBorder;
import edu.cmu.ri.createlab.userinterface.component.DatasetPlotter;
import org.jdesktop.layout.GroupLayout;

/**
 * Created by IntelliJ IDEA.
 * User: Styler
 * Date: Feb 17, 2009
 * Time: 3:45:43 PM
 */
public class GestureProgrammerView extends JFrame
   {
   public enum State
      {
         RECORDING, PLAYING, CALIBRATING, IDLE, QUIT
      }

   //Stores the state of the programmer
   private static State state = State.IDLE;

   //Creates the accelerometer plotter
   final DatasetPlotter<Double> accelerometerPlotter = new DatasetPlotter<Double>(-1.5, 1.5, 300, 300, 10, TimeUnit.MILLISECONDS);

   final JButton playButton = new JButton("Play");
   final JButton recordButton = new JButton("Record");
   final JButton calibrateButton = new JButton("Calibrate");
   final JButton quitButton = new JButton("Quit");
   //Create the sim finch panel for displaying wheel velocities
   final FinchSimViewPanel viewPanel = new FinchSimViewPanel();

   public GestureProgrammerView() throws InterruptedException
      {
      //Create this Jframe
      super("Gesture Programmer");

      //Create datasets for 0,1,x,y,z
      accelerometerPlotter.setBackgroundColor(Color.WHITE);
      accelerometerPlotter.addDataset(Color.LIGHT_GRAY);
      accelerometerPlotter.addDataset(Color.LIGHT_GRAY);
      accelerometerPlotter.addDataset(Color.RED);
      accelerometerPlotter.addDataset(Color.GREEN);
      accelerometerPlotter.addDataset(Color.BLUE);

      try
         {
         SwingUtilities.invokeAndWait(
               new Runnable()
               {
               public void run()
                  {
                  //create main panels
                  final JPanel mainPanel = new JPanel();
                  final JPanel buttonPanel = new JPanel();

                  //Control buttons

                  //False until a program is recorded
                  playButton.setEnabled(false);

                  accelerometerPlotter.getComponent().setBorder(new LineBorder(Color.BLACK, 1));

                  //Add the main panel to view frame and add child components
                  add(mainPanel);
                  mainPanel.add(viewPanel);
                  mainPanel.add(buttonPanel);
                  mainPanel.add(accelerometerPlotter.getComponent());

                  //Layout the main frame
                  GroupLayout layout = new GroupLayout(mainPanel);
                  mainPanel.setLayout(layout);
                  layout.setAutocreateGaps(true);
                  layout.setAutocreateContainerGaps(true);

                  GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
                  vGroup.add(layout.createParallelGroup().
                        add(viewPanel).add(accelerometerPlotter.getComponent()));
                  vGroup.add(buttonPanel);
                  layout.setVerticalGroup(vGroup);

                  GroupLayout.ParallelGroup hGroup = layout.createParallelGroup();
                  hGroup.add(layout.createSequentialGroup().
                        add(viewPanel).add(accelerometerPlotter.getComponent()));
                  hGroup.add(buttonPanel);
                  layout.setHorizontalGroup(hGroup);

                  //Layout the buttons of the button panel
                  buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
                  buttonPanel.add(recordButton);
                  buttonPanel.add(playButton);
                  buttonPanel.add(Box.createGlue());
                  buttonPanel.add(calibrateButton);
                  buttonPanel.add(quitButton);

                  playButton.addActionListener(new ActionListener()
                  {
                  public void actionPerformed(final ActionEvent e)
                     {
                     if (playButton.getText().equals("Play"))
                        {
                        state = State.PLAYING;
                        updateView();
                        }
                     else if (playButton.getText().equals("Stop"))
                        {
                        state = State.IDLE;
                        updateView();
                        }
                     }
                  });

                  recordButton.addActionListener(new ActionListener()
                  {
                  public void actionPerformed(final ActionEvent e)
                     {
                     if (recordButton.getText().equals("Record"))
                        {
                        state = State.RECORDING;
                        updateView();
                        }
                     else if (recordButton.getText().equals(" Stop "))
                        {
                        state = State.IDLE;
                        updateView();
                        }
                     }
                  });

                  calibrateButton.addActionListener(new ActionListener()
                  {
                  public void actionPerformed(final ActionEvent e)
                     {
                     state = State.CALIBRATING;
                     updateView();
                     }
                  });

                  quitButton.addActionListener(new ActionListener()
                  {
                  public void actionPerformed(final ActionEvent e)
                     {
                     state = State.QUIT;
                     }
                  });

                  pack();
                  setVisible(true);
                  }
               });
         }
      catch (InvocationTargetException x)
         {
         System.err.println(x);
         state = State.QUIT;
         }
      catch (InterruptedException x)
         {
         System.err.println(x);
         state = State.QUIT;
         }
      }

   public void updateAccelerations(final double[] accelerometerState)
      {
      accelerometerPlotter.setCurrentValues(0.0, 1.0, accelerometerState[0],
                                            accelerometerState[1],
                                            accelerometerState[2]);
      }

   public State getProgrammerState()
      {
      return this.state;
      }

   public void setProgrammerState(State state)
      {
      this.state = state;
      updateView();
      }

   public void updateView()
      {
      playButton.setText(state == State.PLAYING ? "Stop" : "Play");
      calibrateButton.setText(state == State.CALIBRATING ? "Calibrating..." : "  Calibrate  ");
      recordButton.setText(state == State.RECORDING ? " Stop " : "Record");

      playButton.setEnabled(state == State.IDLE || state == State.PLAYING);
      recordButton.setEnabled(state == State.IDLE || state == State.RECORDING);
      calibrateButton.setEnabled(state == State.IDLE);
      }

   public void updateSim(double left, double right, Color beak)
      {
      viewPanel.update(left,right,beak);
      }
   }
