package net.TheEscape.Client.GUI;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import net.TheEscape.Client.Main;
import net.TheEscape.Client.Vector2D;
import net.TheEscape.Client.audio.SoundSystem;

public class Tickbox extends GUIComponent
{
	private int width, height;
	public Vector2D pos;
	private boolean toggle;
	private String label;
	private boolean hasBeenClicked = false;
	private TrueTypeFont font = Main.getFont(25f);

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param start
	 * @param label
	 */
	public Tickbox(int x, int y, int width, int height, boolean start, String label)
	{
		this.width = width;
		this.height = height;
		pos = new Vector2D(x, y);
		toggle = start;
		this.label = label;
	}

	public void tick(int delta)
	{
		if(hasMouseBeenReleased()) onClicked();
		if(Mouse.isButtonDown(0) && isMouseOver()) hasBeenClicked = true;
	}
	
	public void onClicked()
	{
		toggle = !toggle;
		SoundSystem.Sound.CLICK_SETTING.playSound(1f);
		onUpdate();
	}
	
	public boolean isMouseOver()
	{
		return Main.intBetween(Mouse.getX(), pos.getX(), pos.getX() + width) && Main.intBetween(Mouse.getY(), Main.HEIGHT - pos.getY() - height, Main.HEIGHT - pos.getY());
	}

	public void render()
	{
		glDisable(GL_TEXTURE_2D);
		glColor3f(1f, 1f, 1f);
		glRecti(pos.getX(), pos.getY(), pos.getX() + width, pos.getY() + height);
		if(isMouseOver()) 
		{
			glColor4f(0f, 0f, 0f, .4f);
			glRecti(pos.getX(), pos.getY(), pos.getX() + width, pos.getY() + height);
		}
		if(toggle) glColor3f(0f, 1f, 0f);
		else glColor3f(1f, 0f, 0f);
		glRecti(pos.getX() + 4, pos.getY() + 4, pos.getX() + width - 4, pos.getY() + height - 4);
		font.drawString((pos.getX() + width / 2) - (font.getWidth(label) / 2), pos.getY() - font.getHeight(), label, Color.white);
	}

	public boolean getValue()
	{
		return toggle;
	}
	
	public void setValue(boolean b)
	{
		toggle = b;
	}
	
	public void setValue(int i)
	{
		toggle = Main.intToBoolean(i);
	}
	
	public boolean hasMouseBeenReleased()
	{
		if(hasBeenClicked && !Mouse.isButtonDown(0))
		{
			hasBeenClicked = false;
			if(isMouseOver()) return true;
		}
		return false;
	}
	
	public void refreshFont()
	{
		font = Main.getFont(25f);
	}
}
