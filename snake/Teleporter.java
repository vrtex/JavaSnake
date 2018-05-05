import javafx.util.Pair;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

public class Teleporter
{
	class Exit extends FieldElement
	{
		public Exit(int x, int y, char color)
		{
			super(x, y);
			pic = Game.images.get("teleporter" + color);
		}
	}
	
	private Teleporter.Exit exitA, exitB;
	private char color;
	
	public Teleporter(int xa, int ya, int xb, int yb, char color)
	{
		exitA = new Teleporter.Exit(xa, ya, color);
		exitB = new Teleporter.Exit(xb, yb, color);
		this.color = color;
	}
	
	public void draw(Graphics2D g)
	{
		exitA.draw(g);
		exitB.draw(g);
	}
	
	public Pair<Integer, Integer> getExit(int x, int y)
	{
		if(x == exitA.x && y == exitA.y) return new Pair<>(exitB.x, exitB.y);
		if(x == exitB.x && y == exitB.y) return new Pair<>(exitA.x, exitA.y);
		return null;
	}
	
	public String toString()
	{
		return "Teleporter at: " + String.valueOf(exitA.x) + " " + String.valueOf(exitA.y) + " " + String.valueOf(exitB.x) + " " + String.valueOf(exitA.y) + " ";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Teleporter)) return false;
		Teleporter other = (Teleporter)obj;
		return other.exitB.equals(exitB) && other.exitA.equals(exitA);
	}
	
	@Override
	public int hashCode()
	{
		return exitA.hashCode() + exitB.hashCode() * 17;
	}
	
	public void getInfo(LinkedList<Integer> list)
	{
		list.addLast(exitA.x);
		list.addLast(exitA.y);
		list.addLast(exitB.x);
		list.addLast(exitB.y);
	}
	
	public char getColor()
	{
		return color;
	}
}
