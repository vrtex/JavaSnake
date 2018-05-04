import javafx.util.Pair;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

public class FieldInfo implements Serializable
{
	private static final long serialVersionUID = 1;
	
	public int x, y, width, height;
	public Dimension size;
	public LinkedList<Pair<Integer, Integer>> obstacles;
	public LinkedList<Integer> teleporters;
	
	public FieldInfo(int x, int y, int w, int h, Dimension s, LinkedList<Pair<Integer, Integer>> o, LinkedList<Integer> t)
	{
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		size = s;
		obstacles = o;
		teleporters = t;
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
