import javafx.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player
{
	
	private int offsetX, offsetY;
	private int x, y;
	private long lastMove;
	private ResManager<BufferedImage> images = Game.images;
	private BufferedImage headPic;
	
	public Player(int x, int y, Pair<Integer, Integer> offset)
	{
		
		this.x = x;
		this.y = y;
		offsetX = offset.getKey();
		offsetY = offset.getValue();
		lastMove = System.nanoTime();
		
		headPic = images.get("head");
	}
	
	public void update()
	{
		// todo make timer
		if(System.nanoTime() - lastMove < 1000000000) return;
		lastMove = System.nanoTime();
		++x;
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
