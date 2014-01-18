package net.TheEscape.Client.ClassD;

import net.TheEscape.Client.Main;
import net.TheEscape.Client.Vector2Dd;
import net.TheEscape.Client.render.Render;

import org.newdawn.slick.opengl.Texture;

public class Tile
{
	public static final int TILE_SIZE = 16;
	
	private String name, path;
	private Texture texture;
	
	public static final Texture TEX_MARBLE = initTexture(Main.PATH, "Marble");
	
	public static final Tile AIR = new Tile("Air").setTexture(null);
	public static final Tile MARBLE = new Tile("Marble").setTexture(TEX_MARBLE);
	
	public Tile(String name)
	{
		this.name = name;
	}
	
	public void tick(int delta){}
	
	public String getName()
	{
		return name;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public Tile setPath(String path)
	{
		this.path = path;
		return this;
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
	
	public static Texture initTexture(String path, String name)
	{
		return Main.loadPNG(name, "tiles");
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
