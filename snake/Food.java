

public class Food extends FieldElement
{

	public Food(int x, int y)
	{
		super(x, y);
		pic = Game.images.get("food");
	}
	
	
	public boolean equals(Object o)
	{
		return (super.equals(o) && (o instanceof Food));
	}
	
	
	public int hashCode()
	{
		return Game.pieceCount.height * x * 741 + y;
	}
}
