package Code.graphics.GestureProgrammer.GestureProgrammerUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * @author Alex Styler (astyler@gmail.com)
 */

public class GestureProgram
   {
      private List<GestureCommand> commandList;
      private Queue<GestureCommand> commandListClone;

      public GestureProgram(){
         this.commandList = new ArrayList<GestureCommand>();
      }

      public void appendCommand(GestureCommand command){
         commandList.add(command);
      }

      public void resetProgram(){
         commandListClone = null;
      }

      public GestureCommand getNextCommand(){
         if(commandListClone == null){
            commandListClone = new LinkedList<GestureCommand>(commandList);
         }
         if(commandListClone.peek() == null){
            //program is over, clear the clone list
            //allowing for subsequent execution
            //and return a stop command
            commandListClone = null;
            return new GestureCommand.StopCommand();
         }
         else{
            return commandListClone.poll();
         }
      }

      public boolean hasCommands(){
         return commandList.size() > 0;
      }
   }
