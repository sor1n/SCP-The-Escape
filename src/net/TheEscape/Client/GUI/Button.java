package net.TheEscape.Client.GUI;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import net.DarkKnight.TheEscape.render.Render;
import net.TheEscape.Client.Main;
import net.TheEscape.Client.Vector2D;
import net.TheEscape.Client.audio.SoundSystem.Sound;

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
		if(hasBeenClicked && !Mouse.isButtonDown(0))
		{
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
		if(Mouse.isButtonDown(0) && isMouseOver())
		{
			isClicked = true;
			hasBeenClicked = true;
		}
		else isClicked = false;
		if(hasMouseBeenReleased()) onClicked();
	}

	public void onClicked()
	{
		Sound.CLICK.playSound(1f);
	}

	public void render()
	{
		glDisable(GL_TEXTURE_2D);
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		Render.drawScaledTexturedRectangle(pos.getX(), pos.getY(), width, height, texture);
		
		if(isHover && !isClicked){
			glColor4f(0f, 0f, 0f, 0.4f);
			glRecti(pos.getX(), pos.getY(), pos.getX() + getButtonWidth(), pos.getY() + getButtonHeight());
		}
		if(isClicked){
			glColor4f(1f, 0f, 0f, 0.6f);
			glRecti(pos.getX(), pos.getY(), pos.getX() + getButtonWidth(), pos.getY() + getButtonHeight());
		}
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

	public void clearTextures()
	{
		for(Texture t : textures) t.release();
		texture.release();
	}
}
