package net.TheEscape.Client.render;

import static org.lwjgl.opengl.GL11.*;
import net.TheEscape.Client.Vector2D;
import net.TheEscape.Client.ClassD.Tile;

import org.newdawn.slick.Color;
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
	
	public static void drawTile(float x, float y, Tile tile, float scale)
	{
		glColor3f(1f, 1f, 1f);
		glPushMatrix();
		glLoadIdentity();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glScalef(scale, scale, 1f);
		glEnable(GL_TEXTURE_2D);
		tile.getTexture().bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, y + Tile.TILE_SIZE);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(x + Tile.TILE_SIZE, y + Tile.TILE_SIZE);
		glTexCoord2f(1, 0); // top right
		glVertex2f(x + Tile.TILE_SIZE, y);
		glEnd();
	}
	
	public static void drawTriangle(float x, float y, int sizeX, int sizeY, float r, float g, float b, float angle, float rX, float rY, float rZ)
	{
		glPushMatrix();
		glColor3f(r, g, b);
		glBegin(GL_QUADS);
		glTranslatef(1f, 1f, 1f);
		glLoadIdentity();
		glRotatef(angle, rX, rY, rZ);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x + (sizeX / 2), y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, sizeY + y);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(sizeX + x, sizeY + y);
		glTexCoord2f(1, 0); // top right
		glVertex2f(x + (sizeX / 2), y);
		glEnd();
		glPopMatrix();
	}
	
	public static void drawLine(Vector2D pos1, Vector2D pos2, float r, float g, float b)
	{
		glColor3f(r, g, b);
	    glBegin(GL_LINES);
	    glLineWidth(5.0f);
	    glVertex2f(pos1.getX(), pos1.getY());
	    glVertex2f(pos2.getX(), pos2.getY());
	    glEnd();
	}
	
	public static void drawRectangle(float x, float y, int sizeX, int sizeY, int red, int green, int blue)
	{
		glPushMatrix();
		new Color(red, green, blue).bind();
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
