

public class Food extends FieldElement
{

	public Food(int x, int y)
	{
		super(x, y);
		pic = Game.images.get("food");
	}
	
	public Food(int x, int y, boolean bonus)
	{
		super(x, y);
		if(bonus)
			pic = Game.images.get("bonusFood");
		else
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
