package screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import notes.CircleNote2;

public class TitlePanel extends JPanel implements ActionListener, Runnable{
	/**
	 * Other screens for gui navigation
	 */
	static LevelSelectFrame gui1;
	static InstructionFrame gui2;
	
	/**
	 * Buttons for gui navigation
	 */
	//JButton play = new JButton("Play"); // Button that transports user to the LevelSelectPanel
	GameButton play = new GameButton(375,400,160,40,new ImageIcon("resources\\images\\buttons\\playButton.png"));
	
	//JButton instructions = new JButton("Instructions"); // Button that transports user to the InstructionsPanel
	GameButton instructions = new GameButton(775,400,160,40,new ImageIcon("resources\\images\\buttons\\instructionsButton.png"));
	
	JLabel title = new JLabel("Welcome to C-Beats!"); // Title of the game
	
	
	/**
	 * Thread related variables
	 */
	Thread runner = new Thread(this);
	boolean threadLoop = true;//determines whether thread should loop or not
	/**
	 * Constructor
	 */
	public TitlePanel() {
		// Sets the panel size and appearance
		this.setLayout(null);
		this.setCursor(CustomCursor.customCursor);
		this.setPreferredSize(new Dimension(1350, 690)); // Sets the size of the panel
		play.addActionListener(this);
		instructions.addActionListener(this);
		// Creates the interactive buttons
		/*ImageIcon circle = new ImageIcon("images\\circleNote.png");
		Image temp = circle.getImage();
		temp = temp.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
		circle = new ImageIcon(temp);
		play.addActionListener(this);
		instructions.addActionListener(this);
		play.setIcon(circle);
		instructions.setIcon(circle);
		temp = temp.getScaledInstance(160, 160, java.awt.Image.SCALE_SMOOTH);
		circle = new ImageIcon(temp);
		play.setRolloverIcon(circle);//larger icon for if the mouse is over the button
		instructions.setRolloverIcon(circle);//larger icon for if the mouse is over the button
		play.setContentAreaFilled(false);
		play.setFocusPainted(false);
		play.setBorderPainted(false);
		play.setHorizontalTextPosition(JButton.CENTER);//making sure text in in center of button
		play.setVerticalTextPosition(JButton.CENTER);//making sure text in in center of button
		instructions.setContentAreaFilled(false);
		instructions.setFocusPainted(false);
		instructions.setBorderPainted(false);
		instructions.setHorizontalTextPosition(JButton.CENTER);//making sure text in in center of button
		instructions.setVerticalTextPosition(JButton.CENTER);//making sure text in in center of button
		*/
		
		
		// Creates the labels and adds the buttons
		title.setBounds(420, 50, 700, 200);
		title.setFont(new Font("Century Gothic", Font.BOLD, 50));
		title.setForeground(Color.BLACK);
		//play.setBounds(375, 400, 150, 150);
		//instructions.setBounds(775, 400, 150,150);
		this.add(title);
		this.add(play);
		this.add(instructions);
		this.setBackground(Color.PINK);
		threadLoop = true;
		runner.start();
	}

	/**
	 * Accessor method for JButton play
	 * pre: N/A
	 * post: Returns a JButton of this object's JButton play
	 */
	public JButton getPlay() {
		return play;
	}
	
	/**
	 * Accessor method for JButton instructions
	 * pre: N/A
	 * post: Returns a JButton of this object's JButton instructions
	 */
	public JButton getInstructions() {
		return instructions;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == play) {
			setVisible(false);
			threadLoop = false;//stopping thread looping
			gui1 = new LevelSelectFrame(); // Sends user to the Level Select screen
			gui1.setFocusable(true);
		   // gui1.setBounds(0, 0, 1350, 690);
		    gui1.requestFocus();
		    gui1.setVisible(true); // Makes the Instructions screen visible
		}
		if(event.getSource() == instructions) {
			setVisible(false);
			threadLoop = false;//stopping thread looping
			gui2 = new InstructionFrame(); // Sends user to the Instructions screen
			gui2.setFocusable(true);
		  //  gui2.setBounds(0, 0, 1350, 690);
		    gui2.requestFocus();
		    gui2.setVisible(true); // Makes the Instructions screen visible
		}
	}

	@Override
	public void run() {
		
		while(threadLoop) {
			Point mousePoint = play.getMousePosition();
			play.adjustButton(mousePoint);
			/*if(mousePoint != null) {
				//changing bounds of play button so new icon doesn't move downward
				play.setBounds(327,347,210,210);//moving left and up from original position, and new size
				play.setHorizontalTextPosition(JButton.CENTER);//making sure text in in center of button
				play.setVerticalTextPosition(JButton.CENTER);//making sure text in in center of button
			}
			else {
				play.setBounds(375, 400, 150, 150);//resetting to initial coordinates
			}*/
			
			mousePoint = instructions.getMousePosition();
			instructions.adjustButton(mousePoint);
			/*if(mousePoint != null) {
				//changing bounds of play button so new icon doesn't move downward
				instructions.setBounds(727,347,210,210);//moving left and up from original position, and new size
				instructions.setHorizontalTextPosition(JButton.CENTER);//making sure text in in center of button
				instructions.setVerticalTextPosition(JButton.CENTER);//making sure text in in center of button
			}
			else {
				instructions.setBounds(775, 400, 150, 150);//resetting to initial coordinates
			}*/
			
			try {
				runner.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
