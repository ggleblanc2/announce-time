package com.ggl.announcetime.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.ggl.announcetime.controller.ButtonListener;
import com.ggl.announcetime.controller.SpeechListener;

public class AnnounceTimeFrame {
	
	private final JFrame frame;
	
	private JButton startButton;
	
	private JLabel messageLabel;
	
	private final Timer timer;
	
	public AnnounceTimeFrame() {
		this.frame = createAndShowGUI();
		this.timer = new Timer(60_000, new SpeechListener());
	}
	
	public JFrame createAndShowGUI() {
		JFrame frame = new JFrame("Announce Time");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				timer.stop();
				frame.dispose();
				System.exit(0);
			}
		});
		
		frame.add(createControlPanel(), BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		
		return frame;
	}
	
	private JPanel createControlPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
		
		messageLabel = new JLabel("Left-click the \"Start\" button "
				+ "to start the timer");
		panel.add(messageLabel, BorderLayout.NORTH);
		
		startButton = new JButton("Start");
		startButton.addActionListener(new ButtonListener(this));
		panel.add(startButton, BorderLayout.SOUTH);
		
		return panel;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JLabel getMessageLabel() {
		return messageLabel;
	}

	public Timer getTimer() {
		return timer;
	}

}
