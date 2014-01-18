package net.TheEscape.Client.GUI;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import net.TheEscape.Client.Main;
import net.TheEscape.Client.Vector2D;

public class Options extends GUI
{
	public static int ANTI_ALIAS = 1;
	public static float SOUND_VOL = 1.0f, MUSIC_VOL = 1.0f;

	private static List<GUIComponent> guiComponents = new CopyOnWriteArrayList<GUIComponent>();

	private Texture bg;
	private TrueTypeFont font = Main.getFont(45f);

	private Slider soundVol = new Slider(new Vector2D(Main.widthCenter - 280, Main.heightCenter - 120), 200, 100, "Sound Volume")
	{
		public void onUpdate()
		{
			super.onUpdate();
			SOUND_VOL = getValue() / 100;
		}
		public void add()
		{
			getGUIComponents().add(this);
		}
	};

	private Slider musicVol = new Slider(new Vector2D(Main.widthCenter + 100, Main.heightCenter - 120), 200, 100, "Music Volume")
	{
		public void onUpdate()
		{
			super.onUpdate();
			MUSIC_VOL = getValue() / 100;
		}
		public void add()
		{
			getGUIComponents().add(this);
		}
	};

	private Button back = new Button(new Vector2D(15, 15), 2f, 2f, "Back")
	{
		public void onClicked()
		{
			super.onClicked();
			Main.menu.setActive(true);
			isActive = false;
		}
		public void add()
		{
			getGUIComponents().add(this);
		}
	};
	
	private Tickbox antiAlias = new Tickbox(Main.widthCenter - 10, Main.heightCenter + 100, 40, 40, true, "Anti Alias")
	{
		public void add()
		{
			getGUIComponents().add(this);
		}
		public void onUpdate()
		{
			Options.ANTI_ALIAS = Main.booleanToInt(getValue());
			Main.gameInstance.refreshFonts();
		}
	};

	public static synchronized List<GUIComponent> getGUIComponents()
	{
		return guiComponents;
	}

	public Options()
	{
		bg = Main.loadPNG("Main", "gui");
		soundVol.add();
		musicVol.add();
		back.add();
		antiAlias.add();
	}
	
	public void refreshFont()
	{
		font = Main.getFont(45f);
		for(int i = 0; i < getGUIComponents().size(); i++) getGUIComponents().get(i).refreshFont();
	}

	public void tick(int delta)
	{
		if(isActive)
		{
			for(int i = 0; i < getGUIComponents().size(); i++) getGUIComponents().get(i).tick(delta);
		}
	}

	public void render()
	{
		if(isActive)
		{
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
			glDisable(GL_TEXTURE_2D);
			for(int i = 0; i < getGUIComponents().size(); i++) getGUIComponents().get(i).render();
			font.drawString(Main.widthCenter - (font.getWidth("Options") / 2), 20, "Options", Color.white);
		}
	}
}
