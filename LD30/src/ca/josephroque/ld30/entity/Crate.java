package ca.josephroque.ld30.entity;

import java.awt.Graphics2D;

import ca.josephroque.ld30.Assets;
import ca.josephroque.ld30.Constants;

public class Crate extends Entity
{
	private int pushTimer = 0;
	private boolean stopped = false;
	
	public void update()
	{
		Entity player = Entity.gameInstance.getPlayer(overOrUnder);
		if (player.x == x + width || player.x + player.width == x)
		{
			if (++pushTimer > 20)
			{
				this.dx = player.direction == 0 ? 1:-1;
			}
		}
		else
		{
			dx += (dx < 0) ? 1:(dx > 0) ? -1:0;
			pushTimer = 0;
		}
		
		x += dx;
		
		if (x < 0)
		{
			x = 0;
		}
		else if (x + width >= gameInstance.getLevel().getWidth())
		{
			x = gameInstance.getLevel().getWidth() - width - 1;
		}
		
		stopped = false;
		while ((dx < 0 && (gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, y / Constants.TILE_SIZE)
				|| gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, (y+height-1) / Constants.TILE_SIZE)))
				|| (dx > 0 && (gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, y / Constants.TILE_SIZE)
						|| gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, (y+height-1) / Constants.TILE_SIZE))))
		{
			x += (dx < 0) ? 1:-1;
			stopped = true;
		}
		
		if (stopped)
		{
			dx = 0;
		}
		
		if ((overOrUnder && !gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, (y + height) / Constants.TILE_SIZE + 1)
				&& !gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, (y + height) / Constants.TILE_SIZE + 1))
				|| (!overOrUnder && !gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, y / Constants.TILE_SIZE - 1)
						&& !gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, y / Constants.TILE_SIZE - 1)))
		{
			dy += (dy < Constants.TERMINAL_VELOCITY) ? Constants.GRAVITY:0;
		}
		else
		{
			dy = 0;
		}
		
		if ((dx < 0 && (gameInstance.getLevel().isBlockSolid(x / 32 - 1, y / 32) || gameInstance.getLevel().isBlockSolid(x / 32 - 1, (y + height) / 32)))
				|| (dx > 0 && (gameInstance.getLevel().isBlockSolid(x / 32 + 1, y / 32) || gameInstance.getLevel().isBlockSolid(x / 32 + 1, (y + height) / 32))))
		{
			dx = (dy != 0) ? -dx:0;
		}
		
		
		y += (overOrUnder) ? dy:-dy;
	}
	
	public void render(Graphics2D g2d, float interpolation)
	{
		g2d.drawImage(Assets.terrain.getSubimage(32, (overOrUnder) ? 0:256, width, height), x - Player.getXOffset(), y - Player.getYOffset(), null);
	}

	public Crate(int x, int y, boolean overOrUnder)
	{
		super(x, y, 32, 32, overOrUnder, true);
	}
}
