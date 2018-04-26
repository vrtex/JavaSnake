import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class GameState
{
    protected StateSystem parent;

    public GameState(StateSystem p)
    {
        parent = p;
    }

    public abstract boolean getInput(GameEvent e);

    public abstract void update();

    public abstract void draw(Graphics2D g);
}