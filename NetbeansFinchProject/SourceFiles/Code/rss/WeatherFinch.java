package Code.rss;

import java.util.Scanner;
import edu.cmu.ri.createlab.rss.readers.WeatherReader;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 * Created by: Tom Lauwers
 * Date: 2/12/2009
 * This program uses the Finch to express the weather -
 * it will turn on the LED based on temperature and speak the temperature and weather conditions
 */

public class WeatherFinch
   {

   public static void main(final String[] args)
      {
      // Instantiating the Finch object
      Finch myFinch = new Finch();


      // Instantiate a scanner for inputting the City, State data
      final Scanner getIn = new Scanner(System.in);

      System.out.println("Please enter the name of the City: ");

      // Get the place for which the forecast should be read from the GUI textbox
      final String city = getIn.nextLine();

      System.out.println("Please enter the two character postal abbreviation of the state (like CA): ");

      // Get the place for which the forecast should be read from the GUI textbox
      final String state = getIn.nextLine();

      // Instantiate  the weather reader with this place name.  Note that you need a comma and space separating city and state
      System.out.println("Now checking " + city + ", " + state);
      final WeatherReader reader = new WeatherReader(city + ", " + state);

      // Read in some of the weather conditions
      final String conditions = reader.getConditions();
      final double temperature = reader.getTemperature();

      System.out.println("Conditions are " + conditions + " temperature is " + temperature + "F");

      // Set up variables for setting the LED so we only have to call setLED in one location
      int red = 0;
      int blue = 0;
      int green = 0;

      if (temperature < 20)
         {
         myFinch.saySomething("It's so cold my wings are falling off, it's" + temperature + " and " + conditions + " skies");
         blue = 255;
         }
      else if (temperature < 40)
         {
         myFinch.saySomething("Kinda chilly, time to migrate south,  it's" + temperature + " and " + conditions + " skies");
         // make it blue green, but skew towards blue
         blue = 128 + (int)(40 - temperature) * 6;
         green = (int)(temperature - 20) * 6;
         }
      else if (temperature < 60)
         {
         myFinch.saySomething("A brisk day, it's" + temperature + " and " + conditions + " skies");
         // blue green skewed towards green
         blue = (int)(60 - temperature) * 6;
         green = (int)(temperature - 40) * 6 + 128;
         }

      else if (temperature < 80)
         {
         myFinch.saySomething("Ideal weather for flying, it's" + temperature + " and " + conditions + " skies");
         // green-yellow
         red = (int)(temperature - 60) * 6;
         green = (int)(80 - temperature) * 6 + 128;
         }

      else if (temperature < 100)
         {
         myFinch.saySomething("Wow, time to head for cooler climes, it's " + temperature + " and " + conditions + " skies");
         // yellow to red
         red = (int)(80 - temperature) * 6 + 128;
         green = (int)(temperature - 60) * 6;
         }

      else
         {
         myFinch.saySomething("This sucks, I'm heading north, it's freaking" + temperature + " and " + conditions + " skies");
         // so hot it's just red
         red = 255;
         }
      myFinch.setLED(red, green, blue);
      // Give the Finch time to sleep and you time to see the LED
      myFinch.sleep(10000);

      // Always end your program with finch.quit()
      myFinch.quit();
      System.exit(0);
      }

   private WeatherFinch()
      {
      // private to prevent instantiation
      }
   }

