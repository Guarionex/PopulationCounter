/**
 * 
 * @author Edwin O. Martinez Velazquez
 *
 * Data structure for a person
 */
public class Person {

	/**
	 * Member variables of a Person
	 * 	includes a string name, int year of birth, and int year of death
	 * 	this values are encapsulated and can only be access in getters
	 */
	private String name;
	private int birth;
	private int death;
	
	/**
	 * Default constructor
	 * 	sets birth year to 1900 and death year to 2000
	 */
	public Person()
	{
		name = "";
		birth = 1900;
		death = 2000;
		
	}
	/**
	 * Constructor for a Person
	 * @param name : string name of the person
	 * @param birthYear : int year of birth
	 * @param deathYear : int year of death
	 * @throws IllegalArgumentException : throws exception if death year is before birth year
	 */
	public Person(String name, int birthYear, int deathYear) throws IllegalArgumentException
	{
		/*if(birthYear < 1900 || birthYear > 2000 || deathYear < 1900 || deathYear > 2000)
		{
			throw new IllegalArgumentException("Years must be between 1900 and 2000");
		}*/
		if(birthYear > deathYear)
		{
			throw new IllegalArgumentException("Person cannot die before been born");
		}
		this.name = name;
		birth = birthYear;
		death = deathYear;
	}
	/**
	 * Getter for birth year
	 * @return the birth year
	 */
	public int getBirthYear()
	{
		return birth;
	}
	/**
	 * Getter for death year
	 * @return the death year
	 */
	public int getDeathYear()
	{
		return death;
	}
	/**
	 * Getter for the person's name
	 * @return the person's name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Overrides the default toString method
	 */
	@Override
	public String toString()
	{
		return name + " " + birth + "-" + death;
	}
	/**
	 * Overrides the equals method
	 * 	defines what makes the objects equal
	 * 	Returns true if name, birth year, and end year are the same
	 */
	@Override
	public boolean equals(Object obj)
	{
		Person person = null;
		if(obj.getClass().equals(Person.class)) 
		{
			person = (Person) obj;
		}
	
		if(person == null)
		{
			return false;
		}
		else if(this.name.equals(person.getName()) && this.birth == person.getBirthYear() && this.death == person.getDeathYear())
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	
}
