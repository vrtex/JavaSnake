import java.awt.event.*;

public class GameInput implements KeyListener, MouseListener, MouseMotionListener
{
	private Window parent;
	GameInput(Window p)
	{
		parent = p;
		
	}
	
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		parent.addEvent(new GameEvent(e));
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(22);
		parent.addEvent(new GameEvent(e));
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		parent.addEvent(new GameEvent(e));
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		parent.addEvent(new GameEvent(e));
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		parent.addEvent(new GameEvent(e));
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		parent.addEvent(new GameEvent(e));
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		parent.addEvent(new GameEvent(e));
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		parent.addEvent(new GameEvent(e));
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		parent.addEvent(new GameEvent(e));
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		parent.addEvent(new GameEvent(e));
	}
}
