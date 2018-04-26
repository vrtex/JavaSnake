import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends Canvas
{
	public static final ResManager<BufferedImage> images = new ResManager<>();
	public static final Dimension pieceSize;
	public static final int width, height;
	
	private Window w;
	private StateSystem states;
	
	static
	{
		BufferedImage headPic = null;
		
		try
		{
			headPic = ImageIO.read(new File("Res\\head.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		images.insert("head", headPic);
		pieceSize = new Dimension(headPic.getWidth(), headPic.getHeight());
		
		width = headPic.getWidth() * 21;
		height = headPic.getHeight() * 21;
	}
	
	public Game()
	{
		System.out.println(width);
		System.out.println(height);
		
		setSize(width, height);
		
		states = new StateSystem();
		
		w = new Window(700, 700, this);
		requestFocus();
		createBufferStrategy(3);
	}
	
    public static void main(String args[])
    {
        Game g = new Game();
        g.start();
    }

    private void start()
    {
		
        System.out.println("Game start");


        states.addRequest(new PlayState(states));
        
        GameEvent e = new GameEvent();
	
		// frames management values
        long
				now,
				previousFrame = System.nanoTime(),
				lastPrint = System.nanoTime(),
				frameDelay = 17 * 1000000;
        int frames = 0;
        
        // stuff for drawing
		Graphics2D g;
		BufferStrategy bs = getBufferStrategy();
        
        while(true)
		{
			// events
			while(w.pollEvent(e))
			{
				if(states.getInput(e)) continue;
			}
			
			now = System.nanoTime();
			if(now - previousFrame < frameDelay) continue;
			
			// update stuff
			// accumulator?
			states.update();
			
			// draw background
			g = (Graphics2D)bs.getDrawGraphics();
			g.setColor(Color.green);
			g.fillRect(0, 0, width, height);

			// draw game
			states.draw(g);
			bs.show();
			g.dispose();
			
			
			previousFrame = now;
			++frames;
			if(now - lastPrint < 1000000000) continue;
			
			// fps display
			System.out.printf("FPS: %d\n", frames);
			lastPrint = now;
			frames = 0;
			
		}
    }
}