package net.TheEscape.Client;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class Sheet 
{
	private String name;
	private Texture texture;

	public static final Sheet TILE_SCP = new Sheet("Tile_SCPs");

	public Sheet(String name)
	{
		this.name = name;
		texture = Main.loadPNG(Main.PATH, name, "tiles");
	}

	public String getName()
	{
		return name;
	}

	//	public Texture getSheet()
	//	{
	//		return sheet;
	//	}

	public void drawTiled(int screenWidth, int screenHeight)
	{
		Color.white.bind();
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		int numberPerRow = 1;
		double numberOfRows = 1;
		glBegin(GL_QUADS);
		for(int j = 0; j < numberOfRows; j++)
		{
			//System.out.print("{");
			for(int i = 0; i < numberPerRow; i++)
			{
				//top left
				glTexCoord2f(0, 0);
				glVertex2f(texture.getTextureWidth() * i, texture.getTextureHeight() * j);

				//top right
				glTexCoord2f(1, 0);
				glVertex2f(texture.getTextureWidth() * (i + 1), texture.getTextureHeight() * j);

				//bottom right
				glTexCoord2f(1, 1);
				glVertex2f(texture.getTextureWidth() * (i + 1), texture.getTextureHeight() * (j + 1));

				//bottom left
				glTexCoord2f(0, 1);
				glVertex2f(texture.getTextureWidth() * i, texture.getTextureHeight() * (j + 1));
				//System.out.print("[]");
			}
			//System.out.println("}");
		}
		//close gl
		glEnd();
	}

	public void drawSprite()
	{
		Color.white.bind();
		texture.bind();
		glBegin(GL_QUADS);
		int i = 0;
		int j = 0;
		glTexCoord2f(0, 0);
		glVertex2f(texture.getTextureWidth() * i, texture.getTextureHeight() * j);

		//top right
		glTexCoord2f(1, 0);
		glVertex2f(texture.getTextureWidth() * (i + 1), texture.getTextureHeight() * j);

		//bottom right
		glTexCoord2f(1, 1);
		glVertex2f(texture.getTextureWidth() * (i + 1), texture.getTextureHeight() * (j + 1));

		//bottom left
		glTexCoord2f(0, 1);
		glVertex2f(texture.getTextureWidth() * i, texture.getTextureHeight() * (j + 1));
		glEnd();
	}
}
