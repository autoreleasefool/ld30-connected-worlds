package ca.josephroque.ld30;

public interface Constants
{

	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	public static final int TICKS_PER_SECOND = 30;
	public static final int SKIP_TICKS = 1000000000 / TICKS_PER_SECOND;
	public static final int MAX_FRAMESKIPS = 5;
	
	public static final boolean shouldPrintToConsole = true;
}
