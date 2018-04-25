import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends Canvas
{
    public static void main(String args[])
    {
        Game g = new Game();
        g.run();
    }

    private void run()
    {
        System.out.println("Game start");
        int width, height;
        BufferedImage headPic = null;
        ResManager<BufferedImage> images = new ResManager<>();
        try
        {
            headPic = ImageIO.read(new File("Res\\head.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        width = headPic.getWidth() * 20;
        height = headPic.getHeight() * 21;
		System.out.println(width);
		System.out.println(height);
        images.insert("head", headPic);

        requestFocus();
        StateSystem states = new StateSystem();

        setSize(width, height);
        Window w = new Window(width, height, this);
        w.repack();
        
        createBufferStrategy(3);
        
        GameEvent e = new GameEvent();
        long
				now,
				previousFrame = System.nanoTime(),
				lastPrint = System.nanoTime(),
				frameDelay = 17 * 1000000;
        int frames = 0;
		Graphics g;
        
        while(true)
		{
			while(w.pollEvent(e))
			{
				if(states.getInput(e)) continue;
			}
			
			now = System.nanoTime();
			if(now - previousFrame < frameDelay) continue;
			
			g = getBufferStrategy().getDrawGraphics();
			states.draw(g);
			g.dispose();
			
			// accumulator?
			states.update();
			
			previousFrame = now;
			++frames;
			if(now - lastPrint < 1000000000) continue;
			System.out.printf("FPS: %d\n", frames);
			lastPrint = now;
			frames = 0;
			
		}
    }
}