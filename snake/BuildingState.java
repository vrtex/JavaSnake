import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BuildingState extends GameState
{
	private enum blocks
	{
		block,
		teleporter,
		none
	}
	private GameField blank = new GameField(0);
	
	private BufferedImage ghost;
	private BufferedImage blockGhost;
	private BufferedImage teleporterGhost;
	private BufferedImage none;
	private int placedTeleportes = 0;
	private int maxTeleporters = 3;
	
	
	private blocks currentGhost = blocks.block;
	private Pair<Integer, Integer> teleEnter = null;
	private BufferedImage playerGhost = Game.images.get("head");
	private int mouseX = 0, mouseY = 0;
	
	public BuildingState(StateSystem p)
	{
		super(p);
		
		GameField.loadFieldImages();
		try
		{
			none = ImageIO.read(new File("Res\\cross.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1234);
		}
		Game.images.insert("cross", none);
		
		
		blockGhost = Game.images.get("block");
		teleporterGhost = Game.images.get("teleporter");
		ghost = blockGhost;
	}
	
	@Override
	public boolean getInput(GameEvent e)
	{
		if(e.event.getID() == MouseEvent.MOUSE_MOVED)
			return mouseMoved((MouseEvent)e.event);
		if(e.event.getID() == MouseEvent.MOUSE_CLICKED)
			return mouseClicked((MouseEvent)e.event);
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
	
	private boolean mouseClicked(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1) // left click
		switch(currentGhost)
		{
		case block:
			placeBlock(mouseX, mouseY);
			return true;
		case teleporter:
			placeTeleporter(mouseX, mouseY);
			return true;
		}
		
		else if(e.getButton() == MouseEvent.BUTTON3) // right click
		{
			removeObject(mouseX, mouseY);
			return true;
		}
		
		return true;
	}
	
	private boolean keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_ENTER:
			blank.save(99);
			System.exit(-1);
			return true;
		case KeyEvent.VK_1:
			currentGhost = blocks.block;
			ghost = blockGhost;
			return true;
		case KeyEvent.VK_2:
			if(placedTeleportes != maxTeleporters)
			{
				currentGhost = blocks.teleporter;
				ghost = teleporterGhost;
			}
			else
			{
				currentGhost = blocks.none;
				ghost = none;
			}
			return true;
		case KeyEvent.VK_ESCAPE:
			reset();
			return true;
		}
		return false;
	}
	
	private void placeBlock(int x, int y)
	{
		blank.addObstacle(new Obstacle(x, y));
	}
	
	private void placeTeleporter(int x, int y)
	{
		if(placedTeleportes == maxTeleporters) return;
		if(teleEnter == null)
		{
			teleEnter = new Pair<>(x, y);
			return;
		}
		
		blank.addTeleporter(teleEnter.getKey(), teleEnter.getValue(), x, y);
		teleEnter = null;
		
		++placedTeleportes;
		if(placedTeleportes == maxTeleporters)
		{
			currentGhost = blocks.none;
			ghost = none;
		}
		
	}
	
	private void removeObject(int x, int y)
	{
		if(!blank.isFree(new Obstacle(x, y)))
			blank.removeObstacle(new Obstacle(x, y));
		else if(blank.teleportsFrom(x, y) != null)
		{
			blank.removeTeleporter(x, y);
			--placedTeleportes;
		}
		
	}
	
	private void reset()
	{
		blank = new GameField(0);
		placedTeleportes = 0;
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
		
		g.translate(blank.getX(), blank.getY());
		
		g.setComposite(transparent);
		g.drawImage(
				playerGhost, null,
				PlayState.startX * Game.pieceSize.width,// + (int)blank.getX(),
				PlayState.startY * Game.pieceSize.height// + (int)blank.getY()
					);
		if(teleEnter != null)
			g.drawImage(
					teleporterGhost, null,
					teleEnter.getKey() * Game.pieceSize.width,
					teleEnter.getValue() * Game.pieceSize.height
					   );
		g.drawImage(ghost, null,
				mouseX * Game.pieceSize.width,// + (int)blank.getX(),
				mouseY * Game.pieceSize.height// + (int)blank.getY()
				   );
		
		g.translate(-blank.getX(), -blank.getY());
		g.setComposite(previousComp);
	}
}
