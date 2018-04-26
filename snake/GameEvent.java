import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameEvent
{
	public enum EventType
	{
		nothing,
		key,
		mouse
	}
	
	public InputEvent event;
	public EventType type;
	
	public GameEvent()
	{
		event = null;
		type = EventType.nothing; // empty event
	}
	
	public GameEvent(InputEvent e)
	{
		event = e;
		type = EventType.nothing; // unknown
		if(e instanceof KeyEvent) type = EventType.key;
		else if(e instanceof MouseEvent) type = EventType.mouse;
	}
}
