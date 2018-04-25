import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameEvent
{
	public static int key = 1, mouse = 2;
	
	public InputEvent event;
	public int type;
	
	public GameEvent()
	{
		event = null;
		type = 0; // empty event
	}
	
	public GameEvent(InputEvent e)
	{
		event = e;
		type = -1; // unknown
		if(e instanceof KeyEvent) type = key;
		else if(e instanceof MouseEvent) type = mouse;
	}
}
