package net.PixelThrive.Client.GUI;

import java.awt.Rectangle;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.renders.Render;

public class Toggle 
{
	private int x, y, barX;
	private boolean toggle;
	private String label;
	private Rectangle bar = new Rectangle();
	private boolean isHover = false, isClicked = false;

	public Toggle(int x, int y, boolean start, String label)
	{
		this.x = x;
		this.y = y;
		toggle = start;
		this.label = label;
		if(start) barX = x + 23;
		else barX = x;
	}

	public void tick()
	{
		bar.setBounds(barX, y - 2, 25, 14);
		if(bar.contains(Main.mouseX, Main.mouseY)) isHover = true;
		else isHover = false;
		if(isClicked)
		{
			barX = Main.mouseX;
			if(Main.mouseX > x + 25)
			{
				barX = x + 23;
				toggle = true;
			}
			if(Main.mouseX < x + 25)
			{
				barX = x;
				toggle = false;
			}
		}
		if(Main.key != null && Main.key.isMouseLeft && isHover) isClicked = true;
		if(!Main.key.isMouseLeft) isClicked = false;
		if(toggle) barX = x + 23;
		else barX = x;
	}

	public void render()
	{
		Text.drawStringWithShadow(label, x + 25 - (Render.stringWidth(label, 9) / 2), y - 4, 0xFFFFFF, 9, Main.gameFont);
		Render.setColor(0, 210, 0);
		Render.fillRect(x - 1, y, 25, 10);
		Render.setColor(210, 0, 0);
		Render.fillRect(x + 24, y, 25, 10);
		Render.setColor(0x000000);
		Render.drawRect(x - 1, y, 50, 10);
		Render.setColor(0xFFFFFF);
		Render.fillRect(bar.x, bar.y, bar.width, bar.height);
		Render.setColor(0x000000);
		Render.drawRect(bar.x, bar.y, bar.width, bar.height);
		Render.setColor(0x111111);
		Render.fillRect(bar.x + 12, bar.y + 2, 2, 11);
		Render.setColor(140, 140, 140, 120);
		if(isHover) Render.fillRect(bar.x, bar.y, bar.width + 1, bar.height + 1);
	}

	public boolean getValue()
	{
		return toggle;
	}
	
	public void setValue(boolean b)
	{
		toggle = b;
	}
	
	public void setValue(int i)
	{
		toggle = Main.intToBoolean(i);
	}
}
