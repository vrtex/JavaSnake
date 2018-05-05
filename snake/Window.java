import javax.swing.*;
import java.util.LinkedList;

public class Window extends JFrame
{
    private LinkedList<GameEvent> currentEvents = new LinkedList<>();
    
    public Window(int width, int height, Game g)
    {
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        add(g);
//        GameInput listener = new GameInput(this);
//        addKeyListener(listener);
//        addMouseListener(listener);
        pack();
        setVisible(true);
    }
    
    public void addEvent(GameEvent e)
	{
		currentEvents.push(e);
	}
	
	public boolean pollEvent(GameEvent e)
	{
		if(currentEvents.size() == 0) return false;
		
		GameEvent next = null;
		if(!currentEvents.isEmpty())
			next = currentEvents.pop();
		
		if(next == null)
			return false;
		
		e.event = next.event;
		e.type = next.type;
		
		return true;
	}
 
}