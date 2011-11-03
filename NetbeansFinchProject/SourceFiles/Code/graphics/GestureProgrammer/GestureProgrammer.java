package Code.graphics.GestureProgrammer;

import java.awt.Color;
import Code.graphics.GestureProgrammer.GestureProgrammerUtils.*;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 * Gesture programmer allows the user to record the Finch's motions and play
 * them back.  Users record motions by picking up the Finch and tilting and rolling it,
 * which is then mapped onto forward, backward, or turning motion of the Finch during
 * playback.  This is an excellent program to demonstrate simple gestural programming
 * with kids as young as four.
 * @author Alex Styler (astyler@gmail.com)
 */
public class GestureProgrammer
   {
   //Program that is recorded to when recording
   private static GestureProgram recordingProgram = new GestureProgram();

   //The recorded program that is played back when Play is hit
   private static GestureProgram recordedProgram = new GestureProgram();

   private static double[] accelerometerOffsets = new double[3];

   public static void main(final String[] args) throws InterruptedException
      {
      //Create the finch and the GUI
      final Finch finch = new Finch();
      final GestureProgrammerView view = new GestureProgrammerView();

      int calibrateCounter = 0;
      final double[] calibrationSums = new double[3];

      while (true)
         {
         //Update accelerations in the view panel
         final double[] accelerometerState = finch.getAccelerations();
         accelerometerState[0] -= accelerometerOffsets[0];
         accelerometerState[1] -= accelerometerOffsets[1];
         accelerometerState[2] -= accelerometerOffsets[2];
         view.updateAccelerations(accelerometerState);

         //Current command from accelerometer state
         GestureCommand currentCommand = createGestureCommand(accelerometerState);

         //beak color to set
         Color beakColor;

         switch (view.getProgrammerState())
            {
            case PLAYING:
               //Grab command from recorded program, execute on finch,
               //set beak green
               beakColor = Color.GREEN;
               currentCommand = recordedProgram.getNextCommand();
               currentCommand.execute(finch);

               //If the end of the program is reached, switch to idle state
               if (currentCommand instanceof GestureCommand.StopCommand)
                  {
                     view.setProgrammerState(GestureProgrammerView.State.IDLE);
                  }
               break;

            case RECORDING:
               //Execute command on finch for feedback, append to
               //recording program, set beak red
               beakColor = Color.RED;
               currentCommand.execute(finch);
               recordingProgram.appendCommand(currentCommand);
               break;

            case CALIBRATING:
               //sum accelerometer states for 20 iterations, use
               //average on last iteration as offsets in the future,
               //make sure finch wheels are stopped, and set beak blue
               beakColor = Color.BLUE;
               (new GestureCommand.StopCommand()).execute(finch);

               calibrationSums[0] += accelerometerState[0];
               calibrationSums[1] += accelerometerState[1];
               calibrationSums[2] += accelerometerState[2] - 1;

               calibrateCounter++;
               //last iteration reached
               if (calibrateCounter >= 20)
                  {
                  //reset counter
                  calibrateCounter = 0;
                  //store averages as offsets for the future
                  accelerometerOffsets[0] = calibrationSums[0] / 20.0;
                  accelerometerOffsets[1] = calibrationSums[1] / 20.0;
                  accelerometerOffsets[2] = calibrationSums[2] / 20.0;

                  //set state back to idle because calibration is done
                  view.setProgrammerState(GestureProgrammerView.State.IDLE);
                  }
               break;

            case QUIT:
               //tell the finch to quit, destory the GUI, and exit the program
               finch.quit();
               view.dispose();
               System.exit(0);

            case IDLE:
            default:
               //Turn the beak off, Reset the program to the beginning for next playback,
               //make sure the wheels are stopped, and check if RecordingProgram has data
               beakColor = Color.BLACK;
               recordedProgram.resetProgram();
               new GestureCommand.StopCommand().execute(finch);

               //if recordingProgram has data, a program was just recorded
               //store into recordedProgram and create a new recordingProgram
               //for future recordings
               if(recordingProgram.hasCommands()){
                  recordedProgram = recordingProgram;
                  recordingProgram = new GestureProgram();
               }
               break;
            }

         //have the finch set its beak to the appropriate color
         finch.setLED(beakColor);
         //tell the view to update the simulator with this data
         view.updateSim(currentCommand.leftVel, currentCommand.rightVel, beakColor);
         }
      }

   private static GestureCommand createGestureCommand(double[] accelerometerState)
      {
      double left = 160 * accelerometerState[0];
      double right = 160 * accelerometerState[0];
      left = left - 100 * accelerometerState[1];
      right = right + 100 * accelerometerState[1];
      return new GestureCommand(left, right);
      }
   }