package com.ggl.announcetime;

import javax.swing.SwingUtilities;

import com.ggl.announcetime.view.AnnounceTimeFrame;

public class AnnounceTime implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new AnnounceTime());
	}

	@Override
	public void run() {
		new AnnounceTimeFrame();
	}

}
