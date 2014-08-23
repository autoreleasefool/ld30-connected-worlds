package ca.josephroque.ld30.entity;

import java.awt.Graphics2D;

import ca.josephroque.ld30.Game;

public abstract class Entity
{
	static Game gameInstance = null;

	float x, y;
	float dx, dy;
	int width, height;
	
	public Entity(Game game, float x, float y, int width, int height)
	{
		if (gameInstance == null) {
			gameInstance = game;
		}
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void update();
	public abstract void render(Graphics2D g2d, float interpolation);
	
	public boolean intersects(Entity other)
	{
		return (this.x < other.x + other.width && this.x + this.width > other.x && this.y < other.y + other.height && this.y + this.height > other.y);
	}
}
