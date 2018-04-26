import java.awt.*;

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
	
    public PlayState(StateSystem p)
    {
        super(p);
	
		field.addObstacle(new Obstacle(2, 3));
		field.addObstacle(new Obstacle(0, 0));
		field.addObstacle(new Obstacle(0, 1));
		field.addObstacle(new Obstacle(0, 2));
		field.addObstacle(new Obstacle(0, 3));
        player = new Player(3, 3, field.getPosition());
    }

    public boolean getInput(GameEvent e)
    {
    	if(player.getInput(e)) return true;
        return false;
    }

    public void update()
    {
    	player.update();
    }

    public void draw(Graphics2D g)
    {
    	g.setColor(Color.blue);
        g.fillRect(0, 0, Game.pieceSize.width / 2, Game.pieceSize.height);
        field.draw(g);
        
        player.draw(g);
    }
}