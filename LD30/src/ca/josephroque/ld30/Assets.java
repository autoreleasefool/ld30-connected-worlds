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

import javax.imageio.ImageIO;

public class Assets
{

	public static final BufferedImage terrain = loadBufferedImage("terrain.png");
	
	private static BufferedImage loadBufferedImage(String fileName)
	{
		Image image = null;
		try
		{
			image = ImageIO.read(Assets.class.getResource("ca/josephroque/ld30/_resources/images/" + fileName));
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
	
	public static byte[][] loadLevelLayout(int id)
	{
		byte[][] levelLayout = null;
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(Assets.class.getResourceAsStream("ca/josephroque/ld30/_resources/text/level" + id + ".txt")));
			String line = reader.readLine();
			String layout = "";
			while (line != null)
			{
				layout += line + "\n";
				line = reader.readLine();
			}
			
			String[] splitInput = layout.split("\n");
			levelLayout = new byte[splitInput[0].length()][splitInput.length];
			for (int x = 0; x < levelLayout.length; x++)
			{
				for (int y = 0; y < levelLayout[x].length; y++)
				{
					levelLayout[x][y] = Byte.parseByte(splitInput[x].substring(y, y + 1));
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
}
