import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

public class PlayState extends GameState
{
//    GameField field = new GameField(0, Game.pieceSize.height, Game.width, Game.height - Game.pieceSize.height);
//    Player player = new Player(1, 2, field);
	private GameField field =
		new GameField(
				Game.pieceSize.width / 2,
				Game.pieceSize.height,
				Game.width - Game.pieceSize.width,
				Game.height - Game.pieceSize.height);
	private Player player;
	private HUD hud;
	public final static int startX = 5, startY = 5;
	private int lvlNumber;
	private int difficulty;
	
	
	public PlayState(StateSystem p, int diff, int lvl)
    {
        super(p);
        
        lvlNumber = lvl;
        field = new GameField(lvlNumber);
        
        
//        field.save(0);
//        System.exit(33);
	
//		field.addObstacle(new Obstacle(2, 3));
//		field.addObstacle(new Obstacle(0, 0));
//		field.addObstacle(new Obstacle(0, 1));
//		field.addObstacle(new Obstacle(0, 2));
//		field.addObstacle(new Obstacle(0, 3));
//
//		field.addTeleporter(5, 5, 8, 8);
//
//		field.save(15);
//		System.exit(15);
		
		
		// making them pictures
		Game.insertRotatedImages("head");
		Game.insertRotatedImages("headDead");
		Game.insertRotatedImages("headOpen");
		
		// tail pictures
		tailPictures("tailH1");
		tailPictures("tailH2");
		tailPictures("tailV1");
		tailPictures("tailV2");
		tailPictures("tailLB");
		tailPictures("tailLT");
		tailPictures("tailRB");
		tailPictures("tailRT");
	
	
		difficulty = diff;
		player = new Player(startX, startY, field, difficulty);
		
		hud = new HUD(player);
    }
    
    public PlayState(PlayState other)
	{
		super(other.parent);
		
		lvlNumber = other.lvlNumber;
		field = other.field;
		
		difficulty = other.difficulty;
		player = new Player(PlayState.startX, PlayState.startY, field, difficulty);
		
		hud = new HUD(player);
	}

    public boolean getInput(GameEvent e)
    {
    	if(player.getInput(e)) return true;
    	if(e.event.getID() == KeyEvent.KEY_PRESSED)
		{
			KeyEvent kEvent = (KeyEvent)e.event;
			if(kEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				parent.addRequest(new PauseState(parent));
				return true;
			}
		}
        return false;
    }

    public void update()
    {
    	// todo isDead?
		if(player.isDead())
		{
			parent.changeRequest(new GameOver(parent, this, player.getScore()));
			return;
		}
		field.update();
    	player.update();
		hud.update();
    }

    public void draw(Graphics2D g)
    {
		field.draw(g);
		g.translate(field.x, field.y);
        player.draw(g);
		g.translate(-field.x, -field.y);
		hud.draw(g);
    }
    
    public void end()
	{
		field.save(lvlNumber);
	}
	
	public static void tailPictures(String id)
	{
		BufferedImage toAdd = null;
		if(Game.images.contains(id)) return;
		try
		{
			File f = new File("Res\\tail\\" + id + ".png");
			toAdd = ImageIO.read(f);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(345);
		}
		Game.images.insert(id, toAdd);
		
	}
}