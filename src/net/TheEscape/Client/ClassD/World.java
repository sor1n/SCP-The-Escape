package net.TheEscape.Client.ClassD;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.TheEscape.Client.Main;
import net.TheEscape.Client.Vector2Dd;
import net.TheEscape.Client.render.Render;

public class World
{
	private Tile[][] world;
	public Vector2Dd cam = new Vector2Dd(0, 0);

	public World(boolean random, int worldX, int worldY)
	{
		world = new Tile[worldX][worldY];
		for(int x = 0; x < world.length; x++)
			for(int y = 0; y < world[0].length; y++)
				setTile(x, y, Tile.AIR);
		if(random) generate();
	}

	public void setTile(int x, int y, Tile tile)
	{
		if(isInWorld(x, y)) world[x][y] = tile;
	}

	public Tile getTile(int x, int y)
	{
		if(isInWorld(x, y)) return world[x][y];
		else return Tile.AIR;
	}

	private void generate()
	{
		for(int x = 0; x < world.length; x++)
			for(int y = 0; y < world[0].length; y++)
				setTile(x, y, Tile.MARBLE);
	}

	public void tick(int delta)
	{
		for(int x = 0; x < world.length; x++)
			for(int y = 0; y < world[0].length; y++)
			{
				if(getTile(x, y).onScreen(cam, x, y)) getTile(x, y).tick(delta);
			}

		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) cam.addX(0.1D);
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) cam.addX(-0.1D);
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) cam.addY(-0.1D);
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) cam.addY(0.1D);
		System.out.println(cam.getX());
	}
	int hi = 0;
	public void render()
	{
		renderBackground();
		for(int x = 0; x < world.length; x++)
			for(int y = 0; y < world[0].length; y++)
				if(getTile(x, y).onScreen(cam, x, y)) getTile(x, y).render(x - (int)cam.getX(), y - (int)cam.getY());
	}

	public void renderBackground()
	{
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Render.drawRectangle(0, 0, Main.WIDTH, Main.HEIGHT, 0.02f, 0.01f, 0.1f);
		GL11.glPopMatrix();
	}

	public boolean isInWorld(int x, int y)
	{
		if(Main.intBetween(x, 0, world[0].length) && Main.intBetween(y, 0, world[1].length)) return true;
		else return false;
	}
}
