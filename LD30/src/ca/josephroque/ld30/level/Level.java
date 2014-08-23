package ca.josephroque.ld30.level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import ca.josephroque.ld30.Assets;
import ca.josephroque.ld30.Game;
import ca.josephroque.ld30.entity.Entity;

public class Level
{
	
	private Game game = null;
	private ArrayList<Entity> entities = null;
	private byte[][] layout = null;
	
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
		entities = new ArrayList<Entity>();
		for (int x = 0; x < layout.length; x++)
		{
			for (int y = 0; y < layout[x].length; y++)
			{
				switch(layout[x][y])
				{
				
				}
			}
		}
	}

	public Level(Game game, int id)
	{
		this.game = game;
		this.id = id;
		this.layout = Assets.loadLevelLayout(id);
	}
}
