package ca.josephroque.ld30.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ca.josephroque.ld30.Assets;

public class Door extends Entity implements Explodable
{
	private boolean exit;
	private int deathTimer = -1;
	
	public void update()
	{
		if (!exit)
		{
			if (deathTimer < 0)
			{
				deathTimer = 30;
			}
			else if (--deathTimer == 0)
			{
				die();
			}
		}
	}
	
	public void render(Graphics2D g2d, float interpolation)
	{
		//g2d.drawImage(Assets.terrain.getSubimage(352, 160 + (overOrUnder ? 0:256), width, height), x - Player.getXOffset(), y - Player.getYOffset(), null);
	}

	public Door(int x, int y, boolean overOrUnder, boolean exit)
	{
		super(x, y, 32, 75, overOrUnder, false);
		this.exit = exit;
	}
	
	public void generateParticles(ArrayList<Particle> particles)
	{
		BufferedImage image = Assets.terrain.getSubimage(352, 160 + (overOrUnder ? 0:256), width, height);
		for (int i = 0; i < image.getWidth(); i += 3)
		{
			for (int j = 0; j < image.getHeight(); j += 3)
			{
				particles.add(new Particle(gameInstance, x + i, y + j, new Color(image.getRGB(i, j)), overOrUnder));
			}
		}
	}
}
