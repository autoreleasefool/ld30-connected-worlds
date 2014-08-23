package ca.josephroque.ld30;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ConnectedWorlds
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				GameCanvas gc;
				
				JFrame frame = new JFrame();
				frame.setTitle("LD30 - Connected Worlds");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.getContentPane().add(gc = new GameCanvas());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				
				gc.start();
			}
		});
	}
}
