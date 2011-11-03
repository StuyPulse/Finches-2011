package Code.graphics.searchGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

/**
 * @author Eric Cheek (echeek@andrew.cmu.edu)
 *
 */
final class SearchGameView
   {
   JFrame frame;

   JPanel screen;
   Graphics screenGraphics;

   JLabel status;
   //menu buttons
   JMenuItem startGame, endGame;
   JRadioButtonMenuItem voiceToggle, buzzToggle, ledToggle;
   JRadioButtonMenuItem highSense, midSense, lowSense;

   //action commands
   public static final String START_GAME = "StartGame", END_GAME = "EndGame";
   public static final String TOGGLE_FEEDBACK = "ToggleBack";
   public static final String CHANGE_SENSITIVITY = "ChangeSensitivity";

   Color playerColor = Color.RED;
   int playerRadius = 5;

   SearchGameView()
      {
      frame = new JFrame("Treasure Hunt Game");
      frame.setSize(800, 600);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      screen = new JPanel();
      screen.setBackground(Color.white);
      frame.getContentPane().add(screen);

      status = new JLabel();
      screen.add(status);
      status.setFont(new Font("Times New Roman", 0, 60));
      status.setForeground(new Color(0xcacaca));

      JMenuBar menuBar = new JMenuBar();
      frame.setJMenuBar(menuBar);

      //create Game menu
      JMenu game = new JMenu("Game");
      menuBar.add(game);

      startGame = new JMenuItem("Start Game");
      startGame.setActionCommand(START_GAME); //action command allows listeners to react appropriately
      game.add(startGame);

      endGame = new JMenuItem("End Game");
      endGame.setActionCommand(END_GAME);
      game.add(endGame);

      //create feedback menu
      JMenu feedback = new JMenu("Feedback");
      menuBar.add(feedback);

      voiceToggle = new JRadioButtonMenuItem("Voice", true);
      voiceToggle.setActionCommand(TOGGLE_FEEDBACK);
      feedback.add(voiceToggle);

      buzzToggle = new JRadioButtonMenuItem("Buzz", true);
      buzzToggle.setActionCommand(TOGGLE_FEEDBACK);
      feedback.add(buzzToggle);

      ledToggle = new JRadioButtonMenuItem("LED", true);
      ledToggle.setActionCommand(TOGGLE_FEEDBACK);

      feedback.add(ledToggle);

      //Menu to control sensitivity
      JMenu sensitivity = new JMenu("Sensitivity");
      menuBar.add(sensitivity);

      //ButtonGroup insures no two sensitivity levels are selected at the same time
      ButtonGroup senseButtons = new ButtonGroup();

      highSense = new JRadioButtonMenuItem("High", false);
      highSense.setActionCommand(CHANGE_SENSITIVITY);
      senseButtons.add(highSense);
      sensitivity.add(highSense);

      midSense = new JRadioButtonMenuItem("Medium", true);
      midSense.setActionCommand(CHANGE_SENSITIVITY);
      senseButtons.add(midSense);
      sensitivity.add(midSense);

      lowSense = new JRadioButtonMenuItem("Low", false);
      lowSense.setActionCommand(CHANGE_SENSITIVITY);
      senseButtons.add(lowSense);
      sensitivity.add(lowSense);
      }

   /**
    * Display GUI
    */
   public void show()
      {
      frame.setVisible(true);
      screenGraphics = screen.getGraphics();
      }

   /**
    * Set listener to handle menu input
    */
   public void setListener(ActionListener listener)
      {
      startGame.addActionListener(listener);
      endGame.addActionListener(listener);

      voiceToggle.addActionListener(listener);
      buzzToggle.addActionListener(listener);
      ledToggle.addActionListener(listener);

      highSense.addActionListener(listener);
      midSense.addActionListener(listener);
      lowSense.addActionListener(listener);
      }

   public boolean isVoiceOn()
      {
      return voiceToggle.isSelected();
      }

   public boolean isBuzzOn()
      {
      return buzzToggle.isSelected();
      }

   public boolean isLEDOn()
      {
      return ledToggle.isSelected();
      }

   /**
    * Return 3-level sensitivity setting
    */
   public int getSensitivityLevel()
      {
      if (highSense.isSelected())
         {
         return 2;
         }
      else if (midSense.isSelected())
         {
         return 1;
         }
      else
         {
         return 0;
         }
      }

   /**
    * return current screen width
    */
   public int getScreenWidth()
      {
      return (int)(screen.getSize().getWidth());
      }

   /**
    * return current screen height
    */
   public int getScreenHeight()
      {
      return (int)(screen.getSize().getHeight());
      }

   /**
    * Screen will repaint in backgroundColor (set to white in constructor)
    */
   public void clearScreen()
      {
      screen.repaint();
      }

   /**
    * Set player color
    * @param c
    */
   public void setPlayerColor(Color c)
      {
      playerColor = c;
      }

   /**
    * Set player radius
    * @param r
    */
   public void setPlayerRadius(int r)
      {
      playerRadius = r;
      }

   /**
    * Erase player in position x,y
    */
   public void clearPlayerPosition(int x, int y)
      {
      screenGraphics.setColor(Color.white);
      screenGraphics.fillRect(x - playerRadius, y - playerRadius, playerRadius * 2, playerRadius * 2);
      }

   /**
    * Draw player in position x, y
    */
   public void setPlayerPosition(int x, int y)
      {
      screenGraphics.setColor(playerColor);
      screenGraphics.fillOval(x - playerRadius, y - playerRadius, playerRadius * 2, playerRadius * 2);
      }

   /**
    * Draw target with radius in position x, y
    */
   public void showTarget(int x, int y, int radius)
      {
      screenGraphics.setColor(Color.blue);
      screenGraphics.fillOval(x - 3, y - 3, 6, 6);
      screenGraphics.drawOval(x - radius, y - radius, radius * 2, radius * 2);
      }

   /**
    * Set status font to 12pt
    */
   public void makeStatusSmall()
      {
      status.setFont(new Font("Times New Roman", Font.PLAIN, 12));
      }

   /**
    * Set status font to 60pt
    */
   public void makeStatusBig()
      {
      status.setFont(new Font("Times New Roman", Font.PLAIN, 60));
      }

   /**
    * Set status to display message s
    */
   public void setStatus(String s)
      {
      status.setText(s);
      }
   }
