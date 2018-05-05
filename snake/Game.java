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
	public static final Font font;
	public static final ScoreTable scores;
	
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
		
		boolean gotFont = false;
		Font tmp = null;
		try
		{
			tmp = Font.createFont(Font.TRUETYPE_FONT, new File("Res\\arial.ttf"));
			gotFont = true;
		}
		catch(FontFormatException | IOException e)
		{
			e.printStackTrace();
			System.out.println("no font");
			System.exit(435);
		}
		if(!gotFont)
			font = null;
		else
			font = new Font(tmp.getName(), Font.PLAIN, 32);
		
		scores = ScoreTable.load();
		System.out.println("Current scores:");
		System.out.println(scores);
		
	}
	
	public static void insertRotatedImages(String id)
	{
		BufferedImage nextImage = images.get(id);
		
		Game.images.insert(id + "R", nextImage);
		AffineTransform transform = AffineTransform.getRotateInstance(Math.PI / 2, nextImage.getWidth() / 2, nextImage.getHeight() / 2);
		AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
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
		operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		nextImage = operation.filter(nextImage, null);
		Game.images.remove(id +"L");
		Game.images.insert(id + "L", nextImage);
	}
	
	public static BufferedImage dye(BufferedImage src, Color col)
	{
		int w = src.getWidth();
		int h = src.getHeight();
		BufferedImage toReturn = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		BufferedImage swirl = null;
		try
		{
			swirl = ImageIO.read(new File("Res\\swirl.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(33);
		}
		Graphics2D g = toReturn.createGraphics();
		g.drawImage(src, null, 0, 0);
		Composite transparency = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f);
		g.setComposite(transparency);
		g.setColor(col);
		g.fillRect(0, 0, w, h);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.XOR));
		g.drawImage(swirl, null, 0, 0);
		g.dispose();
		return toReturn;
	}
	
	public Game()
	{
		System.out.println(width);
		System.out.println(height);
		
		setSize(width, height);
		
		
		w = new Window(700, 700, this);
		GameInput listener = new GameInput(w);
		this.addKeyListener(listener);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		this.requestFocus();
		
		//requestFocus();
		createBufferStrategy(3);
		states = new StateSystem((Graphics2D)getBufferStrategy().getDrawGraphics());
	}
	
    public static void main(String args[])
    {
    	boolean building = false;
    	boolean reset = false;
    	if(args.length != 0)
		{
			switch(args[0])
			{
			case "-r":
				reset = true;
				break;
			case "-b":
				building = true;
				break;
			}
		}
		if(reset)
		{
			GameField newField = new GameField(
					Game.pieceSize.width / 2,
					Game.pieceSize.height,
					Game.width - Game.pieceSize.width,
					Game.height - Game.pieceSize.height);
			newField.save(0);
			System.exit(-2);
		}
        Game g = new Game();
        g.start(building);
    }

    public void start(boolean building)
    {
        System.out.println("Game start");


        
        GameEvent e = new GameEvent();
	
		// frames management values
		Clock frameTimer = new Clock(), printTimer = new Clock();
		Time frameTime = Time.milliseconds(16), printDelay = Time.seconds(1);
        int frames = 0;
        
        // stuff for drawing
		Graphics2D g;
		BufferStrategy bs = getBufferStrategy();
        setBackground(new Color(8, 81, 9));
        frameTimer.restart();
        printTimer.restart();
//        states.addRequest(new PlayState(states, 1));
		if(building)
			states.addRequest(new BuildingState(states));
		else
			states.addRequest(new MainMenu(states, (Graphics2D)bs.getDrawGraphics()));
//		states.addRequest(new ScoreState(states));

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
			//System.out.printf("FPS: %d\n", frames);
			frames = 0;
			
			printTimer.restart();
		}
    }
}