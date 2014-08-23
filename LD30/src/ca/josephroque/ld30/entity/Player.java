package ca.josephroque.ld30.entity;

import java.awt.Graphics2D;

import ca.josephroque.ld30.Assets;
import ca.josephroque.ld30.Game;
import ca.josephroque.ld30.Input;
import ca.josephroque.ld30.level.Level;

public class Player extends Entity
{
	private Level currentLevel = null;
	private int frame = 0;
	private int direction = 0;	//Facing right = 0, Facing left = 1
	
	public void update(Input input)
	{
		update();
	}
	
	public void update()
	{
		
	}
	
	public void render(Graphics2D g2d, float interpolation)
	{
		g2d.drawImage(Assets.terrain.getSubimage(0 + (frame * width) + (direction * width * 7), 184 + ((overOrUnder) ? 0:255), width, height), x, y, null);
	}

	public Player(Game game, int x, int y, boolean overOrUnder)
	{
		super(game, x, y, 21, 73, overOrUnder);
	}
	
	public void setCurrentLevel(Level level)
	{
		currentLevel = level;
	}
}
