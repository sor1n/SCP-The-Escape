package net.TheEscape.Client.ClassD;

import net.TheEscape.Client.Main;
import net.TheEscape.Client.Vector2Dd;
import net.TheEscape.Client.render.Render;

import org.newdawn.slick.opengl.Texture;

public class Tile
{
	public static final int TILE_SIZE = 16;
	
	public static final Texture TEX_MARBLE = initTexture("Marble");
	
	public static final Tile AIR = new Tile("Air").setTexture(null);
	public static final Tile MARBLE = new Tile("Marble").setTexture(TEX_MARBLE);
	
	private String name;
	private Texture texture;
	
	public Tile(String path)
	{
		this.name = path;
	}
	
	public void tick(int delta){}
	
	public boolean hasTexture()
	{
		return (texture == null ? false : true);
	}
	
	public Tile setTexture(Texture texture)
	{
		this.texture = texture;
		return this;
	}
	
	public void render(int x, int y)
	{
		if(hasTexture()) Render.drawTile(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, this, 5.5f);
	}
	
	public String getName()
	{
		return name;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public boolean onScreen(Vector2Dd cam, int x, int y)
	{
		if(Main.intBetween(x * Tile.TILE_SIZE, (int)cam.getX(), (int)cam.getX() + Main.WIDTH) && Main.intBetween(y * Tile.TILE_SIZE, (int)cam.getY(), (int)cam.getY() + Main.HEIGHT)) return true;
		else return false;
	}
	
	public static Texture initTexture(String path)
	{
		return Main.loadPNG("tiles/" + path);
	}
}
