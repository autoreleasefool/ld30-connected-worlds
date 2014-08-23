package ca.josephroque.ld30;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	private Thread gameThread = null;
	private BufferStrategy bufferStrategy = null;
	private Input input = null;
	private Game game = null;
	
	private boolean running = false;
	private boolean paused = false;
	
	private void update()
	{
		if (input.escape)
		{
			paused = !paused;
			input.escape = false;
		}
		
		if (paused || !input.focused)
		{
			
		}
		else
		{
			game.update(input);
		}
	}
	
	private void render(float interpolation)
	{
		if (bufferStrategy == null)
		{
			createBufferStrategy(2);
			bufferStrategy = getBufferStrategy();
			return;
		}
		
		Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		
		game.render(g2d, interpolation);
		
		if (paused)
		{
			
		}
		
		if (!input.focused)
		{
			
		}
		
		g2d.dispose();
		bufferStrategy.show();
	}
	
	public void start()
	{
		input = new Input(this);
		game = new Game();
		
		if (!running)
		{
			running = true;
			gameThread = new Thread(this);
			gameThread.start();
		}
	}
	
	public GameCanvas()
	{
		final Dimension size = new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setFocusable(true);
	}

	public void run()
	{
		long nextGameUpdate = System.nanoTime();
		long frameTimer = System.currentTimeMillis();
		int loops = 0;
		int frames = 0;
		int updates = 0;
		float interpolation = 0;
		
		while (running) 
		{
			loops = 0;
			while (System.nanoTime() > nextGameUpdate && loops < Constants.MAX_FRAMESKIPS)
			{
				update();
				nextGameUpdate += Constants.SKIP_TICKS;
				loops++;
				updates++;
			}
			
			interpolation = (System.nanoTime() - nextGameUpdate + Constants.SKIP_TICKS) / (float)Constants.SKIP_TICKS;
			render(interpolation);
			frames++;
				
			while (System.currentTimeMillis() - frameTimer > 1000)
			{
				if (Constants.shouldPrintToConsole)
				{
					System.out.println("TICKS: " + updates + " FPS: " + frames);
				}
				frameTimer += 1000;
				updates = frames = 0;
			}
		}
	}
}
