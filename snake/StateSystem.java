import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;


public class StateSystem
{
    private LinkedList<GameState> states = new LinkedList<>();
    private GameState toAdd = null;
    private int toDelete = 0;
    private Graphics2D graphics;
    
    public StateSystem(Graphics2D g)
	{
		graphics = g;
	}

    public boolean getInput(GameEvent e)
    {
//        if(e.event.getID() == KeyEvent.KEY_PRESSED && ((KeyEvent)e.event).getKeyCode() == KeyEvent.VK_ESCAPE)
//        {
//			if(states.peek() != null) states.peek().end();
//        	clear(new MainMenu(this, graphics));
//        }
        if(states.peek() == null) return false;
        return states.peek().getInput(e);
    }


    public void update()
    {
        while(toDelete != 0)
        {
            states.pop();
            --toDelete;
        }
        if(toAdd != null)
        {
            states.push(toAdd);
            toAdd = null;
        }
        if(states.peek() == null) return;
        states.peek().update();
    }

    public void draw(Graphics2D g)
    {
    	graphics = g;
        for(int i = states.size() - 1; i >= 0; --i)
        {
            states.get(i).draw(g);
        }
    }

    public void addRequest(GameState next)
    {
        toAdd = next;
    }
    
    public void deleteRequest(int n)
    {
        toDelete = n;
    }

    public void changeRequest(GameState next)
    {
        ++toDelete;
        toAdd = next;
    }

    public void clear()
    {
        states.clear();
    }

    public void clear(GameState starting)
    {
        clear();
        toAdd = starting;
        update();
    }
}