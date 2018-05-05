import java.awt.*;
import java.awt.event.KeyEvent;

public class TextBox extends Rectangle
{
	private StringBuilder currentText;
	private String currentString;
	boolean sizeSet = false;
	
	public TextBox(int x, int y)
	{
		this.x = x;
		this.y = y;
		currentText = new StringBuilder();
		currentString = "_";
	}
	
	public boolean getInput(GameEvent e)
	{
		if(!(e.event instanceof KeyEvent)) return false;
		KeyEvent kEvent = (KeyEvent)e.event;
		if(kEvent.getID() != KeyEvent.KEY_PRESSED) return false;
		
		if(kEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			if(currentText.length() <= 0)
				return true;
			
			currentText.deleteCharAt(currentText.length() - 1);
			updateString();
			return true;
		}
		if(Character.isDigit(kEvent.getKeyChar()) || Character.isAlphabetic(kEvent.getKeyChar()))
		{
			currentText.append(kEvent.getKeyChar());
			updateString();
		}
		return false;
	}
	
	public void draw(Graphics2D g)
	{
		g.setFont(Game.font);
		if(!sizeSet)
		{
			width = g.getFontMetrics().getWidths()['A'] * 5;
			height = g.getFontMetrics().getHeight();
		}
		Composite prev = g.getComposite();
		g.setColor(Color.blue);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.7f));
		g.fill(this);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
		g.setColor(Color.black);
		g.drawString(currentString, x + width / 5 , y + (int)(height * 0.9));
		g.setComposite(prev);
	}
	
	private void updateString()
	{
		if(currentText.length() < 3)
		{
			currentString = currentText.toString() + "_";
			return;
		}
		while(currentText.length() > 3)
			currentText.deleteCharAt(currentText.length() - 1);
		currentString = currentText.toString();
	}
	
	public String toString()
	{
		updateString();
		return currentString;
	}
}
