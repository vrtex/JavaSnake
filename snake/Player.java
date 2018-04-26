import javafx.util.Pair;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player
{
	
	private int offsetX, offsetY;
	private int x, y;
	private BufferedImage headPic;
	private Time moveDelay = Time.seconds(0.25); // for changing difficulty
	private Clock moveTimer;
	private int xDir, yDir;
	
	public Player(int x, int y, Pair<Integer, Integer> offset)
	{
		
		this.x = x;
		this.y = y;
		offsetX = offset.getKey();
		offsetY = offset.getValue();
		xDir = 1;
		yDir = 0;
		
		headPic = Game.images.get("head");
		moveTimer = new Clock();
	}
	
	public boolean getInput(GameEvent e)
	{
		if(e.type != GameEvent.EventType.key) return false;
		KeyEvent input = (KeyEvent)e.event;
		if(e.event.getID() != KeyEvent.KEY_PRESSED) return false;
		switch(input.getKeyCode())
		{
		case KeyEvent.VK_DOWN:
			xDir = 0;
			yDir = 1;
			return true;
		case KeyEvent.VK_UP:
			xDir = 0;
			yDir = -1;
			return true;
		case KeyEvent.VK_LEFT:
			xDir = -1;
			yDir = 0;
			return true;
		case KeyEvent.VK_RIGHT:
			xDir = 1;
			yDir = 0;
			return true;
			
		}
		return false;
	}
	
	public void update()
	{
		if(moveTimer.getElapsedTime().compareTo(moveDelay) < 0) return;
		moveTimer.restart();
		x += xDir;
		y += yDir;
	}
	
	public void draw(Graphics2D g)
	{
		g.setColor(Color.cyan);
		g.drawImage(
				headPic,
				null, // rotation ?
				x * Game.pieceSize.width + offsetX,
				y * Game.pieceSize.height + offsetY);
//		g.fillOval(
//				x * Game.pieceSize.width + offsetX,
//				y * Game.pieceSize.height + offsetY,
//				Game.pieceSize.width,
//				Game.pieceSize.height);
	}
}
