import javafx.util.Pair;

import java.io.*;
import java.util.LinkedList;

public class ScoreTable implements Serializable
{
	private LinkedList<Pair<String, Integer>> entries = new LinkedList<>();
	private static final long serialVersionUID = 1;
	
	public int highestScore()
	{
		if(entries.isEmpty()) return 0;
		return entries.peekFirst().getValue();
	}
	
	public int lowestScore()
	{
		if(entries.isEmpty()) return 0;
		return entries.peekLast().getValue();
	}
	
	public void addEntry(String name, int score)
	{
		if(score < lowestScore() && entries.size() == 10) return;
		
		int i = 0;
		for(i = 0; i < entries.size(); ++i)
		{
			if(entries.get(i).getValue() < score)
				break;
		}
		
		entries.add(i, new Pair<>(name, score));
		
		while(entries.size() > 10)
			entries.removeLast();
	}
	
	public LinkedList<Pair<String, Integer>> getEntries()
	{
		return (LinkedList<Pair<String, Integer>>)entries.clone();
	}
	
	public String toString()
	{
		StringBuilder toReturn = new StringBuilder();
		
		for(Pair<String, Integer> e : entries)
		{
			toReturn.append(e.getKey());
			toReturn.append(" ");
			toReturn.append(e.getValue());
			toReturn.append("\n");
		}
		
		return toReturn.toString();
	}
	
	public void save()
	{
		try
		{
			FileOutputStream fStream = new FileOutputStream("Res\\scores.t");
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(this);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Scores not saved");
			System.exit(5643);
		}
	}
	
	public static ScoreTable load()
	{
		ScoreTable toReturn = null;
		try
		{
			FileInputStream fStream = new FileInputStream("Res\\scores.t");
			ObjectInputStream oStream = new ObjectInputStream(fStream);
			toReturn = (ScoreTable)oStream.readObject();
		}
		catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("Scores not loaded");
			System.exit(5643);
		}
		
		return toReturn;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
	}
}
