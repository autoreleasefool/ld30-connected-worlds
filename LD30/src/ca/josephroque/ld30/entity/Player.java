package ca.josephroque.ld30.entity;

import java.awt.Graphics2D;

import ca.josephroque.ld30.Game;
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
		
	}

	public Player(Game game, float x, float y)
	{
		super(game, x, y, 0, 0);
	}
}
