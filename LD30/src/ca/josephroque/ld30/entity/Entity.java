package ca.josephroque.ld30.entity;

import java.awt.Graphics2D;

import ca.josephroque.ld30.Game;

public abstract class Entity
{
	static Game gameInstance = null;

	int x, y;
	int dx, dy;
	int width, height;
	boolean dead = false;
	boolean overOrUnder; //Over = true, Under = false
	
	int frame = 0;
	int direction = 0;	//Facing right = 0, Facing left = 1
	
	public Entity(int x, int y, int width, int height, boolean overOrUnder)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.overOrUnder = overOrUnder;
	}
	
	public abstract void update();
	public abstract void render(Graphics2D g2d, float interpolation);
	
	public boolean intersects(Entity other)
	{
		return (this.x < other.x + other.width && this.x + this.width > other.x && this.y < other.y + other.height && this.y + this.height > other.y);
	}
	
	public void die() {dead = true;}
	public boolean isDead() {return dead;}
	public static void setGame(Game game) {gameInstance = game;}
	
	public void setPosition(int x, int y) {this.x = x; this.y = y;}
	public void setHorizontalSpeed(int dx) {this.dx = dx;}
	public void setVerticalSpeed(int dy) {this.dy = dy;}
	public void resetSpeed() {this.dx = 0; this.dy = 0;}
	public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
}
