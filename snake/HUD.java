import java.awt.*;

public class HUD
{
	Player parent;
	private int score;
	private Font font = Game.font;
	
	public HUD(Player p)
	{
		parent = p;
		score = parent.getScore();
		
	}
	
	public void update()
	{
		score = parent.getScore();
	}
	
	public void draw(Graphics2D g)
	{
		g.setFont(font);
		//g.setColor(Color.red);
		g.setColor(Color.black);
		g.drawString(String.valueOf(score), 0, Game.pieceSize.height);//Game.pieceSize.height);
	}
}
