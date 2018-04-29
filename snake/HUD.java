import java.awt.*;

public class HUD
{
	Player parent;
	private int score;
	private Font font;
	
	public HUD(Player p)
	{
		parent = p;
		score = parent.getScore();
		Font f = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
		font = new Font(f.getName(), Font.PLAIN, 32);
		
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
