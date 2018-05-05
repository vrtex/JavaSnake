import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOver extends GameState
{
	public GameOver(StateSystem p)
	{
		super(p);
	}
	
	@Override
	public boolean getInput(GameEvent e)
	{
		if(e.event.getID() == KeyEvent.KEY_PRESSED)
			return keyPressed((KeyEvent)e.event);
		return false;
	}
	
	private boolean keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_ESCAPE:
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
		g.setFont(Game.font);
		Composite previousComp = g.getComposite();
		g.setColor(Color.yellow);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g.fillRect(0, 0, Game.width, Game.height);
		
		g.setColor(Color.black);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		g.drawString("Game Over", 100, 100);
		
		g.setComposite(previousComp);
	}
}
