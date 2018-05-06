import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseState extends GameState
{
	public PauseState(StateSystem p)
	{
		super(p);
		
	}
	
	@Override
	public boolean getInput(GameEvent e)
	{
		if(e.event.getID() != KeyEvent.KEY_PRESSED) return false;
		KeyEvent kEvent = (KeyEvent)e.event;
		switch(kEvent.getKeyCode())
		{
		case KeyEvent.VK_ESCAPE:
		case KeyEvent.VK_ENTER:
			parent.deleteRequest(1);
			return true;
		case KeyEvent.VK_Q:
			parent.deleteRequest(2);
			return true;
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
		g.setColor(Color.yellow);
		Composite prev = g.getComposite();
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3f));
		g.setColor(Color.yellow);
		g.fillRect(0, 0, Game.width, Game.height);
		
		g.setColor(Color.black);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
		g.setFont(Game.font);
		int height = g.getFontMetrics().getHeight();
		int y = 100;
		int x = 100;
		g.drawString("Game puased", x, y);
		y += height * 2;
		x += 50;
		g.drawString("Press escape or enter to continue", x, y);
		y += height;
		g.drawString("Press Q to exit", x, y);
		
		g.setComposite(prev);
	}
}
