import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Game extends Canvas
{
	public static final ResManager<BufferedImage> images = new ResManager<>();
	public static final Dimension pieceSize;
	public static final int width, height;
	public static final Dimension pieceCount;
	public static final Random rand;
	
	private Window w;
	private StateSystem states;
	
	static
	{
		BufferedImage headPic = null;
		rand = new Random(System.nanoTime());
		
		try
		{
			headPic = ImageIO.read(new File("Res\\headDead.png"));
			images.insert("headDead", headPic);
			headPic = ImageIO.read(new File("Res\\head.png"));
			images.insert("head", headPic);
			headPic = ImageIO.read(new File("Res\\headOpen.png"));
			images.insert("headOpen", headPic);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		pieceSize = new Dimension(headPic.getWidth(), headPic.getHeight());
		
		width = headPic.getWidth() * 21;
		height = headPic.getHeight() * 21;
		
		pieceCount = new Dimension(width / pieceSize.width, height / pieceSize.height);
	}
	
	public static void insertRotatedImages(String id)
	{
		BufferedImage nextImage = images.get(id);
		
		Game.images.insert(id + "R", nextImage);
		AffineTransform transform = AffineTransform.getRotateInstance(Math.PI / 2, nextImage.getWidth() / 2, nextImage.getHeight() / 2);
		AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		nextImage = operation.filter(nextImage, null);
		Game.images.insert(id + "D", nextImage);
		
		nextImage = operation.filter(nextImage, null);
		Game.images.insert(id + "L", nextImage);
		
		nextImage = operation.filter(nextImage, null);
		Game.images.insert(id + "U", nextImage);
		
		// replace the left one
		nextImage = Game.images.get(id + "L");
		transform = AffineTransform.getScaleInstance(1, -1);
		transform.translate(0, -nextImage.getHeight());
		operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		nextImage = operation.filter(nextImage, null);
		Game.images.remove(id +"L");
		Game.images.insert(id + "L", nextImage);
	}
	
	public Game()
	{
		System.out.println(width);
		System.out.println(height);
		
		setSize(width, height);
		
		states = new StateSystem();
		
		w = new Window(700, 700, this);
		//requestFocus();
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
		Clock frameTimer = new Clock(), printTimer = new Clock();
		Time frameTime = Time.milliseconds(16), printDelay = Time.seconds(1);
        int frames = 0;
        
        // stuff for drawing
		Graphics2D g;
		BufferStrategy bs = getBufferStrategy();
        setBackground(Color.green);
        frameTimer.restart();
        printTimer.restart();
        while(true)
		{
			// events
			while(w.pollEvent(e))
			{
				if(states.getInput(e)) continue;
			}
			
			
			if(frameTimer.getElapsedTime().compareTo(frameTime) < 0) continue;
			//if(now - previousFrame < frameDelay) continue;
			
			// update stuff
			states.update();
			// draw background
			g = (Graphics2D)bs.getDrawGraphics();
			paint(g);
			

			// draw game
			states.draw(g);
			bs.show();
			g.dispose();
			
			
			frameTimer.restart();
			++frames;
			
			if(printTimer.getElapsedTime().compareTo(printDelay) < 0) continue;
			
			// fps display
			System.out.printf("FPS: %d\n", frames);
			frames = 0;
			
			printTimer.restart();
		}
    }
}