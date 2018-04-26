import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Obstacle
{
	public int x, y;
	private BufferedImage blockPic;
	
	public Obstacle(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		if(!Game.images.contains("block"))
		{
			try
			{
				blockPic = ImageIO.read(new File("Res\\block.png"));
				Game.images.insert("block", blockPic);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(34);
			}
		}
		else
			blockPic = Game.images.get("block");
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(blockPic, x * Game.pieceSize.width, y * Game.pieceSize.height, null);
	}
	
	public boolean equals(Object o)
	{
		if(!(o instanceof Obstacle)) return false;
		Obstacle other = (Obstacle)o;
		return ((x == other.x) && (y == other.y));
	}
	
	
	public int hashCode()
	{
		return Game.pieceCount.height * x + y;
	}
}
