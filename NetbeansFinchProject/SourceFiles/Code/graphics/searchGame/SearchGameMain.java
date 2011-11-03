package Code.graphics.searchGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 * Created by: Eric Cheek (echeek@andrew.cmu.edu)
 * Date: 15 February 2009
 * A Treasure Hunt Game using the Finch as a controller/feedback device
 *
 * Instructions: Tilt the finch to control movement of the circle.
 * Monitor feedback to find treasure in the window.
 *
 * Feedback options: 
 * Voice: "warmer"/"colder"
 * Buzzing: higher pitched buzzing means you are closer
 * LED: red is closer, blue is further
 */

public class SearchGameMain implements ActionListener
   {
   SearchGameView view;
   SearchGameTiltControl controller;
   Finch myFinch;

   //feedback on/off states
   boolean voiceOn = true, buzzOn = false, ledOn = true;

   // game states
   static final int GAME_ACTIVE = 0, GAME_INACTIVE = 1, GAME_OVER = 2;
   int gameState;
   //other game vairables
   int targetX, targetY, targetRadius;
   int curX, curY;
   double curDistance;

   //times used to limit slow actions like LED setting
   long lastSpeechTime = 0;
   long lastLEDTime = 0;
   double lastReportedDistance;// used for hot/cold reporting

   long gameStartTime, gameTotalTime;

   /**
    * Constructor
    * connect to finch, create view and controller
    */
   public SearchGameMain()
      {
      myFinch = new Finch();
      System.out.println("Finch ready.");

      view = new SearchGameView();
      view.setListener(this);
      view.show();

      fetchFeedbackStates();

      controller = new SearchGameTiltControl(myFinch);
      controller.setLimits(0, 0, view.getScreenWidth(), view.getScreenHeight());
      controller.centerPosition();
      fetchSensitivity();

      //initialize game variables
      gameState = GAME_INACTIVE;
      }

   /**
    * main game control
    */
   public void run()
      {
      while (true)
         {
         //clear and reset game to initial states
         view.clearScreen();
         view.makeStatusBig();
         view.setStatus("Start New Game");

         myFinch.setLED(0, 0, 0);

         while (gameState == GAME_INACTIVE)
            {// wait for new game to start
            updatePlayer();//let user move circle around screen before game
            }

         //setup and start new game
         startNewGame();

         while (gameState == GAME_ACTIVE)
            { //while game is running
            processGame(); //do game logic
            }

         while (gameState == GAME_OVER)
            { //while game is in game over state
            //show player and target
            view.setPlayerPosition(curX, curY);
            view.showTarget(targetX, targetY, targetRadius);

            //flash random colors on finch LED
            myFinch.setLED((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
            }
         }
      }

   /**
    * Initiate new game. Set/Reset necessary variables
    */
   public void startNewGame()
      {
      //move player to center
      controller.setLimits(0, 0, view.getScreenWidth(), view.getScreenHeight());
      controller.centerPosition();
      updatePlayer();

      //choose random position for treasure
      targetX = (int)(Math.random() * view.getScreenWidth());
      targetY = (int)(Math.random() * view.getScreenHeight());
      targetRadius = 30;

      //record game start time
      gameStartTime = System.currentTimeMillis();
      }

   /**
    * Calls main game logic
    */
   public void processGame()
      {
      //update and display game time
      gameTotalTime = System.currentTimeMillis() - gameStartTime;
      view.setStatus("Time: " + (double)(gameTotalTime / 1000.0));

      //game logic:

      updatePlayer();
      provideFeedback();

      //test for game win
      if (curDistance < targetRadius)
         {
         gameOver();
         }
      }

   /**
    * update variables related to player
    */
   public void updatePlayer()
      {
      int tmpX = curX, tmpY = curY;

      //update player position
      controller.update();
      curX = controller.getX();
      curY = controller.getY();

      //redraw player
      view.clearPlayerPosition(tmpX, tmpY);
      view.setPlayerPosition(curX, curY);

      //use distance formula to determine distance between treasure and player
      curDistance = Math.sqrt((targetX - curX) * (targetX - curX)
                              + (targetY - curY) * (targetY - curY));
      }

   /**
    * Determine and issue appropriate feedback
    */
   public void provideFeedback()
      {
      //if voice feedback is on and 1.5 seconds have passed sense last update
      if (voiceOn && System.currentTimeMillis() - lastSpeechTime > 1500)
         {

         if (curDistance < lastReportedDistance)
            {
            myFinch.saySomething("warmer"); //closer than last time
            }
         else if (curDistance == lastReportedDistance)
            {
            myFinch.saySomething("no change"); //no change since last time
            }
         else
            {
            myFinch.saySomething("colder"); //further than last time
            }

         lastReportedDistance = curDistance; //store distance
         lastSpeechTime = System.currentTimeMillis(); //record time of this message
         }

      //if LED feedback is on and 0.25 seconds have passed
      if (ledOn && System.currentTimeMillis() - lastLEDTime > 250)
         {
         //calculate red value
         //prevent division by zero by capping distance to >1
         //prevent red value from exeeding 255
         int red = (int)Math.min(255, 10 * 255 / Math.max(curDistance, 1));

         //calculate blue value
         //try creating your own function to determine blue value
         int blue = (int)Math.min(255, 10 * 255 / Math.max(1000 - curDistance, 1));

         //set LED to color
         myFinch.setLED(red, 0, blue);
         lastLEDTime = System.currentTimeMillis();//store last update time
         }

      //if Buzzer feedback is on
      if (buzzOn)
         {
         //calculate frequency so higher pitches correspond to less distance
         int frequency = (int)(100000 / Math.max(curDistance, 1));

         // System.out.println(frequency);
         //sound buzzer
         myFinch.buzz(frequency, 50);
         }
      }

   /**
    * Called when game is won
    */
   public void gameOver()
      {
      //change game state
      gameState = GAME_OVER;

      //give congratulatory response

      view.makeStatusBig();
      view.setStatus("<html>You Win! <br/>Total time: "
                     + (double)(gameTotalTime / 1000.0) + "s</html>");

      if (voiceOn)
         {
         myFinch.saySomething("You win! Total time: " + (double)(gameTotalTime / 1000.0) + " seconds");
         }
      }

   /**
    * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
    *
    * Handle events fired by view
    */
   public void actionPerformed(ActionEvent e)
      {
      String command = e.getActionCommand();
      if (command.equals(SearchGameView.START_GAME))
         {
         gameState = GAME_ACTIVE;
         }
      else if (command.equals(SearchGameView.END_GAME))
         {
         gameState = GAME_INACTIVE;
         }
      else if (command.equals(SearchGameView.CHANGE_SENSITIVITY))
            {
            //game alerted to change in sensitivity setting
            fetchSensitivity();
            }
         else if (command.equals(SearchGameView.TOGGLE_FEEDBACK))
               {
               //game alerted to change in feedback types
               fetchFeedbackStates();
               }
      }

   /**
    * Fetch on/off state of various feedback systems
    */
   public void fetchFeedbackStates()
      {
      voiceOn = view.isVoiceOn();
      buzzOn = view.isBuzzOn();
      ledOn = view.isLEDOn();
      if (!ledOn)
         {
         //turn off LED if led feedback is disabled
         myFinch.setLED(0, 0, 0);
         }
      }

   /**
    * Get level selection from view and set controller movement scales
    *
    */
   public void fetchSensitivity()
      {
      switch (view.getSensitivityLevel())
         {
         case 2:
            controller.setScales(15, 15);
            break;
         case 1:
            controller.setScales(10, 10);
            break;
         case 0:
            controller.setScales(5, 5);
            break;
         }
      }

   /**
    * Entry point
    */
   public static void main(String[] args)
      {
      SearchGameMain sgm = new SearchGameMain();
      sgm.run();
      }
   }
