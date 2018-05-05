import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class DifficultySetting extends MenuOption
{
	private MenuOption settings[];
	private int numberOfOptions;
	int currentSetting;
	
	public DifficultySetting(int x, int y, String desc, Font f, Graphics2D g, int number)
	{
		super(x, y, desc, f, g);
		settings = new MenuOption[number];
		int width, height;
		FontMetrics fM = g.getFontMetrics();
		width = (int)fM.getStringBounds("99", g).getWidth() * 3;
		height = (int)fM.getStringBounds("99", g).getHeight() * 2;
		numberOfOptions = number;
		int yOffset = 0;
		int xOffset = 0;
		for(int i = 0; i < numberOfOptions; ++i)
		{
			if(i % 5 == 0 && i > 0)
			{
				yOffset += height;
				xOffset = width;
			}
			else
				xOffset += width;
			settings[i] = new MenuOption(
					x + xOffset + 15,
//					x + width * i + 15,
					y + (int)getGlobalBounds().height + 10 + yOffset,
					String.valueOf(i + 1),
					f,
					g);
		}
		
		for(int i = 0; i < numberOfOptions; ++i)
		{
			bounds = (Rectangle2D.Float)bounds.createUnion(settings[i].getGlobalBounds());
		}
		
		settings[0].activate();
		currentSetting = 0;
	}
	
	@Override
	public boolean getInput(GameEvent e)
	{
		boolean inside = super.getInput(e);
		if(!inside) return false;
		
		if(e.event.getID() == KeyEvent.KEY_PRESSED)
			return keyPressed((KeyEvent)e.event);
		
		if(!(e.event instanceof MouseEvent))
			return false;
		MouseEvent mEvent = (MouseEvent)e.event;
		//if(mEvent.getID() != MouseEvent.MOUSE_CLICKED) return false;
		
		int nextSetting = -1;
		
		for(int i = 0; i < numberOfOptions; ++i)
		{
			if(settings[i].getInput(e))
			{
				nextSetting = i;
				break;
			}
		}
		
		if(nextSetting != -1)
		{
			for(MenuOption s : settings)
				s.deactivate();
			settings[nextSetting].activate();
			currentSetting = nextSetting;
		}
		
		
		return true;
	}
	
	private boolean keyPressed(KeyEvent e)
	{
		if(e.getID() != KeyEvent.KEY_PRESSED) return false;
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			if(currentSetting > 0)
			{
				settings[currentSetting].deactivate();
				--currentSetting;
				settings[currentSetting].activate();
			}
			return true;
		case KeyEvent.VK_RIGHT:
			if(currentSetting < settings.length - 1)
			{
				settings[currentSetting].deactivate();
				++currentSetting;
				settings[currentSetting].activate();
			}
			return true;
		}
		return false;
	}
	
	
	
	public void draw(Graphics2D g)
	{
		super.draw(g);
		for(MenuOption s : settings)
		{
			s.draw(g);
		}
	}
	
	public int getCurrentSetting()
	{
		return currentSetting;
	}
}
