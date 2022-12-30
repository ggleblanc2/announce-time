package com.ggl.announcetime.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
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

import com.ggl.announcetime.view.AnnounceTimeFrame;
import com.ggl.error.display.view.ErrorDisplayDialog;

public class SpeechListener implements ActionListener {

	private int previousHour;

	private final AnnounceTimeFrame view;

	public SpeechListener(AnnounceTimeFrame view) {
		this.view = view;
		this.previousHour = -1;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		LocalTime localTime = LocalTime.now();
//		System.out.println(localTime);
		int minute = localTime.getMinute();
		int hour = localTime.getHour(); // 0 to 23
		if ((minute == 0) && (previousHour != hour)) {
			playWavFile(hour);
			previousHour = hour;
		}
	}

	private void playWavFile(int hour) {
		String fileName = "/announce" + hour + ".wav";
		InputStream inputStream = getClass().getResourceAsStream(fileName);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				inputStream);
		AudioInputStream audioStream = null;
		AudioFormat audioFormat = null;

		try {
			audioStream = AudioSystem.getAudioInputStream(bufferedInputStream);
			audioFormat = audioStream.getFormat();
		} catch (UnsupportedAudioFileException e) {
			new ErrorDisplayDialog(view.getFrame(),
					"UnsupportedAudioFileException", e);
			return;
		} catch (IOException e) {
			new ErrorDisplayDialog(view.getFrame(), "IOException", e);
			return;
		}

		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		SourceDataLine sourceLine;
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			new ErrorDisplayDialog(view.getFrame(), "LineUnavailableException",
					e);
			return;
		}

		sourceLine.start();

		int nBytesRead = 0;
		byte[] abData = new byte[128000];
		while (nBytesRead != -1) {
			try {
				nBytesRead = audioStream.read(abData, 0, abData.length);
			} catch (IOException e) {
				new ErrorDisplayDialog(view.getFrame(), "IOException", e);
				return;
			}

			if (nBytesRead >= 0) {
				sourceLine.write(abData, 0, nBytesRead);
			}
		}

		sourceLine.drain();
		sourceLine.close();

		try {
			audioStream.close();
		} catch (IOException e) {
			new ErrorDisplayDialog(view.getFrame(), "IOException", e);
		}
	}

}
