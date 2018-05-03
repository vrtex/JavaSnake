import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class MainMenu extends GameState
{
	private MenuOption options[];
	// start game
	// difficulty
	// exit
	
	private final int startPosition = 0, exitPosition = 2;
	private int currentOption;
	
	public MainMenu(StateSystem p, Graphics2D g)
	{
		super(p);
		
		Font f = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
		Font font = new Font(f.getName(), Font.PLAIN, 32);
		options = new MenuOption[3];
		
		int x, y;
		
		options[startPosition] = new MenuOption(100, 100, "Start Game", font, g);
		Rectangle2D.Float b = options[0].getGlobalBounds();
		x = (int)b.x;
		y = (int)(b.y + b.height) + 50;
		
		options[1] = new DifficultySetting(x, y, "Choose difficulty", font, g);
		b = options[1].getGlobalBounds();
		x = (int)b.x;
		y = (int)(b.y + b.height) + 50;
		
		options[exitPosition] = new MenuOption(x, y, "Exit", font, g);
		
		currentOption = 0;
		options[currentOption].activate();
	}
	
	@Override
	public boolean getInput(GameEvent e)
	{
		
		// kkey pressed:
		if(e.event.getID() == KeyEvent.KEY_PRESSED)
		{
			KeyEvent kEvent = (KeyEvent)e.event;
			
			boolean validKey = keyPressed(kEvent);
			
			
			options[currentOption].activate();
			if(!validKey)
				validKey = options[currentOption].getInput(e);
			return validKey;
		}
		
		// mouse event:
		if(e.event.getID() == MouseEvent.MOUSE_MOVED)
		{
			MouseEvent mEvent = (MouseEvent)e.event;
			int nextOption = -1;
			int i = 0;
			for(MenuOption o : options)
			{
				if(o == null) continue;
				if(o.getGlobalBounds().contains(mEvent.getX(), mEvent.getY()))
				{
					nextOption = i;
					break;
				}
				++i;
				
			}
			if(nextOption == -1) return true;
			
			for(MenuOption o : options)
			{
				if(o == null) continue;
				o.deactivate();
			}
			options[currentOption].activate();
			currentOption = nextOption;
			return true;
		}
		
		
		if(options[startPosition].getInput(e)) // start game
		{
			startGame();
			return true;
		}
		
		if(options[exitPosition].getInput(e)) // end game
		{
			exit();
			return true;
		}
		
		if(options[1].getInput(e)) return true;
		
		return false;
	}
	
	private boolean keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			if(currentOption > 0)
			{
				options[currentOption].deactivate();
				--currentOption;
			}
			return true;
		case KeyEvent.VK_DOWN:
			if(currentOption < options.length - 1)
			{
				options[currentOption].deactivate();
				++currentOption;
			}
			return true;
		case KeyEvent.VK_ENTER:
			if(currentOption == startPosition) // start game
				startGame();
			else if(currentOption == exitPosition) // end game
				exit();
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
		for(MenuOption o : options)
		{
			if(o == null) continue;
			o.draw(g);
		}
	}
	
	private void startGame()
	{
		parent.addRequest(new PlayState(parent, ((DifficultySetting)options[1]).getCurrentSetting()));
	}
	
	private void exit()
	{
		System.exit(33);
	}
}
