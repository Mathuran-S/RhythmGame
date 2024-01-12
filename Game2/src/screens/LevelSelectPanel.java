package screens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class LevelSelectPanel extends JPanel implements ActionListener, Runnable{
	//JPanel levelSelectPanel = new JPanel(); // Panel for the instruction screen
	
	JLabel title = new JLabel("Levels");
	//GameButton level1 = new GameButton(550,200,160,40, new ImageIcon("images\\buttons\\level1Button.png")); // Button that transports user to the first level
	GameButton level1 = new GameButton(180,200,160,40, new ImageIcon("resources\\images\\buttons\\level1Button.png")); // Button that transports user to the first level
	GameButton level2 = new GameButton(180,350,160,40, new ImageIcon("resources\\images\\buttons\\level2Button.png"));//Button that transports user to the second level
	//JButton back = new JButton("Back"); // Button that transports user to the MainFrame
	GameButton back = new GameButton(20,20,80,20, new ImageIcon("resources\\images\\buttons\\backButton.png"));
	static MainFrame gui1;
	static Level_Frame gui2;
	
	/**
	 * Thread related variables
	 */
	Thread runner = new Thread(this);
	boolean threadLoop = true;//determines whether thread should loop or not
	
	/**
	 * Purpose of this method: Constructor for the panel
	 * Pre: N/A
	 * Post: N/A
	 */
	public LevelSelectPanel() {
		Actions();
	}
	/**
	 * Purpose of this method: Creates the panel and the buttons
	 * Pre: N/A
	 * Post: N/A
	 */
	public void Actions () {
		setPreferredSize(new Dimension(500, 500)); // Sets the size of the panel 
		//since this is a big screen with not much, maybe change size??? (jan 16)
		this.setLayout(null);
		this.setCursor(CustomCursor.customCursor);
		this.setBackground(Color.PINK);
		// Creates the interactive buttons
		back.addActionListener(this);
		level1.addActionListener(this);
		level2.addActionListener(this);
		// Creates the labels and adds the buttons
		//title.setBounds(580, 30, 200, 40);
		title.setBounds(200,30,200,40);
		title.setFont(new Font("Century Gothic", Font.BOLD, 35));
		title.setForeground(Color.BLACK);
		this.add(title);
		/*
		 * icon for level1 button
		 */
		/*ImageIcon circle = new ImageIcon("images\\circleNote.png");
		Image temp = circle.getImage();
		temp = temp.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
		circle = new ImageIcon(temp);
		level1.setIcon(circle);
		temp = temp.getScaledInstance(160, 160, java.awt.Image.SCALE_SMOOTH);
		circle = new ImageIcon(temp);
		level1.setRolloverIcon(circle);//larger icon for if the mouse is over the button
		level1.setContentAreaFilled(false);
		level1.setFocusPainted(false);
		level1.setBorderPainted(false);
		level1.setHorizontalTextPosition(JButton.CENTER);//making sure text in in center of button
		level1.setBounds(400, 400, 150, 150);*/
		this.add(level1);
		this.add(level2);
		//back.setBounds(20, 20, 70, 30);
		this.add(back);
		//add(levelSelectPanel);
		setVisible(true);
		runner.start();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	/**
	 * Accessor method for JButton level1
	 * pre: N/A
	 * post: Returns a JButton of this object's JButton level1
	 */
	public JButton getLevel1() {
		return level1;
	}
	
	/**
	 * Accessor method for JButton level2
	 * pre: N/A
	 * post: Returns a JButton of this object's JButton level2
	 */
	public JButton getLevel2() {
		return level2;
	}
	
	/**
	 * Accessor method for JButton back
	 * pre: N/A
	 * post: Returns the JButton for this object's JButton back
	 */
	public JButton getBack() {
		return back;
	}
	
	/**
	 * Purpose of this method: The responses when the user clicks one of the buttons; it will transport the user to a different screen
	 * Pre: N/A
	 * Post: N/A
	 */
	public void actionPerformed(ActionEvent event){
		if(event.getSource() == back) {
			setVisible(false);
			threadLoop = false;//stops thread from looping
			gui1 = new MainFrame(); // Sends user to the Title Screen
			gui1.setFocusable(true);
		    //gui1.setBounds(0, 0, 1350, 690);
		    gui1.requestFocus();
		    gui1.setVisible(true); // Makes the Title screen visible
		}
		if(event.getSource() == level1) {
			System.out.println("LEVEL ONE");
			setVisible(false);
			threadLoop = false;
			try {
				gui2 = new Level_Frame(1);//the 1 tells Level1_Panel to get the first level
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Sends user to the first level
			
			gui2.setFocusable(true);
		   // gui2.setBounds(0, 0, 1350, 690);
		    gui2.requestFocus();
		    gui2.setVisible(true); // Makes the first level visible
		}
		if(event.getSource() == level2) {
			System.out.println("LEVEL ONE");
			setVisible(false);
			threadLoop = false;
			try {
				gui2 = new Level_Frame(2);//the 2 tells Level_Panel to get the first level
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Sends user to the first level
			
			gui2.setFocusable(true);
		   // gui2.setBounds(0, 0, 1350, 690);
		    gui2.requestFocus();
		    gui2.setVisible(true); // Makes the first level visible
		}
	}
	@Override
	public void run() {
		while(threadLoop) {
			Point mousePoint = level1.getMousePosition();
			level1.adjustButton(mousePoint);
			mousePoint = level2.getMousePosition();
			level2.adjustButton(mousePoint);
			mousePoint = back.getMousePosition();
			back.adjustButton(mousePoint);
			try {
				runner.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}