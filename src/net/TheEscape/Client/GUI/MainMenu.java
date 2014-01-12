package net.TheEscape.Client.GUI;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.TheEscape.Client.Main;
import net.TheEscape.Client.Vector2D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class MainMenu extends GUI
{
	private Texture bg, bar;
	private int textureX, titleX;
	private float xSpeed = 0.0f;
	private TrueTypeFont title = Main.getFont(52), version = Main.getFont(40);

	private List<GUIComponent> guiComponents = new CopyOnWriteArrayList<GUIComponent>();

	public Button play = new Button(new Vector2D(15, 70), 2f, 2f, "Play");
	public Button options = new Button(new Vector2D(15, 70 + play.getButtonHeight() + 4), 2f, 2f, "Options")
	{
		public void onButtonClicked()
		{
			super.onButtonClicked();
			Main.optionsMenu.setActive(true);
			isActive = false;
		}
	};
	public Button credits = new Button(new Vector2D(15, 70 + play.getButtonHeight() * 2 + 8), 2f, 2f, "Credits");
	public Button exit = new Button(new Vector2D(15, 70 + play.getButtonHeight() * 3 + 12), 2f, 2f, "Exit")
	{
		public void onButtonClicked()
		{
			super.onButtonClicked();
			clearTextures();
			credits.clearTextures();
			play.clearTextures();
			options.clearTextures();
			Main.gameInstance.close();
		}
	};

	public synchronized List<GUIComponent> getGUIComponents()
	{
		return guiComponents;
	}

	public MainMenu()
	{
		bg = Main.loadPNG("Main");
		bar = Main.loadPNG("Bar");
		textureX = -bar.getTextureWidth();
		titleX = Main.WIDTH;
		getGUIComponents().add(play);
		getGUIComponents().add(options);
		getGUIComponents().add(credits);
		getGUIComponents().add(exit);
	}

	public void tick(int delta)
	{
		if(isActive)
		{
			if(textureX < 0 + bar.getImageWidth()) textureX += 3;
			if(titleX > 15) titleX -= xSpeed;
			if(xSpeed < 16f) xSpeed += 0.4f;
			for(int i = 0; i < getGUIComponents().size(); i++) getGUIComponents().get(i).tick(delta);
		}
	}

	public void render()
	{
		if(isActive)
		{
			glPushMatrix();
			glEnable(GL_TEXTURE_2D);
			bg.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0); // top left
			glVertex2f(0, 0);
			glTexCoord2f(0, 1); // bottom left 
			glVertex2f(0, Main.HEIGHT);
			glTexCoord2f(1, 1); // bottom right
			glVertex2f(Main.WIDTH, Main.HEIGHT);
			glTexCoord2f(1, 0); // top right
			glVertex2f(Main.WIDTH, 0);
			glEnd();
			bar.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0); // top left
			glVertex2f(textureX - bar.getImageWidth(), 0);
			glTexCoord2f(0, 1); // bottom left 
			glVertex2f(textureX - bar.getImageWidth(), Main.HEIGHT);
			glTexCoord2f(1, 1); // bottom right
			glVertex2f(Main.widthCenter + textureX - bar.getImageWidth(), Main.HEIGHT);
			glTexCoord2f(1, 0); // top right
			glVertex2f(Main.widthCenter + textureX - bar.getImageWidth(), 0);
			glEnd();
			glDisable(GL_TEXTURE_2D);
			glPopMatrix();
			title.drawString(titleX, 10, Main.TITLE, Color.white);
			version.drawString(Main.WIDTH - version.getWidth(Main.VERSION) - 5, Main.HEIGHT - 45, Main.VERSION, Color.white);
			for(int i = 0; i < getGUIComponents().size(); i++) getGUIComponents().get(i).render();
		}
	}
}
