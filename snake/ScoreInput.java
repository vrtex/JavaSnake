import java.awt.*;
import java.awt.event.KeyEvent;

public class ScoreInput extends GameState
{
	private TextBox box;
	private int score;
	
	public ScoreInput(StateSystem parent, int s)
	{
		super(parent);
		System.out.println("huehue");
		box = new TextBox(Game.width / 2, Game.height / 2 + 30);
		score = s;
	}
	
	@Override
	public boolean getInput(GameEvent e)
	{
		if(box.getInput(e)) return true;
		if(e.event.getID() == KeyEvent.KEY_PRESSED)
		{
			KeyEvent kEvent = (KeyEvent)e.event;
			if(kEvent.getKeyCode() == KeyEvent.VK_ENTER)
			{
				System.out.println("enter");
				String name = box.toString();
				Game.scores.addEntry(name, score);
				Game.scores.save();
				parent.deleteRequest(1);
			}
		}
		return false;
	}
	
	@Override
	public void update()
	{
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		Composite previous = g.getComposite();
		
		g.setColor(Color.white);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
		g.fillRect(0, 0, Game.width, Game.height);
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
		g.setColor(Color.black);
		int x = Game.width / 2;
		int y = 300;
		g.drawString("You got a high score!", x, y);
		y += g.getFontMetrics().getHeight();
		g.drawString("Enter your name:", x, y);
		
		box.draw(g);
		
		
		g.setComposite(previous);
	}
}
