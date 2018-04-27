import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Player
{
	
	private int x, y;
	private BufferedImage headPic;
	private Time moveDelay = Time.seconds(0.25); // for changing difficulty
	private Clock moveTimer;
	private int xDir, yDir;
	private LinkedList<Segment> tail = new LinkedList<>();
	private int toAdd;
	private boolean dead;
	private Obstacle testObstacle;
	private GameField field;
	
	public Player(int x, int y, GameField f)
	{
		
		this.x = x;
		this.y = y;
		xDir = 1;
		yDir = 0;
		
		field = f;
		
		dead = false;
		
		headPic = Game.images.get("head");
		if(!Game.images.contains("tail"))
		{
			try
			{
				Game.images.insert("tail", ImageIO.read(new File("Res\\debug.png")));
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(234);
			}
		}
		
		toAdd = 5;
		
		testObstacle = new Obstacle(x, y);
		
		moveTimer = new Clock();
		
		field.spawnFood(x, y, tail);
	}
	
	public boolean getInput(GameEvent e)
	{
		if(e.type != GameEvent.EventType.key) return false;
		KeyEvent input = (KeyEvent)e.event;
		if(e.event.getID() != KeyEvent.KEY_PRESSED) return false;
		if(dead) return false;
		switch(input.getKeyCode())
		{
		case KeyEvent.VK_DOWN:
			if(xDir == 0) return true;
			xDir = 0;
			yDir = 1;
			return true;
		case KeyEvent.VK_UP:
			if(xDir == 0) return true;
			xDir = 0;
			yDir = -1;
			return true;
		case KeyEvent.VK_LEFT:
			if(yDir == 0) return true;
			xDir = -1;
			yDir = 0;
			return true;
		case KeyEvent.VK_RIGHT:
			if(yDir == 0) return true;
			xDir = 1;
			yDir = 0;
			return true;
			
		}
		return false;
	}
	
	public void update()
	{
		if(dead) return;
		if(moveTimer.getElapsedTime().compareTo(moveDelay) < 0) return;
		moveTimer.restart();
		tail.addFirst(new Segment(x, y));
		x += xDir;
		y += yDir;
		
		
		if(toAdd > 0)
			--toAdd;
		else
			tail.removeLast();
		
		testObstacle.x = x;
		testObstacle.y = y;
		
		if(tail.contains(new Segment(x, y))) die();
		if(!field.isFree(testObstacle)) die();
		
		if(field.containsFood(x, y))
		{
			++toAdd;
			field.spawnFood(x, y, tail);
		}
	}
	
	public void draw(Graphics2D g)
	{
		for(Segment s : tail)
		{
			s.draw(g);
		}
		
		g.drawImage(
				headPic,
				null, // rotation ?
				x * Game.pieceSize.width,
				y * Game.pieceSize.height);
	}
	
	public void die()
	{
		dead = true;
		headPic = Game.images.get("headDead");
	}
	
	public  boolean isDead()
	{
		return dead;
	}
}