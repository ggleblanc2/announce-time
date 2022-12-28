package com.ggl.announcetime.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.ggl.announcetime.view.AnnounceTimeFrame;

public class ButtonListener implements ActionListener {
	
	private final AnnounceTimeFrame view;

	public ButtonListener(AnnounceTimeFrame view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		String text = button.getText();
		if (text.equals("Start")) {
			view.getTimer().restart();
			button.setText("Stop");
			view.getMessageLabel().setText("Process started, "
					+ "time will be announced");
		} else {
			view.getTimer().stop();
			button.setText("Start");
			view.getMessageLabel().setText("Process stopped");
		}
	}

}
