package screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import notes.*;

/**
 * Mathuran Sivakaran and Fayad Alman
 * January 17, 2022
 * A JPanel class that contains information about how the user performed
 */
public class EndPanel2 extends JPanel implements ActionListener, Runnable{

	/**
	 * Images and icons to be used
	 */
	ImageIcon grade;
	ImageIcon perfect; ImageIcon good; ImageIcon okay; ImageIcon miss;
	
	/**
	 * JLabels for displaying info
	 */
	JLabel gradeLabel;
	JLabel pLabel; JLabel gLabel; JLabel oLabel; JLabel mLabel; // Labels for perfect count, good count, etc.
	JLabel finalPointsLabel; // Label that shows the score
	
	GameButton back = new GameButton(20,20,80,20, new ImageIcon("resources\\images\\buttons\\backButton.png"));
	
	Thread runner = new Thread(this);
	boolean threadLoop = true;//determines whether thread should loop or not
	
	/**
	 * Constructor
	 */
	public EndPanel2(Level2 levelInfo) {//testing a Test2 parameter for constructor
		this.setPreferredSize(new Dimension(1100,600));
		this.setLayout(null);
		this.setCursor(CustomCursor.customCursor);
		this.setBackground(Color.BLACK);
		/**
		 * Initializing ImageIcons
		 */
		// Image for perfect
		perfect = new ImageIcon("resources\\images\\perfect.png");
		Image temp = perfect.getImage();
		temp = temp.getScaledInstance(250,250,java.awt.Image.SCALE_SMOOTH);
		perfect = new ImageIcon(temp);
		// Image for good
		good = new ImageIcon("resources\\images\\good.png");
		temp = good.getImage();
		temp = temp.getScaledInstance(250,250,java.awt.Image.SCALE_SMOOTH);
		good = new ImageIcon(temp);
		// Image for okay
		okay = new ImageIcon("resources\\images\\okay.png");
		temp = okay.getImage();
		temp = temp.getScaledInstance(250,250,java.awt.Image.SCALE_SMOOTH);
		okay = new ImageIcon(temp);
		// Image for miss
		miss = new ImageIcon("resources\\images\\miss.png");
		temp = miss.getImage();
		temp = temp.getScaledInstance(250,250,java.awt.Image.SCALE_SMOOTH);
		miss = new ImageIcon(temp);
		
		/*
		 * Caculating score and grade
		 */
		double finalGrade = (levelInfo.getPerfectCount() + levelInfo.getGoodCount()*0.3 + levelInfo.getOkayCount()*0.1)/40;//each count by a multiplier divided by total number of notes
		finalGrade = Math.round(finalGrade*10000.0)/100.0;//rounding to two decimal places *check*
		//determining letter grade
		if(finalGrade > 95 || (finalGrade > 90 && levelInfo.getMissCount() == 0)) {
			grade = new ImageIcon("resources\\images\\gradeRank\\grade_S.png");
		}
		else if(finalGrade > 90 || (finalGrade > 80 && levelInfo.getMissCount() == 0)) {
			//a
			grade = new ImageIcon("resources\\images\\gradeRank\\grade_A.png");
		}
		else if(finalGrade > 80 || (finalGrade > 70 && levelInfo.getMissCount() == 0)) {
			//b
			grade = new ImageIcon("resources\\images\\gradeRank\\grade_B.png");
		}
		else if(finalGrade > 70 || (finalGrade > 60 && levelInfo.getMissCount() == 0)) {
			//c
			grade = new ImageIcon("resources\\images\\gradeRank\\grade_C.png");
		}
		else {
			//d
			grade = new ImageIcon("resources\\images\\gradeRank\\grade_D.png");
		}
		temp = grade.getImage();
		temp = temp.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
		grade = new ImageIcon(temp);
		
		
		
		/**
		 * Setting label bounds and text
		 */
		// Pastes the grade and accuracy
		gradeLabel = new JLabel("YOUR GRADE             "+Double.toString(finalGrade)+"%");
		gradeLabel.setFont(new Font("Century Gothic", Font.BOLD, 35));
		gradeLabel.setForeground(Color.WHITE);
		gradeLabel.setBounds(200,50,500,100);
		this.add(gradeLabel);
		// Pastes the user's score
		finalPointsLabel = new JLabel("FINAL SCORE:                               "+Integer.toString(levelInfo.getPoints()));
		finalPointsLabel.setBounds(200, 400, 700, 100);
		finalPointsLabel.setForeground(Color.WHITE);
		finalPointsLabel.setFont(new Font("Century Gothic", Font.BOLD, 35));
		this.add(finalPointsLabel);
		// Adds the number of perfects
		Font normalFont = new Font("Verdana", Font.BOLD, 25);
		pLabel = new JLabel("x                 "+levelInfo.getPerfectCount());//the image icon will be in the place of the space
		pLabel.setFont(normalFont);
		pLabel.setForeground(Color.WHITE);
		pLabel.setBounds(670,195,300,50);
		this.add(pLabel);
		// Adds the number of goods
		gLabel = new JLabel("x                 "+levelInfo.getGoodCount());//the image icon will be in the place of the space
		gLabel.setFont(normalFont);
		gLabel.setForeground(Color.WHITE);
		gLabel.setBounds(670,245,300,50);
		this.add(gLabel);
		// Adds the number of okays
		oLabel = new JLabel("x                 "+levelInfo.getOkayCount());//the image icon will be in the place of the space
		oLabel.setFont(normalFont);
		oLabel.setForeground(Color.WHITE);
		oLabel.setBounds(670,295,300,50);
		this.add(oLabel);
		// Adds the number of misses
		mLabel = new JLabel("x                 "+levelInfo.getMissCount());//the image icon will be in the place of the space
		mLabel.setFont(normalFont);
		mLabel.setForeground(Color.WHITE);
		mLabel.setBounds(670,345,300,50);
		this.add(mLabel);
	
		back.addActionListener(this);
		this.add(back);
		this.setVisible(true);
		
		runner.start();
	}
	public static void main(String [] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
	}
	
	/**
	 * Accessor method for JButton back
	 * pre: N/A
	 * post: Returns the JButton for this object's JButton back
	 */
	public JButton getBack() {
		return back;
	}
	
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, 1350, 690);
		/**
		 * Drawing all the images
		 */
		g2.drawImage(grade.getImage(), 150, 150, null);
		g2.drawImage(perfect.getImage(), 600, 100, null);
		g2.drawImage(good.getImage(), 600, 150, null);
		g2.drawImage(okay.getImage(), 600, 200, null);
		g2.drawImage(miss.getImage(), 600, 250, null);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == back) {
			this.setVisible(false);
			threadLoop = false;
		}
		
	}
	@Override
	public void run() {
		while(threadLoop) {
			Point mousePoint = back.getMousePosition();//gets mouse point if it is in the component, else returns null
			back.adjustButton(mousePoint);//will change the button size and icon depending of if the mouse is within the button
			try {
				runner.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
