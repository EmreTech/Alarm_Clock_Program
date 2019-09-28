package com.emretech.alarm;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Music {
	public Clip clip;
	private AudioInputStream audio;
	
	String currentDirectory = System.getProperty("user.dir");
	String filename;
	public long currentFrame;
	
	boolean playing = false;
	boolean looping = false;
	
	public Music(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.filename = filename;
		audio = AudioSystem.getAudioInputStream(new File(currentDirectory + "/res/" + filename).getAbsoluteFile());
		clip = AudioSystem.getClip();
		
		clip.open(audio);
	}
	
	public void play() {
		clip.start();
		playing = true;
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		looping = true;
	}
	
	public void unloop() {
		clip.loop(0);
		looping = false;
	}
	public void pause() {
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		playing = false;
	}
	
	public void resume() {
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}
	
	public void stop() {
		currentFrame = 0L;
		clip.stop();
		clip.close();
		playing = false;
	}
	
	public void setVolume(float volume) {
	    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
	    gainControl.setValue(20f * (float) Math.log10(volume));
	}
	
	public Float getVolume() {
		Float volume = ((FloatControl) clip.getControl(FloatControl.Type.VOLUME)).getValue();
		return volume;
	}
	public boolean isPlaying() {
		return playing;
	}
	private void resetAudioStream() {
		try {
			audio = AudioSystem.getAudioInputStream(new File(currentDirectory + "/res/" + filename).getAbsoluteFile());
			clip.open(audio);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			JOptionPane.showMessageDialog(null, "Error: Something didn't work correctly");
		}
		
	}
}
