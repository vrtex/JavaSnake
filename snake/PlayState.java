import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
	private final int startX = 5, startY = 5;
	
	
	public PlayState(StateSystem p)
    {
        super(p);
	
//		field.addObstacle(new Obstacle(2, 3));
//		field.addObstacle(new Obstacle(0, 0));
//		field.addObstacle(new Obstacle(0, 1));
//		field.addObstacle(new Obstacle(0, 2));
//		field.addObstacle(new Obstacle(0, 3));
		
		// making them pictures
		Game.insertRotatedImages("head");
		Game.insertRotatedImages("headDead");
		
		
        player = new Player(startX, startY, field);
    }

    public boolean getInput(GameEvent e)
    {
    	if(player.getInput(e)) return true;
        return false;
    }

    public void update()
    {
    	// isDead?
    	player.update();
    }

    public void draw(Graphics2D g)
    {
    	g.setColor(Color.blue);
        g.fillRect(0, 0, Game.pieceSize.width / 2, Game.pieceSize.height);
		field.draw(g);
		g.translate(field.x, field.y);
        player.draw(g);
		g.translate(-field.x, -field.y);
    }
    
    public void end()
	{
		field.save();
	}
}