package net.TheEscape.Client.GUI;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;

import net.TheEscape.Client.Main;
import net.TheEscape.Client.Vector2D;
import net.TheEscape.Client.audio.SoundSystem.Sound;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Slider extends GUIComponent
{
	public Vector2D pos;
	private int width;
	private int max, current;
	private String text;
	private Rectangle slider = new Rectangle();
	private boolean isHover = false, isClicked = false;
	private TrueTypeFont font = Main.getFont(35f), smallFont = Main.getFont(27f);

	public Slider(Vector2D pos, int maxAmount, int start, String text)
	{
		this.pos = pos;
		max = maxAmount;
		current = start;
		width = max + 4;
		this.text = text;
	}

	public void tick(int delta)
	{
		slider.setBounds(pos.getX() + current - 23, pos.getY() - 3, 46, 46);
		if(slider.contains(Mouse.getX(), Main.HEIGHT - Mouse.getY())) isHover = true;
		else isHover = false;
		if(isHover && Mouse.isButtonDown(0)) isClicked = true;
		if(isClicked)
		{
			int mouseX = Mouse.getX();
			if(mouseX > pos.getX() + width) mouseX = pos.getX() + width;
			if(mouseX < pos.getX()) mouseX = pos.getX();
			current = (mouseX - pos.getX());
			if(current > max) current = max;
			if(current < 0) current = 0;
			slider.x = mouseX - 23;
			if(!Mouse.isButtonDown(0))
			{
				onUpdate();
				isClicked = false;
			}
		}
	}

	public void render()
	{
		glDisable(GL_TEXTURE_2D);
		glColor3f(1f, 1f, 1f);
		glRecti(pos.getX(), pos.getY(), pos.getX() + width, pos.getY() + 40);
		glColor3f(0f, 0f, 0f);
		glRecti(pos.getX() + 4, pos.getY() + 4, pos.getX() + width - 4, pos.getY() + 36);
		glColor3f(1f, 1f, 1f);
		glRecti(slider.x, slider.y, slider.x + slider.width, slider.height + slider.y);
		if(isHover || isClicked)
		{
			new Color(0f, 0f, 0f, 0.2f).bind();
			glRecti(slider.x, slider.y, slider.x + slider.width, slider.height + slider.y);
		}
		if(isClicked)
		{
			new Color(0f, 0f, 0f, 0.4f).bind();
			glRecti(slider.x, slider.y, slider.x + slider.width, slider.height + slider.y);
		}
		glColor3f(1.0f - (float)(current / max), (float)current / 100, 0f);
		glRecti(slider.x + 4, slider.y + 4, slider.x + slider.width - 4, slider.height + slider.y - 4);
		font.drawString((pos.getX() + width / 2) - (font.getWidth(text) / 2), pos.getY() - 40, text, Color.white);
		smallFont.drawString(slider.x + (slider.width / 2) - (smallFont.getWidth(String.valueOf((int)getValue())) / 2), pos.getY() + 6, String.valueOf((int)getValue()), Color.black);
	}

	public boolean isClicked()
	{
		return isClicked;
	}

	public float getValue()
	{
		return (current / (max / 100f));
	}
	
	public void onUpdate()
	{
		Sound.CLICK_SETTING.playSound(1f);
	}
}
