package net.TheEscape.Client.GUI;

public abstract class GUI 
{
	protected boolean isActive = false;

	public boolean isActive()
	{
		return isActive;
	}
	
	public void setActive(boolean b)
	{
		isActive = b;
	}
}
