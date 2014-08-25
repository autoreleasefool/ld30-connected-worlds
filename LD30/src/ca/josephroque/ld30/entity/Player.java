package ca.josephroque.ld30.entity;

import java.awt.Graphics2D;

import ca.josephroque.ld30.Assets;
import ca.josephroque.ld30.Constants;
import ca.josephroque.ld30.Input;

public class Player extends Entity
{
	
	private static int xOffset = 0;
	private static final int yOffset = 20;
	private static int renderX;
	private static final int SPEED = 4;
	private boolean falling = false;
	
	public void update(Input input)
	{
		update();
		
		if ((input.left || input.a) && !(input.right || input.d))
		{
			dx += (dx > -SPEED) ? -1:0;
		}
		else if ((input.right || input.d) && !(input.left || input.a))
		{
			dx += (dx < SPEED) ? 1:0;
		}
		else
		{
			dx += (dx < 0) ? 1:(dx > 0 ? -1:0);
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
		
		while ((dx <= 0 && (gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, y / Constants.TILE_SIZE)
				|| gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, (y + height) / Constants.TILE_SIZE)))
				|| (dx >= 0 && (gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, y / Constants.TILE_SIZE)
						|| gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, (y+height) / Constants.TILE_SIZE)))
						|| gameInstance.getLevel().isPlayerHittingEntity(this))
		{
			x += (direction != 0) ? 1:-1;
			gameInstance.getPlayer(!overOrUnder).setPosition(x, gameInstance.getPlayer(!overOrUnder).getY());
		}
		
		if (dy < 0 || ((overOrUnder && !gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, (y + height + 1) / Constants.TILE_SIZE)
				&& !gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, (y + height + 1) / Constants.TILE_SIZE))
				|| (!overOrUnder && !gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, (y - 1) / Constants.TILE_SIZE)
				&& !gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, (y - 1) / Constants.TILE_SIZE)))
				&& !gameInstance.getLevel().isPlayerOnTopOfEntity(this))
		{
			falling = true;
			dy += (dy < Constants.TERMINAL_VELOCITY) ? Constants.GRAVITY:0;
			y += (overOrUnder) ? dy:-dy;
			
			while ((overOrUnder && (gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, (y + height) / Constants.TILE_SIZE)
					|| gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, (y + height) / Constants.TILE_SIZE)))
					|| (!overOrUnder && (gameInstance.getLevel().isBlockSolid(x / Constants.TILE_SIZE, (y) / Constants.TILE_SIZE)
					|| gameInstance.getLevel().isBlockSolid((x + width) / Constants.TILE_SIZE, (y) / Constants.TILE_SIZE)))
					|| gameInstance.getLevel().isPlayerHittingEntity(this))
			{
				y += (overOrUnder) ? -1:1;
				dy = 0;
				falling = false;
			}
		}
		else
		{
			falling = false;
			dy = 0;
			if (input.space && !falling)
			{
				dy = -10;
			}
		}
		
		direction = (dx < 0) ? 1:(dx > 0 ? 0:direction);
	}
	
	public void update()
	{
		if (++frame > 25)
		{
			frame = 0;
		}
	}
	
	public void render(Graphics2D g2d, float interpolation)
	{
		if (overOrUnder)
		{
			if (x < Constants.SCREEN_WIDTH / 2 - width / 2)
			{
				renderX = x;
				xOffset = 0;
			}
			else if (x > gameInstance.getLevel().getWidth() - Constants.SCREEN_WIDTH / 2 - width / 2)
			{
				renderX = Constants.SCREEN_WIDTH / 2 + x - (gameInstance.getLevel().getWidth() - Constants.SCREEN_WIDTH / 2);
				xOffset = gameInstance.getLevel().getWidth() - Constants.SCREEN_WIDTH;
			}
			else
			{
				renderX = Constants.SCREEN_WIDTH / 2 - width / 2;
				xOffset = x - (Constants.SCREEN_WIDTH / 2 - width / 2);
			}
		}
		if (falling)
		{
			g2d.drawImage(Assets.terrain.getSubimage((6  * width) + (direction * width * 7), 183 + ((overOrUnder) ? 0:256), width, height), renderX, y - yOffset, null);
		}
		else if (gameInstance.getPlayer(true).dx != 0)
		{
			g2d.drawImage(Assets.terrain.getSubimage((frame / 5 * width) + (direction * width * 7), 183 + ((overOrUnder) ? 0:256), width, height), renderX, y - yOffset, null);
		}
		else
		{
			g2d.drawImage(Assets.terrain.getSubimage((direction * width * 7), 183 + ((overOrUnder) ? 0:256), width, height), renderX, y - yOffset, null);
		}
	}

	public Player(int x, int y, boolean overOrUnder)
	{
		super(x, y, 21, 73, overOrUnder, false);
	}
	
	public static int getXOffset() {return xOffset;}
	public static int getYOffset() {return yOffset;}
}
