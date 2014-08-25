package ca.josephroque.ld30.entity;

import java.awt.Graphics2D;

import ca.josephroque.ld30.Assets;
import ca.josephroque.ld30.Constants;
import ca.josephroque.ld30.Input;

public class Player extends Entity
{
	
	public void update(Input input)
	{
		update();
	}
	
	public void update()
	{
		
	}
	
	public void render(Graphics2D g2d, float interpolation)
	{
		int xx = x;
		if (x > Constants.SCREEN_WIDTH / 2 - width / 2)
		{
			
		}
		int yy = y;
		if (y > Constants.SCREEN_HEIGHT / 2 - height / 2)
		{
			
		}
		g2d.drawImage(Assets.terrain.getSubimage(0 + (frame * width) + (direction * width * 7), 183 + ((overOrUnder) ? 0:256), width, height), xx, yy, null);
	}

	public Player(int x, int y, boolean overOrUnder)
	{
		super(x, y, 21, 73, overOrUnder);
	}
}
