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
	// levels
	// exit
	
	private final int optionsCount = 5;
	private final int
			startPosition = 0,
			difficultyPosition = 1,
			levelPosition = 2,
			scoresPosition = 3,
			exitPosition = optionsCount - 1;
	private int currentOption;
	
	private boolean visible = true;
	
	public MainMenu(StateSystem p, Graphics2D g)
	{
		super(p);
		
		Font font = Game.font;
		
		options = new MenuOption[optionsCount];
		
		int x, y;
		
		options[startPosition] = new MenuOption(100, 100, "Start Game", font, g);
		Rectangle2D.Float b = options[startPosition].getGlobalBounds();
		x = (int)b.x;
		y = (int)(b.y + b.height) + 50;
		
		options[difficultyPosition] = new DifficultySetting(x, y, "Choose difficulty", font, g, 4);
		b = options[difficultyPosition].getGlobalBounds();
		x = (int)b.x;
		y = (int)(b.y + b.height) + 50;
		
		options[levelPosition] = new DifficultySetting(x, y, "Pick level", font, g, 5);
		b = options[levelPosition].getGlobalBounds();
		x = (int)b.x;
		y = (int)(b.y + b.height) + 50;
		
		options[scoresPosition] = new MenuOption(x, y, "High scores", font, g);
		b = options[scoresPosition].getGlobalBounds();
		x = (int)b.x;
		y = (int)(b.y + b.height) + 50;
		
		options[exitPosition] = new MenuOption(x, y, "Exit", font, g);
		
		currentOption = 0;
		options[currentOption].activate();
	}
	
	@Override
	public boolean getInput(GameEvent e)
	{
		
		// key pressed:
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
			if(mouseMoved(mEvent))
				return true;
		}
		
		if(e.event.getID() == MouseEvent.MOUSE_CLICKED)
		{
			MouseEvent mEvent = (MouseEvent)e.event;
			if(mouseClicked(mEvent))
				return true;
			else if(options[currentOption].getInput(e))
				return true;
		}
		
		// ??
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
			else if(currentOption == scoresPosition) // show scores
				showScores();
			return true;
		}
		
		return false;
	}
	
	private boolean mouseMoved(MouseEvent e)
	{
		//MouseEvent mEvent = (MouseEvent)e.event;
		int nextOption = -1;
		int i = 0;
		for(MenuOption o : options)
		{
			if(o == null) continue;
			if(o.getGlobalBounds().contains(e.getX(), e.getY()))
			{
				nextOption = i;
				break;
			}
			++i;
			
		}
		if(nextOption == -1) return false;
		
		for(MenuOption o : options)
		{
			if(o == null) continue;
			o.deactivate();
		}
		currentOption = nextOption;
		options[currentOption].activate();
		return true;
	}
	
	private boolean mouseClicked(MouseEvent e)
	{
		int x , y;
		x = e.getX();
		y = e.getY();
		MenuOption selectedOption = null;
		for(MenuOption o : options)
			if(o.getGlobalBounds().contains(x, y))
			{
				selectedOption = o;
				break;
			}
		if(selectedOption == null)
			return false;
		switch(currentOption)
		{
		case startPosition:
			startGame();
			return true;
		case exitPosition:
			exit();
			return true;
		case difficultyPosition:
			return false;
		case scoresPosition:
			showScores();
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
		if(!visible) return;
		for(MenuOption o : options)
		{
			if(o == null) continue;
			o.draw(g);
		}
	}
	
	@Override
	public void activate()
	{
		visible = true;
	}
	
	@Override
	public void deactivate()
	{
		visible = false;
	}
	
	private void startGame()
	{
		parent.addRequest(
				new PlayState(parent,
				((DifficultySetting)options[difficultyPosition]).getCurrentSetting(),
				((DifficultySetting)options[levelPosition]).getCurrentSetting())
						 );
	}
	
	private void exit()
	{
		System.exit(33);
	}
	
	private void showScores()
	{
		parent.addRequest(new ScoreState(parent));
	}
}
