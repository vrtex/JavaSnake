import javafx.util.Pair;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ScoreState extends GameState
{
	
	public ScoreState(StateSystem p)
	{
		super(p);
		
	}
	
	@Override
	public boolean getInput(GameEvent e)
	{
		if(e.event.getID() != KeyEvent.KEY_PRESSED) return false;
		KeyEvent kEvent = (KeyEvent)e.event;
		if(kEvent.getKeyCode() != KeyEvent.VK_ESCAPE) return false;
		parent.deleteRequest(1);
		return true;
	}
	
	@Override
	public void update()
	{
	
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		g.setFont(Game.font);
		int x = 100;
		int y = 100;
		int height = g.getFontMetrics().getHeight();
		g.setColor(Color.gray);
		g.drawString("Name: ", x, y);
		g.drawString("Score: ", x + 200, y);
		
		y += height;
		for(Pair<String, Integer> e : Game.scores.getEntries())
		{
			y += height;
			g.setColor(Color.black);
			g.drawString(e.getKey(), x, y);
			g.setColor(Color.white);
			g.drawString(String.valueOf(e.getValue()), x + 200, y);
		}
	}
}
