package net.PhuckYuToo.TheEscape.GUI;

import net.PhuckYuToo.TheEscape.Main;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class MainMenu 
{
	private Texture bg, bar;
	private int textureX, titleX;
	private float xSpeed = 0.0f;
	private TrueTypeFont title = Main.getFont(52), version = Main.getFont(40);

	public MainMenu()
	{
		bg = Main.loadPNG("Main");
		bar = Main.loadPNG("Bar");
		textureX = -bar.getTextureWidth();
		titleX = Main.WIDTH;
	}

	public void tick(int delta)
	{
		if(textureX < 0 + bar.getImageWidth()) textureX += 3;
		if(titleX > 15) titleX -= xSpeed;
		if(xSpeed < 16f) xSpeed += 0.4f;
	}

	public void render()
	{
		glEnable(GL_TEXTURE_2D);
		bg.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(0, 0);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(0, Main.HEIGHT);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(Main.WIDTH, Main.HEIGHT);
		glTexCoord2f(1, 0); // top right
		glVertex2f(Main.WIDTH, 0);
		glEnd();
		bar.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(textureX - bar.getImageWidth(), 0);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(textureX - bar.getImageWidth(), Main.HEIGHT);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(Main.widthCenter + textureX - bar.getImageWidth(), Main.HEIGHT);
		glTexCoord2f(1, 0); // top right
		glVertex2f(Main.widthCenter + textureX - bar.getImageWidth(), 0);
		glEnd();
		title.drawString(titleX, 10, Main.TITLE, Color.white);
		version.drawString(Main.WIDTH - version.getWidth(Main.VERSION) - 5, Main.HEIGHT - 45, Main.VERSION, Color.white);
	}
}
