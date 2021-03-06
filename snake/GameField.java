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
	private LinkedList<Teleporter> teleporters = new LinkedList<>();
	
	public Dimension size;
	
	//	private HashSet<Obstacle> obstacles = new HashSet<>();
	private Food food, bonusFood = null;
	private int bonusCountdown = 1;
	private Time bonusTime = Time.seconds(5);
	private Clock bonusTimer;
//	public static char teleporterColors[] = new char[] {'R', 'B', 'G'};
	private LinkedList<Character> avalibleColors = new LinkedList<>();
	private LinkedList<Character> usedColors = new LinkedList<>();
//	private int nextTeleporter = 0;
	
	public GameField(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		size = new Dimension(width / Game.pieceSize.width, height / Game.pieceSize.height);
		
		avalibleColors.addLast('R');
		avalibleColors.addLast('B');
		avalibleColors.addLast('G');
		
		loadFieldImages();
		bonusTimer = new Clock();
		bonusTimer.restart();
	}
	
	public GameField(int n) // loading from file
	{
		avalibleColors.addLast('R');
		avalibleColors.addLast('B');
		avalibleColors.addLast('G');
		
		load(n);
		
		loadFieldImages();
		
		
		bonusTimer = new Clock();
		bonusTimer.restart();
	}
	
	
	public void addObstacle(Obstacle o)
	{
		if(obstacles.contains(o)) return;
		obstacles.add(o);
	}
	
	public void removeObstacle(Obstacle o)
	{
		obstacles.remove(o);
	}
	
	public void addTeleporter(int xa, int ya, int xb, int yb) throws RuntimeException
	{
//		if(nextTeleporter == teleporterColors.length) throw new RuntimeException();
		if(avalibleColors.isEmpty()) throw new RuntimeException();
		
//		Teleporter toAdd = new Teleporter(xa, ya, xb, yb, teleporterColors[nextTeleporter]);
		char col = avalibleColors.removeFirst();
		usedColors.addLast(col);
		Teleporter toAdd = new Teleporter(xa, ya, xb, yb, col);
		if(teleporters.contains(toAdd)) return;
		teleporters.add(toAdd);
//		++nextTeleporter;
	}
	
	public void removeTeleporter(int x, int y)
	{
		Teleporter toRemove = null;
		for(Teleporter t : teleporters)
		{
			if(t.getExit(x, y) != null)
			{
				toRemove = t;
				break;
			}
		}
		
		if(toRemove == null) return;
		
		
		teleporters.remove(toRemove);
		usedColors.remove((Character)toRemove.getColor());
		avalibleColors.addFirst((Character)toRemove.getColor());
//		--nextTeleporter;
		
	}
	
	public Pair<Integer, Integer> teleportsFrom(int x, int y)
	{
		Pair<Integer, Integer> exit = null;
		for(Teleporter t : teleporters)
		{
			exit = t.getExit(x, y);
			if(exit != null) break;
		}
		return exit;
	}
	
	public boolean isFree(Obstacle place)
	{
		if(obstacles.size() == 0) return true;
		return !obstacles.contains(place);
	}
	
	public void update()
	{
		
		if(bonusFood == null) return;
		if(bonusTimer.getElapsedTime().compareTo(bonusTime) < 0) return;
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
		
		for(Teleporter t : teleporters)
		{
			t.draw(g);
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
		if(obstacles.size() + tail.size() == this.size.width * this.size.height)
			return;
		
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
		if(bonusCountdown != 0) return;
		spawnBonus(x, y, tail);
	}
	
	private void spawnBonus(int x, int y, LinkedList<Segment> tail)
	{
		if(obstacles.size() + tail.size() >= this.size.width * this.size.height - 5)
			return;
		
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
	
	@Deprecated
	public void save()
	{
		
		LinkedList<Pair<Integer, Integer>> list = new LinkedList<>();
		
		Pair pos = new Pair<>(x, y);
		list.addLast(pos);
		Pair size = new Pair<>(getSize().width, getSize().height);
		list.addLast(size);
		
		for(Obstacle o : obstacles)
		{
			Pair p = new Pair<>(o.x, o.y);
			list.addLast(p);
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
	
	public void save(int n)
	{
		
		LinkedList<Pair<Integer, Integer>> list = new LinkedList<>();
		LinkedList<Integer> teleporterList = new LinkedList<>();
		
		for(Obstacle o : obstacles)
		{
			Pair p = new Pair<>(o.x, o.y);
			list.addLast(p);
		}
		
		for(Teleporter t : teleporters)
		{
			t.getInfo(teleporterList);
		}
		
		FieldInfo toSave = new FieldInfo(x, y, width, height, size, list, teleporterList);
		
		try
		{
			FileOutputStream f = new FileOutputStream("lvl\\l" + String.valueOf(n) + ".lvl");
			ObjectOutputStream oStream = new ObjectOutputStream(f);
			oStream.writeObject(toSave);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(555);
		}
	}
	
	@Deprecated
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
	
	
	private void load(int n)
	{
		LinkedList<Pair<Integer, Integer>> list = null;
		LinkedList<Integer> teleporterList = null;
		
		FieldInfo loaded = null;
		
		try
		{
			FileInputStream f = new FileInputStream("lvl\\l" + String.valueOf(n) + ".lvl");
			ObjectInputStream iStream = new ObjectInputStream(f);
			loaded = (FieldInfo)iStream.readObject();
			
		}
		catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(555);
		}
		
		if(loaded == null) throw new NullPointerException();
		
		x = loaded.x;
		y = loaded.y;
		width = loaded.width;
		height = loaded.height;
		size = loaded.size;
		
		list = loaded.obstacles;
		teleporterList = loaded.teleporters;
		
		
		
		if(teleporterList.size() % 4 != 0) throw new RuntimeException();
		
		
		while(!list.isEmpty())
		{
			Pair<Integer, Integer> p = list.pop();
			addObstacle(new Obstacle(p.getKey(), p.getValue()));
		}
		
		
		while(!teleporterList.isEmpty())
		{
			int xa, xb, ya, yb;
			xa = teleporterList.removeFirst();
			ya = teleporterList.removeFirst();
			xb = teleporterList.removeFirst();
			yb = teleporterList.removeFirst();
			
			addTeleporter(xa, ya, xb, yb);
		}
	}
	
	public static void loadFieldImages()
	{
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
			
			
			if(!Game.images.contains("teleporter"))
			{
				BufferedImage baseTeleporter = ImageIO.read(new File("Res\\teleporter.png"));
				Game.images.insert("teleporter", baseTeleporter);
				nextImage = Game.dye(baseTeleporter, Color.RED);
				Game.images.insert("teleporterR", nextImage);
				nextImage = Game.dye(baseTeleporter, Color.blue);
				Game.images.insert("teleporterB", nextImage);
				nextImage = Game.dye(baseTeleporter, Color.green);
				Game.images.insert("teleporterG", nextImage);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(543656);
		}
	}
}
