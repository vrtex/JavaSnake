import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;


public class StateSystem
{
    private LinkedList<GameState> states = new LinkedList<>();
    private GameState toAdd = null;
    private int toDelete = 0;

    public boolean getInput(GameEvent e)
    {
        // use modifier to find out event type (pressed /releaase)
        // 0: pressed
        // 1: released
        if(states.size() == 0) return false;
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
        if(states.isEmpty()) return;
        states.peek().update();
    }

    public void draw(Graphics g)
    {
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