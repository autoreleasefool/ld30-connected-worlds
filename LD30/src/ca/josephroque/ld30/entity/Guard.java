package ca.josephroque.ld30.entity;

import java.awt.Graphics2D;

import ca.josephroque.ld30.Assets;

public class Guard extends Entity
{
	private int leftLimit, rightLimit;
	private int switchTimer = 0;
	
	private static final int SPEED = 5;
	
	public void update()
	{
		if (++frame == 40)
		{
			frame = 0;
		}
		
		if (dx == 0)
		{
			if (switchTimer == 0)
			{
				dx = SPEED * (x == leftLimit ? 1:-1);
				direction = (dx < 0) ? 1:0;
			}
			else
			{
				switchTimer--;
			}
		}
		else if (dx < 0)
		{
			x = (x + dx < leftLimit ? leftLimit:x + dx);
			if (x == leftLimit)
			{
				dx = 0;
				switchTimer = 30;
			}
		}
		else
		{
			x = (x + dx + width > rightLimit ? rightLimit:x + dx);
			if (x + width == rightLimit)
			{
				dx = 0;
				switchTimer = 30;
			}
		}
		
		//Detect player here
	}
	
	public void render(Graphics2D g2d, float interpolation)
	{
		g2d.drawImage(Assets.terrain.getSubimage(294 + direction * width, 239 + (overOrUnder ? 0:256), width, height), x, y + ((frame < 20) ? 0:2), null);
	}

	public Guard(int x, int y, int leftLimit, int rightLimit, boolean overOrUnder)
	{
		super(x, y, 17, 17, overOrUnder);
		this.leftLimit = leftLimit;
		this.rightLimit = rightLimit;
		dx = SPEED * (Math.random() < 0.5 ? 1:-1);
		direction = (dx < 0) ? 1:0;
	}
}
