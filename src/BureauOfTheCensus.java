import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Edwin O. Martinez Velazquez
 *	Generates a dataset of random people with random dates between 1900 and 2000
 *	then calculates the highest population and it's year
 */
public class BureauOfTheCensus {

	public static void main(String[] args)
	{
		String fileName = "";
		//generate txt file
		try {
			generatePeople(100);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		fileName = "listOfPeople.txt";
		List<String> allLines;
		//defaults to project's path
		try {
			allLines = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			allLines = new ArrayList<String>();
			e.printStackTrace();
		}
		//Parses the data set from text file to Person List
		ArrayList<Person> people = new ArrayList<Person>();
		for(String line : allLines)
		{
			String[] rawPerson = line.split("\\s+");
			String[] rawYears = rawPerson[1].split("-");
			people.add(new Person(rawPerson[0], Integer.parseInt(rawYears[0]), Integer.parseInt(rawYears[1])));
		}
		//Run the calculations
		Census census = new Census(people);
		int year = census.startCensus(1900, 2000);
		int population = census.getPopulation(year);
		//Result
		System.out.println("Biggest population was "+population+" in "+year);
		PrintWriter writer;
		try {
			writer = new PrintWriter("censusResults.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			writer = null;
			e.printStackTrace();
		}
		if(writer != null)
		{
			writer.println("Biggest population was "+population+" in "+year);
			for(String line : census.getAllYears().split("\n"))
			{
				writer.println(line);
			}
		}
		writer.close();
	}
	/**
	 * Helper method to generate random people with random birth and death years
	 * 	
	 * @param numberOfPeople : determines how many people to make
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private static void generatePeople(int numberOfPeople) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter("listOfPeople.txt", "UTF-8");
		Random r = new Random();
		//Sets range of the random numbers
		int Low = 1900;
		int High = 2001;
		for(int i = 0; i < numberOfPeople; i++)
		{
			//Will set birth year between 1900 and 2000, but set death year between birth year and 2000
			int birthYear = r.nextInt(High-Low) + Low;
			int deathYear = r.nextInt(High-birthYear) + birthYear;
			writer.println("Person" + i + " " + birthYear + "-" + deathYear);
		}
		
		writer.close();
	}
	
}
