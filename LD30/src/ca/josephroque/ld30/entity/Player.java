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
		
		x += dx;
		y += (overOrUnder) ? dy:-dy;
		
		direction = (dx < 0) ? 1:(dx > 0 ? 0:direction);
		
		x += dx;
		y += dy;
	}
	
	public void update()
	{
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
			else if (x > gameInstance.getLevel().getWidth() - Constants.SCREEN_WIDTH / 2 + width / 2)
			{
				renderX = Constants.SCREEN_WIDTH / 2 + x - (gameInstance.getLevel().getWidth() - Constants.SCREEN_WIDTH / 2);
				xOffset = x - (Constants.SCREEN_WIDTH / 2 - width / 2);
			}
			else
			{
				renderX = Constants.SCREEN_WIDTH / 2 - width / 2;
				xOffset = gameInstance.getLevel().getWidth() - Constants.SCREEN_WIDTH;
			}
		}
		g2d.drawImage(Assets.terrain.getSubimage(0 + (frame * width) + (direction * width * 7), 183 + ((overOrUnder) ? 0:256), width, height), renderX, y - yOffset, null);
	}

	public Player(int x, int y, boolean overOrUnder)
	{
		super(x, y, 21, 73, overOrUnder);
	}
	
	public static int getXOffset() {return xOffset;}
	public static int getYOffset() {return yOffset;}
}
