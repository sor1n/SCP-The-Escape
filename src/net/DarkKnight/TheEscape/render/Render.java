package net.DarkKnight.TheEscape.render;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

public class Render 
{
	public static void drawRectangle(float x, float y, int sizeX, int sizeY, float red, float green, float blue)
	{
		glPushMatrix();
		glColor3f(red, green, blue);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, sizeY + y);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(sizeX + x, sizeY + y);
		glTexCoord2f(1, 0); // top right
		glVertex2f(sizeX + x, y);
		glEnd();
		glPopMatrix();
	}
	
	public static void drawTexturedRectangle(float x, float y, Texture tex)
	{
		glPushMatrix();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, y + tex.getImageHeight());
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(x + tex.getImageWidth(), y + tex.getImageHeight());
		glTexCoord2f(1, 0); // top right
		glVertex2f(x + tex.getImageWidth(), y);
		glEnd();
		glPopMatrix();
	}
	
	public static void drawScaledTexturedRectangle(float x, float y, float width, float height, Texture tex)
	{
		glColor3f(1f, 1f, 1f);
		glScalef(width, height, 0f);
		glPushMatrix();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, y + tex.getImageHeight());
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(x + tex.getImageWidth(), y + tex.getImageHeight());
		glTexCoord2f(1, 0); // top right
		glVertex2f(x + tex.getImageWidth(), y);
		glEnd();
		glPopMatrix();
	}
}
