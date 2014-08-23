package ca.josephroque.ld30;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, FocusListener
{
	public boolean w, a, s, d, e, up, down, left, right, space, escape, enter;
	
	public boolean focused = true;

	@Override
	public void focusGained(FocusEvent e)
	{
		focused = true;
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		focused = false;
	}

	@Override
	public void mouseDragged(MouseEvent e){}

	@Override
	public void mouseMoved(MouseEvent e){}

	@Override
	public void mouseClicked(MouseEvent e){}

	@Override
	public void mousePressed(MouseEvent e){}

	@Override
	public void mouseReleased(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}

	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void keyPressed(KeyEvent e)
	{
		flip(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		flip(e.getKeyCode(), false);
	}

	private void flip(int keyCode, boolean pressed)
	{
		switch(keyCode)
		{
		case KeyEvent.VK_W: w = pressed; break;
		case KeyEvent.VK_A: a = pressed; break;
		case KeyEvent.VK_S: s = pressed; break;
		case KeyEvent.VK_D: d = pressed; break;
		case KeyEvent.VK_E: e = pressed; break;
		case KeyEvent.VK_UP: up = pressed; break;
		case KeyEvent.VK_DOWN: down = pressed; break;
		case KeyEvent.VK_LEFT: left = pressed; break;
		case KeyEvent.VK_RIGHT: right = pressed; break;
		case KeyEvent.VK_SPACE: space = pressed; break;
		case KeyEvent.VK_ESCAPE: escape = pressed; break;
		case KeyEvent.VK_ENTER: enter = pressed; break;
		}
	}
	
	public Input(GameCanvas gc)
	{
		gc.addKeyListener(this);
		gc.addMouseListener(this);
		gc.addFocusListener(this);
		gc.addMouseMotionListener(this);
	}
}
