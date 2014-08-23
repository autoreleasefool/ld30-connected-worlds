package ca.josephroque.ld30;

import java.awt.Graphics2D;

import ca.josephroque.ld30.entity.Player;
import ca.josephroque.ld30.level.Level;

public class Game
{
	private Player overPlayer = null;
	private Player underPlayer = null;
	private Level level = null;
	
	public void update(Input input)
	{
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
		overPlayer = new Player(this, 0, 0, true);
		underPlayer = new Player(this, 0, 0, false);
		level = new Level(this, 1);
		overPlayer.setCurrentLevel(level);
		underPlayer.setCurrentLevel(level);
	}
	
	public Player getPlayer(boolean over)
	{
		return (over) ? overPlayer:underPlayer;
	}
}
