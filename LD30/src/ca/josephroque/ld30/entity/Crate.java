package ca.josephroque.ld30.entity;

import java.awt.Graphics2D;

import ca.josephroque.ld30.Assets;
import ca.josephroque.ld30.Constants;

public class Crate extends Entity
{
	private int pushTimer = 0;
	
	public void update()
	{
		Entity player = Entity.gameInstance.getPlayer(overOrUnder);
		if ((player.x == x + width + 1 && player.dx < 0) || (player.x + player.width == x - 1 && player.dx > 0))
		{
			if (++pushTimer > 10)
			{
				this.dx = player.dx;
			}
		}
		else if (dx != 0)
		{
			dx += (dx < 0) ? 1:-1;
			pushTimer = 0;
		}
		
		if (!gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, (y + height) / Constants.TILE_SIZE + 1)
				&& !gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, (y + height) / Constants.TILE_SIZE + 1))
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
	}
	
	public void render(Graphics2D g2d, float interpolation)
	{
		g2d.drawImage(Assets.terrain.getSubimage(32, (overOrUnder) ? 0:256, width, height), x, y, null);
	}

	public Crate(int x, int y, boolean overOrUnder)
	{
		super(x, y, 32, 32, overOrUnder);
	}
}
