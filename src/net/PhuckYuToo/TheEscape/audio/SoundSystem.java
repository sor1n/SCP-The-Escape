package net.PhuckYuToo.TheEscape.audio;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class SoundSystem
{
	public SoundSystem()
	{
		Sound.values();
		Music.values();
	}
	
	public void playSound(Sound sound, float pitch, float gain, boolean loop)
	{
		sound.playSound(pitch, gain, loop);
	}
	
	public enum Sound
	{
		SCP096_CRY("096_cry"), SCP096_SCREAM("096_scream"), SCP127("127"),
		ABLE1("able1"), ABLE2("able2"), ABLE_DOOR("abledoor"), ALARM("alarm"),
		BEHIND_YOU("behindyou"), BRASS1("brass1"), BRASS2("brass2"), BRASS3("brass3"),
		CLICK_SETTING("click_setting1"), CLICK("click"), COWBELL("cowbell"), DEATH_MTF("death_mtf"),
		FIRE_CRACK("fireCrackling"), FLAME("flame"), FRESHMEAT("FreshMeat"), HURT("hurt"),
		LOOKUP("lookup"), OLDMAN_DRAG("oldmandrag"), RELOAD("reload_gun"), SHOT("shot"), WORTHOFLIFE("worthoflife");
		
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
				ogg = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("sounds/" + path + ".ogg"));
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
		public static float getSoundVolume()
		{
			return SoundStore.get().getSoundVolume();
		}
	}
	
	public enum Music
	{
		MAIN("TheEscape"), CLASSDTHEME("StruggleAgainstUnknown"),
		S058THEME("SCP-058"), ABLETHEME("SCP-076-2"),
		S073THEME("SCP-073"), S999THEME("SCP-999"),
		S1000THEME("SCP-1000"), S990THEME("SCP-990"),
		S079THEME("SCP-079"), S106THEME("SCP-106"),
		S629THEME("SCP-629"), S420JTHEME("SCP-420-J"),
		S096THEME("SCP-096"), S080THEME("SCP-080"),
		S966THEME("SCP-966");
		
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
				ogg = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("sounds/music/" + path + ".ogg"));
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
			SoundStore.get().setCurrentMusicVolume(vol);
		}
		public static float getMusicVolume()
		{
			return SoundStore.get().getCurrentMusicVolume();
		}
	}

	/**
	 * Game loop update
	 */
	public void tick(int delta)
	{
		// polling is required to allow streaming to get a chance to
		// queue buffers.
		SoundStore.get().poll(0);
	}
}
