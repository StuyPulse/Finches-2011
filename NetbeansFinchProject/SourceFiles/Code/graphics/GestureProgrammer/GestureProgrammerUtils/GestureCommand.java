package Code.graphics.GestureProgrammer.GestureProgrammerUtils;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 * @author Alex Styler (astyler@gmail.com)
 */

public class GestureCommand
   {
   public double leftVel = 0;
   public double rightVel = 0;

   public GestureCommand(double left, double right)
      {
      this.leftVel = left;
      this.rightVel = right;
      }

   public void execute(Finch finch)
      {
      finch.setWheelVelocities((int)leftVel, (int)rightVel);
      }

   public String toString()
      {
      return "(" + leftVel + ", " + rightVel + ")";
      }

   public static class StopCommand extends GestureCommand
      {
      public StopCommand()
         {
            super(0.0,0.0);            
         }
      }
   }
