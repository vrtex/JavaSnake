public class Time implements Comparable<Time>
{
	public static final int NANOS_IN_SECOND = 1000000000;
	public static final int MICROS_IN_SECOND = 1000000;
	public static final int MILLIS_IN_SECOND = 1000;
	
	long period;

	public Time(long nanoseconds)
	{
		period = nanoseconds;
	}
	
	public static Time zero()
	{
		return new Time(0);
	}
	
	public long asNanoseconds()
	{
		return period;
	}
	
	public long asMillisecond()
	{
		return period / 1000000;
	}
	
	public double asSeconds()
	{
		return (double)period / Time.NANOS_IN_SECOND;
	}
	
	public int compareTo(Time o)
	{
		if(period < o.period) return -1;
		if(period > o.period) return 1;
		return 0;
		//return (int)(period - o.period);
	}
	
	public static Time seconds(double seconds)
	{
		return new Time((long)(NANOS_IN_SECOND * seconds));
	}
	
	public static Time milliseconds(int millis)
	{
		return new Time(1000000 * millis);
	}
	
	public String toString()
	{
		return String.valueOf(period);
	}
}
