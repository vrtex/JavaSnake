import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOver extends GameState
{
	private PlayState previous;
	private String lines[];
	private final int finalScore;
	private boolean scoreCheck = false;
	
	public GameOver(StateSystem p, PlayState prev, int score)
	{
		super(p);
		previous = prev;
		finalScore = score;
		String msg = "Game Over\n" +
				"    Press enter to try again\n" +
				"    Press escape to exit\n";
		lines = msg.split("\n");
		
		
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
			parent.deleteRequest(1);
			return true;
		case KeyEvent.VK_ENTER:
			parent.changeRequest(new PlayState(previous));
			return true;
		}
		return false;
	}
	
	@Override
	public void update()
	{
		if(scoreCheck) return;
		scoreCheck = true;
		if(finalScore > Game.scores.lowestScore())
			parent.addRequest(new ScoreInput(parent, finalScore));
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		previous.draw(g);
		
		g.setFont(Game.font);
		Composite previousComp = g.getComposite();
		g.setColor(Color.yellow);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g.fillRect(0, 0, Game.width, Game.height);
		
		g.setColor(Color.black);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		int x = 100;
		int y = 100;
		for(String l : lines)
			g.drawString(l, x, y += g.getFontMetrics().getHeight());
		
		g.setComposite(previousComp);
	}
}
