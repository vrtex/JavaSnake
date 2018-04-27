
// hello there
// general kenobi
// this will make a fine addition to my collection
// fuck I can't remember more
// fsdgf

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
