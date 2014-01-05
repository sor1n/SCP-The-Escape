package net.PhuckYuToo.TheEscape.GUI;

import static org.lwjgl.opengl.GL11.*;
import java.util.Random;
import net.PhuckYuToo.TheEscape.Main;
import net.PhuckYuToo.TheEscape.Vector2D;

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
	
	public void tick(int delta)
	{
		
	}
	
	public void render()
	{
		glPushMatrix();
		glScalef(width, height, 0f);
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(pos.getX(), pos.getY());
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(pos.getX(), texture.getImageHeight() + pos.getY());
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(texture.getImageWidth() + pos.getX(), texture.getImageHeight() + pos.getY());
		glTexCoord2f(1, 0); // top right
		glVertex2f(texture.getImageWidth() + pos.getX(), pos.getY());
		glEnd();
		glDisable(GL_TEXTURE_2D);
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
}
