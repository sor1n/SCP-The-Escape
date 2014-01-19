package net.TheEscape.Client;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import net.TheEscape.Client.Class3D.Raycast;
import net.TheEscape.Client.ClassD.World;
import net.TheEscape.Client.GUI.MainMenu;
import net.TheEscape.Client.GUI.Options;
import net.TheEscape.Client.audio.SoundSystem;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Main
{
	public static Main gameInstance;

	public static final int WIDTH = 800, HEIGHT = 600, widthCenter = WIDTH / 2, heightCenter = HEIGHT / 2;

	/** time at last frame */
	private long lastFrame;

	/** frames per second */
	private int fps;
	/** last fps time */
	private long lastFPS;

	public static final String TITLE = "SCP - The Escape", VERSION = "v0.4", PATH = "scptheescape";
	/** is VSync Enabled */
	boolean vsync;

	//Declaration
	public static MainMenu menu;
	public static Options optionsMenu;
	public static SoundSystem soundSystem;
	public static Raycast ray;
	public static World world;

	public void init()
	{
		soundSystem = new SoundSystem();
		menu = new MainMenu();
		optionsMenu = new Options();
		menu.setActive(true);
	}

	public void initGL()
	{
		glEnable(GL_TEXTURE_2D);               
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);    
		// enable alpha blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, WIDTH, HEIGHT);
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	public void start()
	{
		try
		{
			setDisplayMode(WIDTH, HEIGHT, false);
			Display.setTitle(TITLE);
			Display.setIcon(new ByteBuffer[]{new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/scptheescape/gui/Icon.png")), false, false, null)});
			Display.create();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		} 
		initGL();
		init();
		lastFPS = getTime();
		while(!Display.isCloseRequested())
		{
			int delta = getDelta();
			// Clear the screen and depth buffer
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
			tick(delta);
			render();
			Display.update();
			Display.sync(60); // cap fps to 60fps
		}
		close();
	}

	public void close()
	{
		Main.consoleMessage("You closed me :c");
		Display.destroy();
	}

	public void updateFPS()
	{
		if(getTime() - lastFPS > 1000)
		{
			Display.setTitle(TITLE + " | FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void tick(int delta)
	{
		if(menu != null) menu.tick(delta);
		if(ray != null) ray.tick(delta);
		if(optionsMenu != null) optionsMenu.tick(delta);
		if(world != null) world.tick(delta);
		soundSystem.tick(delta);
		while(Keyboard.isCreated() && Keyboard.next())
		{
			if(Keyboard.getEventKeyState())
			{
				if(Keyboard.getEventKey() == Controls.FULLSCREEN) setDisplayMode(WIDTH, HEIGHT, !Display.isFullscreen());
				else if(Keyboard.getEventKey() == Controls.VSYNC)
				{
					vsync = !vsync;
					Display.setVSyncEnabled(vsync);
					consoleMessage("vSync enabled: " + vsync);
				}
				else if(Keyboard.getEventKey() == Controls.SCREENSHOT) captureScreenshot();
			}
		}
		updateFPS();
	}
	
	public void refreshFonts()
	{
		menu.refreshFont();
	}

	public void render()
	{
		if(menu != null) menu.render();
		if(optionsMenu != null) optionsMenu.render();
		if(ray != null) ray.render();
		if(world != null) world.render();
	}

	public static void main(String args[])
	{	
		Main ggame = new Main();
		gameInstance = ggame;
		ggame.start();
	}

	/**
	 * @return An instance of the Component class.
	 */
	public static Main getGameInstance()
	{
		return gameInstance;
	}

	/**
	 * This method flips the image horizontally
	 * @param img --> BufferedImage Object to be flipped horizontally
	 * @return
	 */
	public BufferedImage flipHorizontal(BufferedImage img)
	{
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();
		return dimg;
	}

	/**
	 * This method flips the image vertically
	 * @param img --> BufferedImage object to be flipped
	 * @return
	 */
	public BufferedImage flipVertical(BufferedImage img)
	{
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
		g.dispose();
		return dimg;
	}

	public static <T> void consoleMessage(T t)
	{
		System.out.println("[SCP-TE] " + String.valueOf(t));
	}

	public static <T> void consoleError(T t)
	{
		System.err.println("[SCP-TE] " + String.valueOf(t));
	}

	public static void openWebsite(String webSite)
	{
		URI url = URI.create(webSite);
		try
		{
			java.awt.Desktop.getDesktop().browse(url);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	//	public static Image getIcon()
	//	{
	//		return icon;
	//	}

	/**
	 * Get the time in milliseconds
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public int getDelta()
	{
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	/**
	 * Set the display mode to be used 
	 * 
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen)
	{ 
		// return if requested DisplayMode is already set
		if((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen)) return;
		try 
		{
			DisplayMode targetDisplayMode = null;
			if(fullscreen)
			{
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;
				for(int i= 0; i < modes.length; i++)
				{
					DisplayMode current = modes[i];
					if((current.getWidth() == width) && (current.getHeight() == height))
					{
						if((targetDisplayMode == null) || (current.getFrequency() >= freq))
						{
							if((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel()))
							{
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against the 
						// original display mode then it's probably best to go for this one
						// since it's most likely compatible with the monitor
						if((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency()))
						{
							targetDisplayMode = current;
							break;
						}
					}
				}
			} 
			else targetDisplayMode = new DisplayMode(width, height);

			if(targetDisplayMode == null)
			{
				consoleError("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
				return;
			}
			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
		} 
		catch(LWJGLException e)
		{
			consoleError("Unable to setup mode "+ width + "x" + height + " fullscreen=" + fullscreen + e);
		}
	}

	public static Texture loadPNG(String path, String name, String type)
	{
		try
		{
			Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + path + "/" + type + "/" + name + ".png"));
			//			consoleMessage("Texture loaded: "+ texture);
			//			consoleMessage(">> Image width: "+ texture.getImageWidth());
			//			consoleMessage(">> Image height: "+ texture.getImageHeight());
			//			consoleMessage(">> Texture width: "+ texture.getTextureWidth());
			//			consoleMessage(">> Texture height: "+ texture.getTextureHeight());
			//			consoleMessage(">> Texture ID: "+ texture.getTextureID());
			return texture;
		} 
		catch(IOException e) {}
		return null;
	}

	public static boolean intToBoolean(int i)
	{
		if(i <= 0) return false;
		else return true;
	}
	
	public static int booleanToInt(boolean b)
	{
		if(b) return 1;
		else return 0;
	}

	/**
	 * Get a new instance of the main font.
	 * @param size The size of the font
	 * @return The font
	 */
	public static TrueTypeFont getFont(float size)
	{
		// load font from a .ttf file
		try
		{
			Font awtFont3 = Font.createFont(Font.TRUETYPE_FONT, gameInstance.getClass().getResourceAsStream("/Angies New House.ttf"));
			awtFont3 = awtFont3.deriveFont(size);
			GraphicsEnvironment vc = GraphicsEnvironment.getLocalGraphicsEnvironment();
			vc.registerFont(awtFont3);
			TrueTypeFont font = new TrueTypeFont(awtFont3, intToBoolean(Options.ANTI_ALIAS));
			return font;
		}
		catch(Exception e) {}	
		return null;
	}

	public static boolean intBetween(int a, int min, int max)
	{
		if(a > min && a < max) return true;
		else return false;
	}

	public static void captureScreenshot()
	{
		GL11.glReadBuffer(GL11.GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height= Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/").mkdir();	
		new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/" + Main.TITLE + "/").mkdir();	
		File file = new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/" + Main.TITLE + "/SCP-TE - " + getDate() + ".png"); // The file to save to.
		try
		{
			file.createNewFile();
		}
		catch(IOException e1) {e1.printStackTrace();}
		String format = "PNG"; // Example: "PNG" or "JPG"
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
			{
				int i = (x + (width * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		try
		{
			ImageIO.write(image, format, file);
		} 
		catch(IOException e) {e.printStackTrace();}
	}
	
	public static String getDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("y-MMM-d-HH.mm.s.S");
		return String.valueOf(sdf.format(cal.getTime()));
	}
	
	public void start3DClassD() //TODO: after Char selection
	{
		ray = new Raycast();
	}
	
	public void startClassD()
	{
		world = new World(true, 180, 180);
	}
}
