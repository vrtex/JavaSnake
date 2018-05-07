

public class Clock
{
	
	private long lastRestart;
	
	public Clock()
	{
		lastRestart = System.nanoTime();
	}
	
	public Time restart()
	{
		Time toReturn = getElapsedTime();
		lastRestart = System.nanoTime();
		return toReturn;
	}
	
	public Time getElapsedTime()
	{
		return new Time(System.nanoTime() - lastRestart);
	}
	
	public String toString()
	{
		return "Clock with current time: " + String.valueOf(getElapsedTime().asSeconds());
	}
}
