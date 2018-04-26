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
        addKeyListener(new GameInput(this));
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
		
		GameEvent next = currentEvents.pop();
		
		e.event = next.event;
		e.type = next.type;
		
		return true;
	}
 
}