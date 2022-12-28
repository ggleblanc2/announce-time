package com.ggl.announcetime.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SpeechListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent event) {
		LocalTime localTime = LocalTime.now();
		int minute = localTime.getMinute();
		if (minute == 0) {
			int hour = localTime.getHour();		// 0 to 23
			playWavFile(hour);
		}
	}
	
	private void playWavFile(int hour) {
		String fileName = "/announce" + hour + ".wav";
		InputStream inputStream = getClass().getResourceAsStream(fileName);
		AudioInputStream audioStream = null;
		AudioFormat audioFormat = null;
		
		try {
			audioStream = AudioSystem.getAudioInputStream(inputStream);
			audioFormat = audioStream.getFormat();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		SourceDataLine sourceLine;
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		}
        
        sourceLine.start();

		int nBytesRead = 0;
		byte[] abData = new byte[128000];
		while (nBytesRead != -1) {
			try {
				nBytesRead = audioStream.read(abData, 0, abData.length);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			
			if (nBytesRead >= 0) {
				sourceLine.write(abData, 0, nBytesRead);
			}
		}

		sourceLine.drain();
		sourceLine.close();
	}

}
