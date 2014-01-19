package net.TheEscape.Client.ClassD;

import net.TheEscape.Client.Main;
import net.TheEscape.Client.Vector2Dd;
import net.TheEscape.Client.render.Render;

import org.newdawn.slick.opengl.Texture;

public class Tile
{
	public static final int TILE_SIZE = 16;
	
	private String name;
	private Texture texture;
	
//	public static final Texture TEX_MARBLE = initTexture("Marble");
	
	public static final Tile AIR = new Tile("Air", Main.PATH, false);
	public static final Tile MARBLE = new Tile("Marble", Main.PATH, true);
	
	public Tile(String name, String path, boolean hasTexture)
	{
		this.name = name;
		if(hasTexture) texture = initTexture(name, path);
		else texture = null;
	}
	
	public void tick(int delta){}
	
	public String getName()
	{
		return name;
	}
	
	public boolean hasTexture()
	{
		return (texture == null ? false : true);
	}
	
	public Tile setTexture(Texture texture)
	{
		this.texture = texture;
		return this;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public static Texture initTexture(String name, String path)
	{
		return Main.loadPNG(path, name, "tiles");
	}
	
	public boolean onScreen(Vector2Dd cam, int x, int y)
	{
		if(Main.intBetween(x * Tile.TILE_SIZE, (int)cam.getX(), (int)cam.getX() + Main.WIDTH) && Main.intBetween(y * Tile.TILE_SIZE, (int)cam.getY(), (int)cam.getY() + Main.HEIGHT)) return true;
		else return false;
	}

	public void render(int x, int y)
	{
		if(hasTexture()) Render.drawTile(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, this, 5.5f);
	}
}
