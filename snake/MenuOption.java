import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class MenuOption
{
	private String description;
	private int x, y;
	private Font font;
	protected Rectangle2D.Float bounds;
	private Color activeColor, inactiveColor;
	private boolean active;
	
	
	public MenuOption(int x, int y, String desc, Font f, Graphics2D g)
	{
		description = desc;
		this.x = x;
		this.y = y;
		font = f;
		FontMetrics fMets = g.getFontMetrics(font);
		bounds = (Rectangle2D.Float)fMets.getStringBounds(description, g);
		bounds.x += x;
		bounds.y += y;
		
		active = false;
		
		activeColor = Color.gray;
		inactiveColor = Color.black;
		
	}
	
	public boolean getInput(GameEvent e)
	{
		if(e.event.getID() == MouseEvent.MOUSE_MOVED)
			return mouseMoved((MouseEvent)e.event);
		if(e.event.getID() == MouseEvent.MOUSE_CLICKED)
			return mouseClicked((MouseEvent)e.event);
		if(e.event.getID() == MouseEvent.MOUSE_RELEASED)
			return mouseReleased((MouseEvent)e.event);
		if(e.event.getID() == MouseEvent.MOUSE_PRESSED)
			return mousePressed((MouseEvent)e.event);
		
		if(e.event.getID() == KeyEvent.KEY_PRESSED)
			return true;
		
		return false;
		
	}
	
	protected boolean mouseClicked(MouseEvent e)
	{
		return bounds.contains(e.getX(), e.getY());
	}
	
	protected boolean mouseMoved(MouseEvent e)
	{
		active = bounds.contains(e.getX(), e.getY());
		return bounds.contains(e.getX(), e.getY());
	}
	
	protected boolean mouseReleased(MouseEvent e)
	{
		return mouseClicked(e);
	}
	
	protected boolean mousePressed(MouseEvent e)
	{
		return mouseClicked(e);
	}
	
	
	public void draw(Graphics2D g)
	{
		//g.drawRect((int)bounds.x, (int)bounds.y, (int)bounds.x + (int)bounds.width, (int)bounds.y + (int)bounds.height);
		if(active)
			g.setColor(activeColor);
		else
			g.setColor(inactiveColor);
		// g.draw(bounds);
		g.setFont(font);
		g.drawString(description, x, y);
	}
	
	public Rectangle2D.Float getGlobalBounds()
	{
		return bounds;
	}
	
	void activate()
	{
		active = true;
	}
	
	void deactivate()
	{
		active = false;
	}
}
