package net.TheEscape.Client;

public class Vector2Dd 
{
	private double x, y;

	public Vector2Dd(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public void setY(double y)
	{
		this.y = y;
	}
	
	public void addX(double x)
	{
		this.x += x;
	}

	public void addY(double y)
	{
		this.y += y;
	}
}
