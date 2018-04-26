

public class Clock
{
	
	private long lastRestart;
	
	public Clock()
	{
		lastRestart = System.nanoTime();
	}
	
	public Time restart()
	{
		Time toReturn = new Time(System.nanoTime() - lastRestart);
		lastRestart = System.nanoTime();
		return toReturn;
	}
	
	public Time getElapsedTime()
	{
		return new Time(System.nanoTime() - lastRestart);
	}
}
