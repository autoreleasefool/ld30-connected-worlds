package ca.josephroque.ld30.entity;

import java.awt.Color;
import java.awt.Graphics;

import ca.josephroque.ld30.Constants;
import ca.josephroque.ld30.Game;

public class Particle
{
	private static Game gameInstance = null;
	
	private int x, y;
	private int dx, dy;
	private Color color;
	private int decayTime;
	private boolean dead = false;
	private boolean overOrUnder;
	
	public void update()
	{
		if (--decayTime < 0)
			dead = true;
		
		x += dx;
		
		if (x < 0)
		{
			x = 0;
			dx = -dx;
		}
		else if (x + 1 >= gameInstance.getLevel().getWidth())
		{
			x = gameInstance.getLevel().getWidth() - 2;
			dx = -dx;
		}
		
		if ((overOrUnder && !gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, (y + 1) / Constants.TILE_SIZE + 1)
				&& !gameInstance.getLevel().isBlockSolid((x + 1) / Constants.TILE_SIZE, (y + 1) / Constants.TILE_SIZE + 1))
				|| (!overOrUnder && !gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, y / Constants.TILE_SIZE - 1)
						&& !gameInstance.getLevel().isBlockSolid((x + 1) / Constants.TILE_SIZE, y / Constants.TILE_SIZE - 1)))
		{
			dy += (dy < Constants.TERMINAL_VELOCITY) ? Constants.GRAVITY:0;
		}
		else
		{
			dy = -(dy - 1);
		}
		
		//dy += (dy < Constants.TERMINAL_VELOCITY) ? Constants.GRAVITY:0;
		y += (overOrUnder) ? dy:-dy;
	}
	
	public void render(Graphics g, float interpolation)
	{
		//g.setColor(color);
		//g.fillRect(x - Player.getXOffset(), y - Player.getYOffset(), 1, 1);
	}
	
	public Particle(Game game, int x, int y, Color color, boolean overOrUnder)
	{
		if (gameInstance == null)
		{
			gameInstance = game;
		}
		
		this.x = x;
		this.y = y;
		this.color = color;
		this.overOrUnder = overOrUnder;
		decayTime = (int)(Math.random() * 30 + 10);
		dx = (int)((Math.random() * 3 + 1) * (Math.random() < 0.5 ? -1:1));
	}
	
	public boolean isDead() {return dead;}
}
