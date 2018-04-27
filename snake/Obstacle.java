import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Obstacle extends FieldElement
{
	public Obstacle(int x, int y)
	{
		super(x, y);
			pic = Game.images.get("block");
	}
	
	
	public boolean equals(Object o)
	{
		return (super.equals(o) && (o instanceof Obstacle));
	}
	
	
	public int hashCode()
	{
		return Game.pieceCount.height * x * 48 + y;
	}
}
