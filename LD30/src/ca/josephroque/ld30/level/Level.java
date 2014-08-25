package ca.josephroque.ld30.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import ca.josephroque.ld30.Assets;
import ca.josephroque.ld30.Constants;
import ca.josephroque.ld30.Game;
import ca.josephroque.ld30.entity.Crate;
import ca.josephroque.ld30.entity.Entity;
import ca.josephroque.ld30.entity.Explodable;
import ca.josephroque.ld30.entity.Particle;
import ca.josephroque.ld30.entity.Player;

public class Level
{
	
	private Game game = null;
	private ArrayList<Entity> entities = null;
	private ArrayList<Particle> particles = null;
	
	private BufferedImage imgLevel = null;
	private BufferedImage imgEntities = null;
	
	private int[][] layout = null;
	private int id;
	
	public void update()
	{
		if (entities == null)
		{
			generateEntities();
		}
		
		Entity e;
		for (Iterator<Entity> it = entities.iterator(); it.hasNext(); )
		{
			e = it.next();
			if (e.isDead())
			{
				if (e instanceof Explodable) 
				{
					Explodable ex = (Explodable) e;
					ex.generateParticles(particles);
				}
				it.remove();
			}
			else
			{
				e.update();
			}
		}
		
		Particle p;
		for (Iterator<Particle> it = particles.iterator(); it.hasNext(); )
		{
			p = it.next();
			if (p.isDead())
			{
				it.remove();
			}
			else
			{
				p.update();
			}
		}
	}
	
	public void render(Graphics2D g2d, float interpolation)
	{
		if (entities == null)
		{
			return;
		}
		
		g2d.drawImage(imgLevel.getSubimage(Player.getXOffset(), Player.getYOffset(), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), 0, 0, null);
		Graphics2D g = imgEntities.createGraphics();
		g.setBackground(new Color(0, 0, 0, 0));
		g.clearRect(0, 0, imgEntities.getWidth(), imgEntities.getHeight());
		for (int i = 0; i<entities.size(); i++)
		{
			if (!entities.get(i).isDead() && (entities.get(i).getX() < Player.getXOffset() + Constants.SCREEN_WIDTH
												&& entities.get(i).getX() + entities.get(i).getWidth() > Player.getXOffset()
												&& entities.get(i).getY() < Player.getYOffset() + Constants.SCREEN_HEIGHT
												&& entities.get(i).getY() + entities.get(i).getHeight() > Player.getYOffset()))
			{
				entities.get(i).render(g, interpolation);
			}
		}
		
		for (int i = 0; i<particles.size(); i++)
		{
			particles.get(i).render(g, interpolation);
		}
		
		g.dispose();
		g2d.drawImage(imgEntities, 0, 0, null);
	}
	
	private void generateEntities()
	{
		entities = Assets.loadEntities(game, id, imgLevel.getHeight());
		for (int x = 0; x < layout.length; x++)
		{
			for (int y = 0; y < layout[x].length; y++)
			{
				switch(layout[x][y])
				{
				case 1: case 129:
					entities.add(new Crate(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, layout[x][y] == 1));
					break;
				}
			}
		}
	}
	
	public boolean isBlockSolid(int x, int y)
	{
		
		switch(layout[x][y])
		{
		case 0: case 1: case 128: case 129: return false;
		default: return true;
		}
	}
	
	public boolean isPlayerHittingEntity(Player player)
	{
		for (int i = 0; i<entities.size(); i++)
		{
			if (entities.get(i).isSolid() && player.intersects(entities.get(i)))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isPlayerOnTopOfEntity(Player player)
	{
		for (int i = 0; i<entities.size(); i++)
		{
			if (entities.get(i).isSolid() && player.getX() < entities.get(i).getX() + entities.get(i).getWidth()
				&& player.getX() + player.getWidth() > entities.get(i).getX()
				&& ((player.isOver() && player.getY() + player.getHeight() == entities.get(i).getY())
					|| (!player.isOver() && player.getY() == entities.get(i).getY() + entities.get(i).getHeight())))
			{
				return true;
			}
		}
		return false;
	}

	public Level(Game game, int id)
	{
		this.game = game;
		this.id = id;
		this.layout = Assets.loadLevelLayout(id);
		particles = new ArrayList<Particle>();
		
		GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		imgLevel = graphicsConfig.createCompatibleImage(layout.length * Constants.TILE_SIZE, layout[0].length * Constants.TILE_SIZE, Transparency.OPAQUE);
		generateMap(imgLevel, layout);
		imgEntities = graphicsConfig.createCompatibleImage(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Transparency.TRANSLUCENT);
	}
	
	private void generateMap(BufferedImage image, int[][] layout)
	{
		Graphics g = image.getGraphics();
		
		int xx, yy;
		for (int x = 0; x < layout.length; x++)
		{
			for (int y = 0; y < layout[x].length; y++)
			{
				switch(layout[x][y])
				{
				case 1:
				case 129:
					g.drawImage(Assets.terrain.getSubimage(0, (layout[x][y] == 1) ? 0:256, Constants.TILE_SIZE, Constants.TILE_SIZE), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, null);
					break;
				case 2:
				case 130:
					g.drawImage(Assets.terrain.getSubimage(64, (layout[x][y] == 2) ? 0:256, Constants.TILE_SIZE, Constants.TILE_SIZE), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, null);
					if (x == 0 || (x > 0 && (layout[x - 1][y] != 2 && layout[x - 1][y] != 130)))
						g.drawImage(Assets.terrain.getSubimage(96, (layout[x][y] == 2) ? 0:256, 3, Constants.TILE_SIZE), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, null);
					if (x == layout.length - 1 || (x < layout.length - 1 && (layout[x + 1][y] != 2 && layout[x + 1][y] != 130)))
						g.drawImage(Assets.terrain.getSubimage(96, (layout[x][y] == 2) ? 0:256, 3, Constants.TILE_SIZE), x * Constants.TILE_SIZE + 29, y * Constants.TILE_SIZE, null);
					if (y == 0 || (y > 0 && (layout[x][y - 1] != 2 && layout[x][y - 1] != 130)))
						g.drawImage(Assets.terrain.getSubimage(96, (layout[x][y] == 2) ? 0:256, Constants.TILE_SIZE, 3), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, null);
					if (y == layout[x].length - 1 || (y < layout[x].length && (layout[x][y + 1] != 2 && layout[x][y + 1] != 130)))
						g.drawImage(Assets.terrain.getSubimage(96, (layout[x][y] == 2) ? 0:256, Constants.TILE_SIZE, 3), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE + 29, null);
					break;
				default: 
					xx = layout[x][y] % 16;
					yy = layout[x][y] / 16;
					g.drawImage(Assets.terrain.getSubimage(xx * Constants.TILE_SIZE, yy * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, null);
					break;
				}
			}
		}
		g.dispose();
	}
	
	public int getWidth() {return imgLevel.getWidth();}
	public int getHeight() {return imgLevel.getHeight();}
}
