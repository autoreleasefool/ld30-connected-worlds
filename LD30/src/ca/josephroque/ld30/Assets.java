package ca.josephroque.ld30;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import ca.josephroque.ld30.entity.Entity;
import ca.josephroque.ld30.entity.Guard;

public class Assets
{

	public static final BufferedImage terrain = loadBufferedImage("terrain.png");
	
	private static BufferedImage loadBufferedImage(String fileName)
	{
		Image image = null;
		try
		{
			image = ImageIO.read(Assets.class.getResource("/ca/josephroque/ld30/_resources/images/" + fileName));
		}
		catch (IOException io)
		{
			if (Constants.shouldPrintToConsole)
			{
				io.printStackTrace();
			}
		}
		
		GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage buffImage = graphicsConfig.createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);
		Graphics tempGraphics = buffImage.getGraphics();
		tempGraphics.drawImage(image, 0, 0, null);
		tempGraphics.dispose();
		return buffImage;
	}
	
	public static int[][] loadLevelLayout(int id)
	{
		int[][] levelLayout = null;
		
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(Assets.class.getResourceAsStream("/ca/josephroque/ld30/_resources/text/level" + id + "_terrain.txt")));
			String line = reader.readLine();
			String layout = "";
			while (line != null)
			{
				layout += line + "\n";
				line = reader.readLine();
			}
			
			String[] splitInput = layout.split("\n");
			String[] layoutUnparsed = splitInput[0].split(", *");;
			levelLayout = new int[layoutUnparsed.length][splitInput.length];
			for (int y = 0; y < levelLayout[0].length; y++) {
				layoutUnparsed = splitInput[y].split(", *");
				for (int x = 0; x < levelLayout.length; x++)
				{
					levelLayout[x][y] = Integer.parseInt(layoutUnparsed[x].trim());
				}
			}
		}
		catch (IOException io)
		{
			if (Constants.shouldPrintToConsole)
			{
				io.printStackTrace();
			}
		}
		
		return levelLayout;
	}
	
	public static ArrayList<Entity> loadEntities(Game game, int id, int mapHeight)
	{
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(Assets.class.getResourceAsStream("/ca/josephroque/ld30/_resources/text/level" + id + "_entity.txt")));
			String line = reader.readLine();
			String input = "";
			while (line != null)
			{
				input += line + "\n";
				line = reader.readLine();
			}
			
			String[] entityList = input.split("\n");
			for (int i = 0; i < entityList.length; i++)
			{
				String entityName = entityList[i].substring(0, entityList[i].indexOf(":"));
				String[] stats = entityList[i].substring(entityList[i].indexOf(":") + 1).split(",");
				switch(entityName)
				{
				case "PLAYER":
					game.getPlayer(true).setPosition(Integer.parseInt(stats[0]), Integer.parseInt(stats[1]));
					game.getPlayer(false).setPosition(Integer.parseInt(stats[0]), mapHeight / 2 + (mapHeight / 2 - game.getPlayer(true).getY() - game.getPlayer(true).getHeight()));
					break;
				case "GUARD_OVER":
					entities.add(new Guard(Integer.parseInt(stats[0]), Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Integer.parseInt(stats[3]), true));
					break;
				case "GUARD_UNDER":
					entities.add(new Guard(Integer.parseInt(stats[0]), Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Integer.parseInt(stats[3]), false));
					break;
				}
			}
		} catch (IOException io)
		{
			if (Constants.shouldPrintToConsole)
			{
				io.printStackTrace();
			}
		}
		
		return entities;
	}
}
