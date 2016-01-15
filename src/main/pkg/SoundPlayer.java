package main.pkg;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
	
	public static final String LANDSHOOT = "sound/landshoot.wav";
	public static final String WATERSHOOT = "sound/watershoot.wav";
	public static final String TARGETSHOOT = "sound/targetshoot.wav";
	public static final String WIN = "sound/win.wav";
	public static final String LOSE = "sound/lose.wav";
	
	private static SoundPlayer instance;
	
	private SoundPlayer(){
		
	}
	
	public void play( String name ){
		
		Thread t = new Thread(new Runnable() {
				
			@Override
			public void run() {
					
				try{
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(new File(name)));
					clip.start();
					Thread.sleep(clip.getMicrosecondLength()/1000);
					
				}
				catch(Exception e){
					
				}
					
			}
		});
		
		t.start();
		
	}
	
	public static SoundPlayer getInstance(){
		if( instance == null ) instance = new SoundPlayer();
		return instance;
	}
	
}
