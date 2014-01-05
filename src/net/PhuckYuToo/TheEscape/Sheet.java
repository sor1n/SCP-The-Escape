package net.PhuckYuToo.TheEscape;

import org.newdawn.slick.opengl.Texture;

public class Sheet 
{
	private String path;
	
	public static final Sheet TILE_SCP = new Sheet("Tile_SCPs.png");
	
	public Sheet(String path)
	{
		this.path = path;
	}
	
	public String getPath()
	{
		return path;
	}
	
//	public Texture getSheet()
//	{
//		return sheet;
//	}
//	
//	public Texture getIcon(int x, int y, int width, int height)
//	{
//		
//	}
}
