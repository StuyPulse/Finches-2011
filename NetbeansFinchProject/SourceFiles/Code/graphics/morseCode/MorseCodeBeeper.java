package Code.graphics.morseCode;

import java.awt.TextArea;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 * @author Erik Pasternak (epastern@andrew.cmu.edu)
 *
 * Created using work by Chris Bartley as a starting point. This module is
 * designed to take input from the user either as dot '.', dash '-', space ' '
 * input that it will translate into text or a text string that it will
 * translate into Morse Code.
 *
 * To use this program, place the Finch's tail on a soft object so it is slightly inclined.
 * Then tap on the tail to enter morse code (as a telegraph operator used to enter the code).
 */
public class MorseCodeBeeper
   {
   //MorseLookup is arranged with the alphabet, then letters
   //e.g. 'a','b',...,'z','0','1','2',...,'9'
   static final String[] MorseLookup = {".-", "-...", "-.-.", "-..", ".", "..-.",
                                        "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.",
                                        "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", "-----", ".----", "..---", "...--",
                                        "....-", ".....", "-....", "--...", "---..", "----."};
   //An array of strings is used because it makes lookup and appending to a string easier
   static final String[] asciiLookup = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                                        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4",
                                        "5", "6", "7", "8", "9"};
   final int ENCRYPT = 'e';
   final int DECRYPT = 'd';
   final int EXIT = 'x';
   //whether or not we want to strictly enforce timing requirements for the morse interpreter
   boolean STRICTMORSE = false;
   TextArea messageOutput;
   TextField morseOutput;

   public MorseCodeBeeper() throws IOException
      {
      //First we initialize a new Finch, this finds a finch and connects to it.
      //It currently hangs indefinitely if there is no Finch to connect to.
      final Finch finch = new Finch();
      //We'll also want some input from the keyboard, so start a reader for it
      final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      //This will store the message the user wants to change to morse
      String Message;
      //This stores morse the user wants to change to a message
      String Morse;
      //Whether or not an input is valid.
      boolean isValid;
      //Encrypt, Decrypt, or Exit option
      int edxOpt;
      //Min and Max accelerometer values
      double[] accelVals;
      //Vals for setting a threshhold and tracking the difference between min
      //and max. Currently, only Thresh is used and it is set for halfway between
      //the min and max.
      double beepThresh, beepDiff;

      System.out.println("Hello, Dave.");
      System.out.println("Can you set up my accelerometer, please?");
      //Gets the min and max vals for wherever you've set your Finch up
      accelVals = MCB_setAccelerometer(in, finch);
      //Halfway between is where we switch. Future versions need some hysteresis
      beepThresh = (accelVals[0] + accelVals[1]) / 2;
      beepDiff = accelVals[0] - accelVals[1];
      if (beepDiff < 0)
         {
         beepDiff = -beepDiff;
         }
      System.out.println("Now then, what can I help you with today?");

      //Repeat until we quit.
      while (true)
         {
         //First we want to get whether we're encrypting, decrypting, or letting the user
         //beep.
         do
            {
            isValid = true;
            System.out.println("Enter 'e' to encrypt, 'd' to decrypt, 'i' to input by" +
                               " bouncing the finch, or 'x' to exit:");
            edxOpt = in.read();

            if (edxOpt == EXIT)
               {
               //I can feel my mind going, Dave.
               System.out.println("Daisy, Daisy, give me your answer truuuuuuuuuu...");
               finch.quit();
               System.exit(0);
               }
            //These are the only valid options, so clearly we need to take in new input.
            else if (edxOpt != 'e' && edxOpt != 'd' && edxOpt != 'i')
               {
               System.out.println("I can't let you do that, Dave.");
               isValid = false;
               }
            //Throw away any available bytes because you have to hit return.
            while (in.ready())
               {
               in.read();
               }
            }
         while (!isValid);

         if (edxOpt == 'e')
            {
            //Red light means encrypt
            finch.setLED(255, 0, 0);
            //Get an input line and change it into morse. Stores as a string
            //of dots and dashes.
            Morse = MCB_GetMessage(in);
            System.out.println("The encrypted message is");
            System.out.println(Morse);
            //Play it on the Finch's on board buzzer
            MCB_PlayMorse(Morse, finch);
            }
         if (edxOpt == 'd')
            {
            //Blue light means decrypt
            finch.setLED(0, 0, 255);
            //Gets text input as .'s and -'s and translates it back into a message
            Message = MCB_Decrypt(in);
            System.out.println("The decrypted message is");
            System.out.println(Message);
            }
         if (edxOpt == 'i')
            {
            System.out.println("Use the tail of the finch to input your message. Press any" +
                               " key to end.");
            double pressed;
            boolean wasPressed = false;
            finch.setLED(0, 255, 0);
            MorseCodeFrame frame = new MorseCodeFrame();
            frame.setVisible(true);

            while (true)
               {
               //Grab the current accelerometer values
               pressed = finch.getXAcceleration();
               if (pressed > beepThresh)
                  {
                  //Change our state to true if the tail is down
                  //the MorseCodeFrame.TAIL tells it the tail of the finch was
                  //the input so it knows to filter the result
                  frame.beepStateChanged(true, MorseCodeFrame.TAIL);
                  finch.buzz(frame.getFreq(), 50);
                  }
               else
                  {
                  //otherwise change it to false
                  frame.beepStateChanged(false, MorseCodeFrame.TAIL);
                  }
               //if the user hit enter quit
               if (in.ready())
                  {
                  in.read();
                  break;
                  }
               }
            //delete the frame we made
            frame.dispose();
            System.out.println("All I can do is beep, Dave.");
            }
         System.out.println("");
         }
      }

   /*This function is designed to take in a max and min input for the accelerometers
    * on the Finch. This way the user can position it where they want and calibrate for
    * however they found to balance the finch.
    */

   double[] MCB_setAccelerometer(BufferedReader in, Finch finch) throws IOException
      {
      double[] minMax = {0, 0};
      System.out.println("Place the tail of the Finch onto something you can press it" +
                         " down on and will bounch the tail back up when you release it.");
      System.out.println("Let it sit unpressed and hit return.");
      in.readLine();
      minMax[1] = finch.getXAcceleration();
      System.out.println("Now hold the tail of the finch down and hit return again.");
      in.readLine();
      minMax[0] = finch.getXAcceleration();
      return minMax;
      }

   /*This function reads in a string from standard in and translates it into morse. The
    * result is a string of dots '.' and dashes '-' with single spaces between characters
    * and triple spaces between words for easy readability. Currently, only letters and
    * the numbers 0-9 are accepted as inputs.
    */

   String MCB_GetMessage(BufferedReader in) throws IOException
      {
      //Start with an empty string
      String Morse = "";
      int currChar;
      int MorseIndex;
      System.out.println("Enter the message to encrypt. Only letters, spaces, and" +
                         " numbers will be processed. All other characters will be" +
                         " ignored. Press 'Enter' when finished.");
      do
         {
         //read the characters one at a time.
         currChar = in.read();
         //If it's lower case or uppercase subtract to have a/A reference index 0.
         if (currChar >= 'a' && currChar <= 'z')
            {
            MorseIndex = currChar - 'a';
            }
         else if (currChar >= 'A' && currChar <= 'Z')
            {
            MorseIndex = currChar - 'A';
            //Numbers start at 26 in the lookup table.
            }
         else if (currChar >= '0' && currChar <= '9')
               {
               MorseIndex = currChar - '0' + 26;
               }
            else if (currChar == ' ')
                  {
                  //If the input was a space append two spaces to our output. We end
                  //up with three total because each letter has a space after it already.
                  Morse = Morse + "  ";
                  continue;
                  }
               else
                  {
                  continue;
                  }
         //This adds the string of dots and dashes that represent the letter, followed
         //by a space.
         Morse = Morse + MorseLookup[MorseIndex] + " ";
         //We're done if we see a carriage return or a linefeed character.
         }
      while (currChar != 13 && currChar != 10);
      //Return the generated string.
      return Morse;
      }

   /*Reads in a string from standard in and translates it from .s and -s to text.
    * Returns the translated string.
    */

   String MCB_Decrypt(BufferedReader in) throws IOException
      {
      //Start with an empty string
      String Message = "";
      //The current input character
      int currChar;
      //Whether or not the last character seen was a space
      boolean wasSpace = false;
      //Whether or not the letter was recognized
      boolean letterFound;
      //Keep track of dots and dashes and only process them when we have a full letter
      String currLetter = "";

      System.out.println("Enter your message in morse code using '.' for dots and '-' for" +
                         " dashes. Put a single space between letters and a double space" +
                         " between words.");
      do
         {
         //read the characters one at a time.
         currChar = in.read();
         //Add dots and dashes to the current letter.
         if (currChar == '.')
            {
            currLetter += ".";
            }
         else if (currChar == '-')
            {
            currLetter += "-";
            }
         //On a space or a return we should finish the current letter and add it to
         //the message.
         if (currChar == ' ' || currChar == 13 || currChar == 10)
            {
            //If we have a letter, of course.
            if (currLetter.length() > 0)
               {
               //In case we don't recognize it.
               letterFound = false;
               //Check through the lookup table for a match
               for (int i = 0; i < MorseLookup.length; i++)
                  {
                  //If we find it add it to the message and break
                  if (currLetter.compareTo(MorseLookup[i]) == 0)
                     {
                     Message += asciiLookup[i];
                     letterFound = true;
                     break;
                     }
                  }
               //if we didn't find it print an error
               if (!letterFound)
                  {
                  System.out.printf("%s was not recognized and will be ignored\n", currLetter);
                  }
               //and then clear our current letter track
               currLetter = "";
               }
            //If this is the second space we've seen put a space into the message
            if (wasSpace)
               {
               Message += " ";
               //We set this to false because we want every two spaces to appear as one.
               wasSpace = false;
               }
            //Otherwise this is a new space and set wasSpace to true
            else
               {
               wasSpace = true;
               }
            }
         //And if we saw anything else wasSpace becomes false.
         else
            {
            wasSpace = false;
            }
         //We're done if it was a return
         }
      while (currChar != 13 && currChar != 10);
      //Return the translated message
      return Message;
      }

   /*This function will read a string of .'s, -'s and spaces and play it as morse
    * code on the Finch's onboard buzzer. Two or more spaces will play as a space
    * between words, while single spaces will play as a space between characters.
    * Future versions should make the length of oneTick selectable.
    */

   void MCB_PlayMorse(String Morse, Finch finch)
      {
      int oneTick = 150;
      int frequency = 600;
      //a dot is one tick
      //a dash is three dots
      //a space between dots and dashes is one dot
      //a space between letters is 3 dots
      //a space between words is 7 dots
      for (int i = 0; i < Morse.length(); i++)
         {
         //For a single space delay three ticks, for multiple spaces delay 7 ticks
         if (Morse.charAt(i) == ' ')
            {
            //We want to delay longer if there's more than one space.
            if ((i + 1) < Morse.length() && Morse.charAt(i + 1) == ' ')
               {
               //Delay only 6 because there's a delay after every dot and dash already
               MCB_DelayTicks(6);
               //Keep incrimenting the counter until the next character isn't a space.
               while (i + 1 < Morse.length() && Morse.charAt(i + 1) == ' ')
                  {
                  i++;
                  }
               //again, only 2 delays here instead of 3.
               }
            else
               {
               MCB_DelayTicks(2);
               }
            }
         //Plays a dot and then delays one tick. The buzzer on the finch runs in parallel
         //with whatever we're doing, so we delay for two ticks. One for the buzz, one for
         //the pause
         else if (Morse.charAt(i) == '.')
            {
            finch.buzz(frequency, oneTick);
            MCB_DelayTicks(2 * oneTick);
            }
         //Plays a dash and then delays for one tick
         else if (Morse.charAt(i) == '-')
               {
               finch.buzz(frequency, oneTick * 3);
               //three delays for the buzz and one for the space
               MCB_DelayTicks(4 * oneTick);
               }
         }
      }

   //This function just sleeps the current thread for ticks number of miliseconds

   void MCB_DelayTicks(int ticks)
      {
      try
         {
         Thread.sleep(ticks); // do nothing for ticks miliseconds
         }
      catch (InterruptedException e)
         {
         e.printStackTrace();
         }
      }

   public static void main(final String[] args) throws IOException
      {
      new MorseCodeBeeper();
      }
   }