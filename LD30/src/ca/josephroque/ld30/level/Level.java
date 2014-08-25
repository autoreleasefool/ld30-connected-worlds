package ca.josephroque.ld30.level;

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

public class Level
{
	
	private Game game = null;
	private ArrayList<Entity> entities = null;
	private BufferedImage map = null;
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
				it.remove();
			}
			else
			{
				e.update();
			}
		}
	}
	
	public void render(Graphics2D g2d, float interpolation)
	{
		if (entities == null)
		{
			return;
		}
		
		for (int i = 0; i<entities.size(); i++)
		{
			if (!entities.get(i).isDead())
			{
				entities.get(i).render(g2d, interpolation);
			}
		}
	}
	
	private void generateEntities()
	{
		entities = Assets.loadEntities(game, id, map.getHeight());
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

	public Level(Game game, int id)
	{
		this.game = game;
		this.id = id;
		this.layout = Assets.loadLevelLayout(id);
		map = generateMap(layout);
	}
	
	private BufferedImage generateMap(int[][] layout)
	{
		GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage image = graphicsConfig.createCompatibleImage(layout.length * Constants.TILE_SIZE, layout[0].length * Constants.TILE_SIZE, Transparency.OPAQUE);
		Graphics g = image.getGraphics();
		
		int xx, yy;
		for (int x = 0; x < layout.length; x++)
		{
			for (int y = 0; y < layout.length; y++)
			{
				switch(layout[x][y])
				{
				case 1:
					g.drawImage(Assets.terrain.getSubimage(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, null);
					break;
				case 129:
					g.drawImage(Assets.terrain.getSubimage(0, 256, Constants.TILE_SIZE, Constants.TILE_SIZE), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, null);
					break;
				default: 
					xx = layout[x][y] % 16;
					yy = layout[x][y] / 16;
					g.drawImage(Assets.terrain.getSubimage(xx, yy, Constants.TILE_SIZE, Constants.TILE_SIZE), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, null);
					break;
				}
			}
		}
		g.dispose();
		return image;
	}
}
