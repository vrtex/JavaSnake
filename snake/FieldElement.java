import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class FieldElement
{
	public int x, y;
	protected BufferedImage pic;
	
	public FieldElement(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(pic, x * Game.pieceSize.width, y * Game.pieceSize.height, null);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof FieldElement)) return false;
		return (x == ((FieldElement)obj).x && y == ((FieldElement)obj).y);
	}
	
	@Override
	public int hashCode()
	{
		return Game.pieceCount.height * x * 78 + y;
	}
}
