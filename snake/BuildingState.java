import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class BuildingState extends GameState
{
	private GameField blank = new GameField(0);
	BufferedImage ghost;
	private int mouseX = 0, mouseY = 0;
	
	public BuildingState(StateSystem p)
	{
		super(p);
		GameField.loadFieldImages();
		ghost = Game.images.get("block");
	}
	
	@Override
	public boolean getInput(GameEvent e)
	{
		if(e.event.getID() == MouseEvent.MOUSE_MOVED)
			return mouseMoved((MouseEvent)e.event);
		if(e.event.getID() == MouseEvent.MOUSE_CLICKED)
			return mouseClicked();
		if(e.event.getID() == KeyEvent.KEY_PRESSED)
			return keyPressed((KeyEvent)e.event);
		return false;
	}
	
	private boolean mouseMoved(MouseEvent e)
	{
		if(!blank.contains(e.getX(), e.getY())) return false;
		mouseX = (e.getX() - (int)blank.getX()) / Game.pieceSize.width;
//		mouseX = mouseX - (mouseX % Game.pieceSize.width);
		mouseY = (e.getY() - (int)blank.getY()) / Game.pieceSize.height;
//		mouseY = mouseY - (mouseY % Game.pieceSize.height);
		return true;
	}
	
	private boolean mouseClicked()
	{
		if(blank.isFree(new Obstacle(mouseX, mouseY)))
			blank.addObstacle(new Obstacle(mouseX, mouseY));
		
		return true;
	}
	
	private boolean keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_ENTER:
			blank.save(55);
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
		blank.draw(g);
		Composite previousComp = g.getComposite();
		AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		g.setComposite(transparent);
		g.drawImage(ghost, null,
				mouseX * Game.pieceSize.width + (int)blank.getX(),
				mouseY * Game.pieceSize.height + (int)blank.getY());
		g.setComposite(previousComp);
	}
}
