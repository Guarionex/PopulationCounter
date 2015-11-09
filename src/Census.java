import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * 
 * @author Edwin O. Martinez Velazquez
 * Algorithm for finding the year with the highest population
 *
 */
public class Census {

	/**
	 * Member variables
	 * 	HashMap population sets the zero gain count of birth and deaths
	 * 	HashMap census sets the population of all years within the specified range based on the birth death counts of population
	 * 	ArrayList people maintains a list of all the Person objects used for the Census
	 * 	boolean ranCensus determines the state of the Census
	 */
	private HashMap<Integer, Integer> population;
	private HashMap<Integer, Integer> census;
	private ArrayList<Person> people;
	private boolean ranCensus;
	/**
	 * Default constructor 
	 */
	public Census()
	{
		population = new HashMap<Integer, Integer>();
		census = new HashMap<Integer, Integer>();
		people = new ArrayList<Person>();
		ranCensus = false;
	}
	/**
	 * Constructor that takes in a List of Persons
	 * 	adds them all to the internal Person List and sets the population counter
	 * @param people : List of Person
	 */
	public Census(List<Person> people)
	{
		population = new HashMap<Integer, Integer>();
		census = new HashMap<Integer, Integer>();
		this.people = new ArrayList<Person>();
		ranCensus = false;
		this.addAllPerson(people);
		
	}
	/**
	 * Adds and individual Person to the population count 
	 * @param person : Person data
	 * @return true if adding was successfull
	 */
	public boolean addPerson(Person person)
	{
		
		Integer birthCount = population.get(person.getBirthYear());
		Integer deathCount = population.get(person.getDeathYear());
		
		//the value will be null if no entry exist, in which case make a new value
		if(birthCount == null) birthCount = 1;
		else birthCount += 1;
		if(deathCount == null) deathCount = -1;
		else deathCount -= 1;
		
		population.put(person.getBirthYear(), birthCount);
		population.put(person.getDeathYear(), deathCount);
		
		//the data change, need to run census again for accurate numbers
		ranCensus = false;
		
		return people.add(person);
	}
	/**
	 * Adds a List of Persons to the population count
	 * @param people : List of Person
	 * @return true if any changes where made, false if all additions fails
	 */
	public boolean addAllPerson(List<Person> people)
	{
		//re-use code for single Person adding, but follow Lists's addAll return rules
		boolean success = false;
		for(Person person : people)
		{			
			if(this.addPerson(person))
			{
				success = true;
			}
		}
		
		return success;
		
	}
	/**
	 * Removes a person from the count
	 * @param person : Person to remove
	 * @return true if remove was successful
	 */
	public boolean removePerson(Person person)
	{
		if(people.remove(person))
		{
			Integer birthCount = population.get(person.getBirthYear());
			Integer deathCount = population.get(person.getDeathYear());
			
			//Opposite of add
			if(birthCount == null) birthCount = -1;
			else birthCount -= 1;
			if(deathCount == null) deathCount = 1;
			else deathCount += 1;
			
			population.put(person.getBirthYear(), birthCount);
			population.put(person.getDeathYear(), deathCount);
			return true;
		}
		ranCensus = false;
		
		return false;
	}
	/**
	 * Removes a list of Person from the count
	 * @param people : List of Person
	 * @return true if any changes where made, false if all remove failed
	 */
	public boolean removeAllPerson(List<Person> people)
	{
		boolean success = false;
		for(Person person : people)
		{
			if(this.removePerson(person))
			{
				success = true;
			}
		}
		return success;
	}
	/**
	 * Gets the year that had the highest population
	 * @param year : key to get population from
	 * @return the population of the requested year
	 * @throws IllegalStateException if you haven't run the census
	 * @throws IllegalArgumentException if the year is outside of the census's range
	 */
	public int getPopulation(int year) throws IllegalStateException, IllegalArgumentException
	{
		//census map will not be populated to get a year, need to run first
		if(!ranCensus)
		{
			throw new IllegalStateException("You must first start the census");
		}
		Integer populationOnYear = census.get(year);
		if(populationOnYear == null)
		{
			throw new IllegalArgumentException("This year is outside the census data");
		}
		return populationOnYear;
	}
	/**
	 * Runs the census, populates the census map based on birth/death count and gets the year of highest population
	 * @param startYear : year to start counting from
	 * @param endYear : year to stop counting from
	 * @return The year with the highest population
	 * @throws IllegalArgumentException if the end year is before the start year
	 */
	public int startCensus(int startYear, int endYear) throws IllegalArgumentException
	{
		if(endYear < startYear)
		{
			throw new IllegalArgumentException("End year cannot be before start year");
		}
		//Populate the census map in the year range
		for(int i = startYear; i <= endYear; i++)
		{
			Integer birthDeathCounter = population.get(i);
			//it's null if a zero gain counter wasn't made for that year
			if(birthDeathCounter != null)
			{
				//Get the previous year's population and apply the counter
				Integer previousCensus = census.get(i - 1);
				if(previousCensus == null) previousCensus = 0;
				census.put(i, previousCensus + birthDeathCounter);
			}
			else
			{
				//simply transfer the previous year's population
				Integer previousCensus = census.get(i - 1);
				if(previousCensus == null) previousCensus = 0;
				census.put(i, previousCensus);
			}
		}
		
		ranCensus = true;
		return this.highestYear();
	}
	/**
	 * Releases the population for all years
	 * @return the String form of all the populations
	 * @throws IllegalStateException if you havent run the census
	 */
	public String getAllYears() throws IllegalStateException
	{
		if(!ranCensus)
		{
			throw new IllegalStateException("You must first start the census");
		}
		String data = "";
		for(Integer key : census.keySet())
		{
			data += "Population in " + key + ": " + census.get(key) + "\n";
		}
		return data;
	}
	/**
	 * Helper method to get key from value
	 * @return year of highest population / key in the map
	 */
	private int highestYear()
	{
		//Need to get the key from value in a 1:1 key -> value pair structure
		Integer biggestBirth = Integer.MIN_VALUE;
		Integer biggestYear = 0;
		//Loops for every entry
		for(Entry<Integer, Integer> entry : census.entrySet())
		{
			//finds the biggest population and keep track of the year
			if(biggestBirth < entry.getValue())
			{
				biggestBirth = entry.getValue();
				biggestYear = entry.getKey();
			}
		}
		
		return biggestYear;
	}
	
}
