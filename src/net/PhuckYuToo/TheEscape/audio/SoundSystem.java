package net.PhuckYuToo.TheEscape.audio;

import net.PhuckYuToo.TheEscape.Main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class SoundSystem
{
	public enum Sound
	{
		SCP096CRY("096_cry.ogg");
		
		Sound(String path)
		{
			try 
			{
				// you can play oggs by loading the complete thing into 
				// a sound
				//oggEffect = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sounds/brass1.ogg"));

				this.path = path;
				// or setting up a stream to read from. Note that the argument becomes
				// a URL here so it can be reopened when the stream is complete. Probably
				// should have reset the stream by thats not how the original stuff worked
				ogg = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("sounds/" + path));
			} 
			catch(Exception e) {}
		}
		/** The ogg sound effect */
		//private Audio oggEffect;
		/** The ogg stream thats been loaded */
		private Audio ogg;
		private String path;
		
		public Audio getAudio()
		{
			return ogg;
		}
		public String getPath()
		{
			return path;
		}
		public void playSound(float pitch, float gain, boolean loop)
		{
			ogg.playAsSoundEffect(pitch, gain, loop); //pitch, gain, loop
		}
		public void stopSound()
		{
			ogg.stop();
		}
		public boolean isPlaying()
		{
			return ogg.isPlaying();
		}
		public static void setSoundVolume(float vol)
		{
			SoundStore.get().setSoundVolume(vol);
		}
	}
	
	public enum Music
	{
		MAIN("TheEscape.ogg");
		
		Music(String path)
		{
			try 
			{
				// you can play oggs by loading the complete thing into 
				// a sound
				//oggEffect = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sounds/brass1.ogg"));

				this.path = path;
				// or setting up a stream to read from. Note that the argument becomes
				// a URL here so it can be reopened when the stream is complete. Probably
				// should have reset the stream by thats not how the original stuff worked
				ogg = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("sounds/music/" + path));
			} 
			catch(Exception e) {}
		}
		/** The ogg sound effect */
		//private Audio oggEffect;
		/** The ogg stream thats been loaded */
		private Audio ogg;
		private String path;
		
		public Audio getAudio()
		{
			return ogg;
		}
		public String getPath()
		{
			return path;
		}
		public void playMusic(float pitch, float gain, boolean loop)
		{
			ogg.playAsMusic(pitch, gain, loop); //pitch, gain, loop
		}
		public void stopMusic()
		{
			ogg.stop();
		}
		public boolean isPlaying()
		{
			return ogg.isPlaying();
		}
		public static void setMusicVolume(float vol)
		{
			SoundStore.get().setMusicVolume(vol);
		}
		public static float getMusicVolume()
		{
			return SoundStore.get().getMusicVolume();
		}
	}

	/**
	 * Game loop update
	 */
	public void tick(int delta)
	{
		if(!Music.MAIN.isPlaying()) Music.MAIN.playMusic(1f, 1f, false);
		Main.consoleMessage(Music.getMusicVolume());
		while(Keyboard.next())
		{
			if(Keyboard.getEventKeyState())
			{
				if(Keyboard.getEventKey() == Keyboard.KEY_Q)
				{
					Main.consoleError("Q");
					Music.setMusicVolume(2.0f);
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_W)
				{
					Main.consoleError("W");
					Music.setMusicVolume(1.0f);
				}
			}
		}

		// polling is required to allow streaming to get a chance to
		// queue buffers.
		SoundStore.get().poll(0);
	}
}
