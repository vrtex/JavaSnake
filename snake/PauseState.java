import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class PauseState extends GameState
{
	private MenuOption options[] = new MenuOption[2];
	private int currentOption = 0;
	
	public PauseState(StateSystem p)
	{
		super(p);
		
		int height = parent.graphics.getFontMetrics(Game.font).getHeight();
		
		int y = 100 + height * 2;
		int x = 100;
		
		options[0] = new MenuOption(x, y, "Continue", Game.font, parent.graphics);
		Rectangle2D.Float b = options[0].getGlobalBounds();
		
		y += height;
		
		options[1] = new MenuOption(x, y, "Exit", Game.font, parent.graphics);
		
		options[0].activate();
	}
	
	@Override
	public boolean getInput(GameEvent e)
	{
		if(e.event.getID() != KeyEvent.KEY_PRESSED) return false;
		KeyEvent kEvent = (KeyEvent)e.event;
		switch(kEvent.getKeyCode())
		{
		case KeyEvent.VK_ENTER:
			switch(currentOption)
			{
			case 0:
				parent.deleteRequest(1);
				return true;
			case 1:
				parent.deleteRequest(2);
				return true;
			}
			return true;
		case KeyEvent.VK_ESCAPE:
			parent.deleteRequest(1);
			return true;
		case KeyEvent.VK_Q:
			parent.deleteRequest(2);
			return true;
		case KeyEvent.VK_DOWN:
			++currentOption;
			currentOption = (currentOption + options.length) % options.length;
			for(MenuOption o : options)
			{
				o.deactivate();
			}
			options[currentOption].activate();
			return true;
		case KeyEvent.VK_UP:
			--currentOption;
			currentOption = (currentOption + options.length) % options.length;
			for(MenuOption o : options)
			{
				o.deactivate();
			}
			options[currentOption].activate();
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
//
//		g.setColor(Color.black);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
//		g.setFont(Game.font);
//		int height = g.getFontMetrics().getHeight();
		int y = 100;
		int x = 100;
		g.drawString("Game paused", x, y);
//		y += height * 2;
//		x += 50;
//		g.drawString("Press escape or enter to continue", x, y);
//		y += height;
//		g.drawString("Press Q to exit", x, y);
		
		
		for(MenuOption o : options)
		{
			o.draw(g);
		}
		
		g.setComposite(prev);
	}
}
