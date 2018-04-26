import javafx.util.Pair;

import java.awt.*;
import java.util.HashSet;

public class GameField extends Rectangle
{
	private HashSet<Obstacle> obstacles = new HashSet<>();
	
	
	public GameField(int x, int y, int w, int h)
	{
		super(w, h);
		this.x = x;
		this.y = y;
		width = w;
		height = h;
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
		
		g.setColor(Color.red);
		g.translate(x, y);
		for(Obstacle o : obstacles)
		{
			o.draw(g);
		}
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
}
