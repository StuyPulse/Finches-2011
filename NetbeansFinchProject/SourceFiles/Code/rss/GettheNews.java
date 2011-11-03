package Code.rss;

/**
 * Created by:  Tom Lauwers
 * Date:  2/16/2009
 * Uses the news reader to return stories which include a word being searched for.
 * This class only uses the RSS feeds - it doesn't use the Finch in any way
 */

import edu.cmu.ri.createlab.rss.readers.NewsReader;
import java.util.Scanner;

public class GettheNews
{

   public static void main(final String[] args)
   {
		NewsReader reader = new NewsReader(); // Make a news reader
		String word;
		Scanner s = new Scanner(System.in); // Make a scanner class
		
		System.out.print("Please enter a word or phrase to search the news for:  ");
		word = s.nextLine(); // User inputs the word to look for
		
		// The search method in the news class basically does this program for you - returns all entries that have that word in them
		System.out.println(reader.search(word));

		System.exit(0);

    }
}

