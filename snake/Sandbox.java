import java.io.*;

public class Sandbox
{
	public static void main(String args[])
	{
		
		ScoreTable table = ScoreTable.load();
		
		
		System.out.println(table.lowestScore());
//		table.addEntry("Krowa", 15);
//		table.addEntry("Baleron", 10);
//		table.addEntry("Huehue", 55);
		System.out.println(table);
		
		table.save();
	}
}
