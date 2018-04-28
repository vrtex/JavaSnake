import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class GameField extends Rectangle
{
	private ObstacleSet obstacles = new ObstacleSet();
//	private HashSet<Obstacle> obstacles = new HashSet<>();
	private Food food;
	private Random rand = new Random(System.nanoTime());
	
	
	public GameField(int x, int y, int w, int h)
	{
		super(w, h);
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		
		BufferedImage nextImage;
		try
		{
			if(!Game.images.contains("block"))
			{
				nextImage = ImageIO.read(new File("Res\\block.png"));
				Game.images.insert("block", nextImage);
			}
			
			if(!Game.images.contains("food"))
			{
				nextImage = ImageIO.read(new File("Res\\food.png"));
				Game.images.insert("food", nextImage);
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(543656);
		}
		
		load();
	}
	
	public void addObstacle(Obstacle o)
	{
		if(obstacles.contains(o)) return;
		obstacles.add(o);
	}
	
	public boolean isFree(Obstacle place)
	{
		if(obstacles.size() == 0) return false;
		return !obstacles.contains(place);
	}
	
	public void draw(Graphics2D g)
	{
		
		g.setColor(Color.black);
		g.fill(this);
		
		g.translate(x, y);
		
		for(Obstacle o : obstacles)
		{
			o.draw(g);
		}
		
		if(food != null)
			food.draw(g);
		
		g.translate(-x, -y);
		
	}
	
	public Pair<Integer, Integer> getPosition()
	{
		return new Pair<>(x, y);
	}
	
	public Dimension getSize()
	{
		return new Dimension(width, height);
	}
	
	public void spawnFood(int x, int y, LinkedList<Segment> tail)
	{
		int fx, fy;
		do
		{
			fx = rand.nextInt(Game.pieceCount.width - 1);
			fy = rand.nextInt(Game.pieceCount.height - 1);
		}while((fx == x && fy == y) || tail.contains(new Segment(fx, fy)) || obstacles.contains(new Obstacle(fx, fy)));
		food = new Food(fx, fy);
	}
	
	public boolean containsFood(int x, int y)
	{
		return food.x == x && food.y == y;
	}
	
	public void save()
	{
		
		LinkedList<Pair<Integer, Integer>> list = new LinkedList<>();
		for(Obstacle o : obstacles)
		{
			Pair p = new Pair<>(o.x, o.y);
			list.push(p);
		}
		
		try
		{
			FileOutputStream f = new FileOutputStream("l0.lvl");
			ObjectOutputStream oStream = new ObjectOutputStream(f);
			oStream.writeObject(list);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(555);
		}
	}
	
	public void load()
	{
		LinkedList<Pair<Integer, Integer>> list = null;
		try
		{
			FileInputStream f = new FileInputStream("l0.lvl");
			ObjectInputStream iStream = new ObjectInputStream(f);
			list = (LinkedList<Pair<Integer, Integer>>)iStream.readObject();
		}
		catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(555);
		}
		
		while(!list.isEmpty())
		{
			Pair<Integer, Integer> p = list.pop();
			addObstacle(new Obstacle(p.getKey(), p.getValue()));
		}
	}
}
