package screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 * Mathuran Sivakaran and Fayad Alman
 * January 12, 2022
 * JFrame that holds the LevelSelectPanel
 */
public class LevelSelectFrame extends JFrame implements ActionListener{
	LevelSelectPanel selectPanel = new LevelSelectPanel();
	
	/*
	 * Buttons whose actions will be received
	 */
	JButton back = selectPanel.getBack();
	JButton level1 = selectPanel.getLevel1();
	JButton level2 = selectPanel.getLevel2();
	
	public LevelSelectFrame() {
		super("SELECT A LEVEL");
		back.addActionListener(this);
		level1.addActionListener(this);
		this.add(selectPanel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == back) {
			setVisible(false);
		}
		if(event.getSource() == level1 || event.getSource() == level2) {
			setVisible(false);
		}
	}
}
