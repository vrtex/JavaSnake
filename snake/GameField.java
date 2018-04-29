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
	public final Dimension size;
	//	private HashSet<Obstacle> obstacles = new HashSet<>();
	private Food food, bonusFood = null;
	private int bonusCountdown = 1;
	Time bonusTime = Time.seconds(3);
	Clock bonusTimer;
	
	
	public GameField(int x, int y, int w, int h)
	{
		super(w, h);
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		size = new Dimension(width / Game.pieceSize.width, height / Game.pieceSize.height);
		
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
			
			if(!Game.images.contains("bonusFood"))
			{
				nextImage = ImageIO.read(new File("Res\\bonusFood.png"));
				Game.images.insert("bonusFood", nextImage);
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(543656);
		}
		
		load();
		bonusTimer = new Clock();
		bonusTimer.restart();
		System.out.println("Bonus timer:");
		System.out.println(bonusTime);
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
	
	public void update()
	{
		
		if(bonusFood == null) return;
		if(bonusTimer.getElapsedTime().compareTo(bonusTime) < 0) return;
		System.out.printf("deleting food, timer: %s, time: %s\n", bonusTimer.getElapsedTime(), bonusTime);
		System.out.println(bonusTimer);
		bonusFood = null;
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
		if(bonusFood != null)
			bonusFood.draw(g);
		
		g.translate(-x, -y);
		
	}
	
	
	public void eatFood(int x, int y, LinkedList<Segment> tail)
	{
		if(containsFood(x, y) == 1 || food == null)
		{
			spawnFood(x, y, tail);
			--bonusCountdown;
		} else if(containsFood(x, y) == 5)
			bonusFood = null;
	}
	
	private void spawnFood(int x, int y, LinkedList<Segment> tail)
	{
		
		int fx, fy;
		do
		{
			fx = Game.rand.nextInt(Game.pieceCount.width - 1);
			fy = Game.rand.nextInt(Game.pieceCount.height - 1);
		} while(
				(
						fx == x && fy == y) ||
						tail.contains(new Segment(fx, fy)) ||
						obstacles.contains(new Obstacle(fx, fy)) ||
						containsFood(fx, fy) != 0
				);
		food = new Food(fx, fy, false);
		System.out.printf("bonus countown: %d\n", bonusCountdown);
		if(bonusCountdown != 0) return;
		spawnBonus(x, y, tail);
	}
	
	private void spawnBonus(int x, int y, LinkedList<Segment> tail)
	{
		bonusCountdown = 5;
		int fx, fy;
		do
		{
			fx = Game.rand.nextInt(Game.pieceCount.width - 1);
			fy = Game.rand.nextInt(Game.pieceCount.height - 1);
		} while(
				(
						fx == x && fy == y) ||
						tail.contains(new Segment(fx, fy)) ||
						obstacles.contains(new Obstacle(fx, fy)) ||
						containsFood(fx, fy) != 0
				);
		bonusFood = new Food(fx, fy, true);
		System.out.printf("spwaning bonus at %d %d\n", fx, fy);
		bonusTimer.restart();
	}
	
	public int containsFood(int x, int y)
	{
		if(food != null)
			if(food.x == x && food.y == y)
				return 1;
		
		if(bonusFood != null)
			if(bonusFood.x == x && bonusFood.y == y)
				return 5;
		
		return 0;
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
