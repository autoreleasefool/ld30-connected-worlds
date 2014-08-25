package ca.josephroque.ld30;

import java.awt.Graphics2D;

import ca.josephroque.ld30.entity.Entity;
import ca.josephroque.ld30.entity.Player;
import ca.josephroque.ld30.level.Level;

public class Game
{
	private Player overPlayer = null;
	private Player underPlayer = null;
	private Level level = null;
	
	private boolean firstIteration = true;
	
	public void update(Input input)
	{
		if (firstIteration)
		{
			Entity.setGame(this);
			firstIteration = false;
		}
		
		level.update();
		overPlayer.update(input);
		underPlayer.update(input);
	}
	
	public void render(Graphics2D g2d, float interpolation)
	{
		level.render(g2d, interpolation);
		overPlayer.render(g2d, interpolation);
		underPlayer.render(g2d, interpolation);
	}

	public Game()
	{
		overPlayer = new Player(0, 0, true);
		underPlayer = new Player(0, 0, false);
		level = new Level(this, 1);
	}
	
	public Player getPlayer(boolean over) {return (over) ? overPlayer:underPlayer;}
	public Level getLevel() {return level;}
}
