import java.awt.*;
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
	
	@Override
	public void update()
	{
		System.out.printf("currentX: %d, currentY: %d\n", mouseX, mouseY);
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
