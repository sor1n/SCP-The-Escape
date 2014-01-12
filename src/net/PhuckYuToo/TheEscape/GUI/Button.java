package net.PhuckYuToo.TheEscape.GUI;

import static org.lwjgl.opengl.GL11.*;
import java.util.Random;

import net.DarkKnight.TheEscape.render.Render;
import net.PhuckYuToo.TheEscape.Main;
import net.PhuckYuToo.TheEscape.Vector2D;
import net.PhuckYuToo.TheEscape.audio.SoundSystem.Sound;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class Button extends GUIComponent
{
	public Vector2D pos;
	private float width, height;
	private Texture[] textures;
	private Texture texture;
	private String text;
	private TrueTypeFont font = Main.getFont(45f);
	private boolean isHover = false, isClicked = false, hasBeenClicked = false;
	
	public Button(Vector2D pos, float width, float height, String txt)
	{
		this.width = width;
		this.height = height;
		this.pos = pos;
		text = txt;
		textures = new Texture[4];
		textures[0] = Main.loadPNG("button");
		for(int i = 1; i < textures.length; i++) textures[i] = Main.loadPNG("button" + i);
		texture = textures[new Random().nextInt(textures.length)];
	}
	
	public boolean isMouseOver()
	{
		return Main.intBetween(Mouse.getX(), pos.getX()*(int)width, pos.getX()*(int)width + getButtonWidth()*(int)width) && Main.intBetween(Mouse.getY(), Main.HEIGHT - pos.getY()*(int)height - getButtonHeight()*(int)height, Main.HEIGHT - pos.getY()*(int)height);
	}
	
	public boolean hasMouseBeenReleased()
	{
		if(hasBeenClicked && !Mouse.isButtonDown(0)){
			hasBeenClicked = false;
			if(isMouseOver()) return true;
		}
		return false;
	}
	
	public void tick(int delta)
	{
		if(isMouseOver()) isHover = true;
		else isHover = false;
		
		if(isHover && Mouse.isButtonDown(0)) isClicked = true;
		else isClicked = false;
		if(Mouse.isButtonDown(0) && isMouseOver()){
			isClicked = true;
			hasBeenClicked = true;
		}
		else isClicked = false;
		if(hasMouseBeenReleased()) onButtonClicked();
	}
	
	public void onButtonClicked()
	{
		Main.soundSystem.playSound(Sound.CLICK, 1f, Options.SOUND_VOL, false);
	}
	
	public void render()
	{
		glPushMatrix();
		glScalef(width, height, 0f);
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		Render.drawTexturedRectangle(pos.getX(), pos.getY(), 0, 0, texture);
		new Color(1f, 1f, 1f, 0.1f).bind();
		if(isHover && !isClicked) glRecti(pos.getX(), pos.getY(), pos.getX() + getButtonWidth(), pos.getY() + getButtonHeight());
		new Color(0.6f, 0f, 0f, 0.3f).bind();
		if(isClicked) glRecti(pos.getX(), pos.getY(), pos.getX() + getButtonWidth(), pos.getY() + getButtonHeight());
		glPopMatrix();
		font.drawString(pos.getX() + ((getButtonWidth() * width) / 2) - (font.getWidth(text) / 2) + 10, pos.getY() * height + 7, text, Color.white);
	}
	
	public String getText()
	{
		return text;
	}
	
	public int getButtonHeight()
	{
		return texture.getTextureHeight();
	}
	
	public int getButtonWidth()
	{
		return texture.getTextureWidth();
	}
	
	public boolean isClicked()
	{
		return isClicked;
	}
}
