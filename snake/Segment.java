import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Segment
{
	private int x, y;
	private BufferedImage pic;
	
	public Segment(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		pic = Game.images.get("tail");
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(pic, x * Game.pieceSize.width, y * Game.pieceSize.height, null);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Segment)) return false;
		Segment other = (Segment)obj;
		return (x == other.x && y == other.y);
	}
	
	@Override
	public int hashCode()
	{
		
		return Game.pieceCount.height * x * 50 + y;
	}
}
