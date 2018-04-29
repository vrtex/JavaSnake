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
	private int score = 0;
	private BufferedImage headPic;
	private BufferedImage head[];
	
	private Time moveDelay = Time.seconds(0.25); // for changing difficulty
	private Clock moveTimer;
	private int xDir, yDir;
	private LinkedList<Segment> tail = new LinkedList<>();
	private int toAdd;
	private boolean dead;
	private Obstacle testObstacle;
	private GameField field;
	private int rotation;
	private int newRotation;
	
	public Player(int x, int y, GameField f)
	{
		
		this.x = x;
		this.y = y;
		rotation = 0;
		newRotation = rotation;
		xDir = 1;
		yDir = 0;
		
		field = f;
		
		dead = false;
		
		head = new BufferedImage[3 * 4];
		char directions[] = {'R', 'D', 'L', 'U'};
		String temp = "head";
		for(int i = 0; i < 4; ++i)
		{
			head[i] = Game.images.get(temp + directions[i]);
		}
		temp = "headDead";
		for(int i = 0; i < 4; ++i)
		{
			head[i + 4] = Game.images.get(temp + directions[i]);
		}
		temp = "headOpen";
		for(int i = 0; i < 4; ++i)
		{
			head[i + 8] = Game.images.get(temp + directions[i]);
		}
		
		headPic = head[rotation];
		if(!Game.images.contains("debug"))
		{
			try
			{
				Game.images.insert("debug", ImageIO.read(new File("Res\\debug.png")));
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
		
		field.eatFood(x, y, tail);
	}
	
	public boolean getInput(GameEvent e)
	{
		if(e.type != GameEvent.EventType.key) return false;
		KeyEvent input = (KeyEvent)e.event;
		if(e.event.getID() != KeyEvent.KEY_PRESSED) return false;
		if(dead) return false;
		if(rotate(input.getKeyCode())) return true;
		return false;
	}
	
	public void update()
	{
		if(dead) return;
		if(moveTimer.getElapsedTime().compareTo(moveDelay) < 0) return;
		moveTimer.restart();
		move();
		
		testObstacle.x = x;
		testObstacle.y = y;
		
		if(tail.contains(new Segment(x, y))) die();
		if(!field.isFree(testObstacle)) die();
		
		if(field.containsFood(x, y) != 0)
		{
			++toAdd;
			score += field.containsFood(x, y);
			field.eatFood(x, y, tail);
		}
		
		pickHead();
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
		headPic = head[rotation + 4];
	}
	
	public  boolean isDead()
	{
		return dead;
	}
	
	private boolean rotate(int code)
	{
		switch(code)
		{
		case KeyEvent.VK_RIGHT:
			if(rotation % 2 == 0) return true;
			xDir = 1;
			yDir = 0;
			newRotation = 0;
			return true;
		case KeyEvent.VK_DOWN:
			if(rotation % 2 != 0) return true;
			xDir = 0;
			yDir = 1;
			newRotation = 1;
			return true;
		case KeyEvent.VK_LEFT:
			if(rotation % 2 == 0) return true;
			xDir = -1;
			yDir = 0;
			newRotation = 2;
			return true;
		case KeyEvent.VK_UP:
			if(rotation % 2 != 0) return true;
			xDir = 0;
			yDir = -1;
			newRotation = 3;
			return true;
			
		}
		return false;
	}
	
	private void move()
	{
		tail.addFirst(new Segment(x, y, getNextImage()));
		
		
		
		x += xDir;
		y += yDir;
		
		x = (x + field.size.width) % field.size.width;
		y = (y + field.size.height) % field.size.height;
		
		rotation = newRotation;
		
		if(toAdd > 0)
			--toAdd;
		else
			tail.removeLast();
		
		headPic = head[rotation];
		
	}
	
	private void pickHead()
	{
		if(dead)
		{
			headPic = head[4 + rotation];
			return;
		}
		
		if(field.containsFood((x + xDir + field.size.width) % field.size.width, (y + yDir + field.size.height) % field.size.height) != 0)
		{
			headPic = head[8 + rotation];
			return;
		}
		
		headPic = head[rotation];
	}
	
	private BufferedImage getNextImage()
	{
		if(newRotation == rotation)
		{
			if(rotation % 2 == 1)
			{
				if(Game.rand.nextBoolean())
					return Game.images.get("tailV1");
				else
					return Game.images.get("tailV2");
			}
			else
			{
				if(Game.rand.nextBoolean())
					return Game.images.get("tailH1");
				else
					return Game.images.get("tailH2");
			}
			
		}
		switch(rotation)
		{
		case 2:
			if(newRotation == 1)
				return Game.images.get("tailRB");
			else
				return Game.images.get("tailRT");
		case 3:
			if(newRotation == 0)
				return Game.images.get("tailRB");
			else
				return Game.images.get("tailLB");
		case 0:
			if(newRotation == 1)
				return Game.images.get("tailLB");
			else
				return Game.images.get("tailLT");
		case 1:
			if(newRotation == 2)
				return Game.images.get("tailLT");
			else
				return Game.images.get("tailRT");
		}
		return Game.images.get("debug");
	}
	
	public int getScore()
	{
		return score;
	}
}
