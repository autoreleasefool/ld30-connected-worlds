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
import ca.josephroque.ld30.entity.Player;

public class Level
{
	
	private Game game = null;
	private ArrayList<Entity> entities = null;
	
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

	public Level(Game game, int id)
	{
		this.game = game;
		this.id = id;
		this.layout = Assets.loadLevelLayout(id);
		
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
					g.drawImage(Assets.terrain.getSubimage(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, null);
					break;
				case 129:
					g.drawImage(Assets.terrain.getSubimage(0, 256, Constants.TILE_SIZE, Constants.TILE_SIZE), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, null);
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
