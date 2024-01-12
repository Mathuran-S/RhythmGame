package notes;
/**
 * Mathuran Sivakaran and Fayad Alman
 * December 18, 2021
 * The JPanel class that runs the actual level
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;

import screens.EndPanel;

public class Level2 extends JPanel implements Runnable{

	Thread runner = new Thread(this);//in charge of repainting jpanel with new changes
	File song; //= new File("songs\\song1.wav");
	public Clip clip;
	public static long timer = 0;//counts time
	Note goingNote;//will be cast to appropriate note (Slider or Circle) when needed
	Note goingNote2;
	Note goingNote3;
	ArrayList <Note> list;//all notes added to ArrayList and then removed when their time comes
	ArrayList <Note> list2;//all notes added to ArrayList and then removed when their time comes
	ArrayList <Note> list3;//all notes added to ArrayList and then removed when their time comes
	
	/**
	 * Scoring related variables and objects
	 * Includes counter variables, JLabels, and icons
	 */
	//public static int points = 0;//keeps track of score
	private int points = 0;
	private int combo = 0;//counts combo
	JLabel scoreDisplay = new JLabel();
	JLabel comboDisplay = new JLabel();

	static ImageIcon icon = new ImageIcon();//for scoring icons
	static JLabel scoreIconLabel = new JLabel();//the JLabel that holds the icon
	static ImageIcon icon2 = new ImageIcon();
	static JLabel scoreIconLabel2 = new JLabel();
	static ImageIcon icon3 = new ImageIcon();
	static JLabel scoreIconLabel3 = new JLabel();
	
	Note prevNote;//prevNote is set to goingNote whenever pointState is set 
	Note prevNote2;
	Note prevNote3;
	
	/*
	 * Variables that count the number of perfect, good, or okay hits and misses
	 */
	private int perfectCount, goodCount, okayCount, missCount;
	
	/**
	 * Constructor
	 */
	public Level2(ArrayList<Note> list, ArrayList<Note> list2, ArrayList<Note> list3, File song) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.setPreferredSize(new Dimension(1350,690));
		this.setLayout(null);
		this.setCursor(screens.CustomCursor.customCursor);
		this.setVisible(true);
		this.song = song;
		clip = AudioSystem.getClip();
		AudioInputStream aStream = AudioSystem.getAudioInputStream(this.song);
		clip.open(aStream);
		
		this.list = (ArrayList<Note>) list.clone();//test to see if it works
		this.list2 = (ArrayList<Note>) list2.clone();//test to see if it works
		this.list3 = (ArrayList<Note>) list3.clone();//test to see if it works
		
		perfectCount = 0; goodCount = 0; okayCount = 0; missCount = 0;
		points = 0; combo = 0;
		timer = 0;
		runner.start();
	}
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		JFrame frame = new JFrame();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Accessor methods for required Test2 fields
	 */
	
	
	/** Accessor method for int perfectCount
	  * pre: N/A
	  * post: Returns an int value of this object's int perfectCount
	  */
	public int getPerfectCount() {
		return perfectCount;
	}
	
	/** Accessor method for int goodCount
	  * pre: N/A
	  * post: Returns an int value of this object's int goodCount
	  */
	public int getGoodCount() {
		return goodCount;
	}
	
	/** Accessor method for int okayCount
	  * pre: N/A
	  * post: Returns an int value of this object's int okayCount
	  */
	public int getOkayCount() {
		return okayCount;
	}
	
	/** Accessor method for int missCount
	  * pre: N/A
	  * post: Returns an int value of this object's int missCount
	  */
	public int getMissCount() {
		return missCount;
	}
	
	/** Accessor method for int points
	  * pre: N/A
	  * post: Returns an int value of this object's int points
	  */
	public int getPoints() {
		return points;
	}
	
	/** Accessor method for int combo
	  * pre: N/A
	  * post: Returns an int value of this object's int combo
	  */
	public int getCombo() {
		return combo;
	}
	
	
	
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );//makes rounded edges look smoother
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, 1350, 690);//blue background
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(3));
		if(goingNote != null) {//if a note needs to be shown on screen
			this.add(goingNote);
			goingNote.setFocusable(true);
			goingNote.requestFocus();
			Ellipse2D.Double appCircle = goingNote.getApproachCircle();
			appCircle.setFrameFromCenter(goingNote.getCenterX(), goingNote.getCenterY(), goingNote.getCenterX()-appCircle.width/2, goingNote.getCenterY()-appCircle.height/2);//setting frame for approach circle
			g2.draw(appCircle);
			if(goingNote instanceof SliderNote2) {
				SliderNote2 s = (SliderNote2)goingNote;
				g2.draw(s.getSliderShape().getTShape());//drawing the path line if the note is a slider note
			}
		}
		if(goingNote2 != null) {//if a note needs to be shown on screen
			this.add(goingNote2);
			goingNote2.setFocusable(true);
			goingNote2.requestFocus();
			Ellipse2D.Double appCircle = goingNote2.getApproachCircle();
			appCircle.setFrameFromCenter(goingNote2.getCenterX(), goingNote2.getCenterY(), goingNote2.getCenterX()-appCircle.width/2, goingNote2.getCenterY()-appCircle.height/2);//setting frame for approach circle
			g2.draw(appCircle);
			if(goingNote2 instanceof SliderNote2) {
				SliderNote2 s = (SliderNote2)goingNote2;
				g2.draw(s.getSliderShape().getTShape());//drawing the path line if the note is a slider note
			}
		}
		if(goingNote3 != null) {//if a note needs to be shown on screen
			this.add(goingNote3);
			goingNote3.setFocusable(true);
			goingNote3.requestFocus();
			Ellipse2D.Double appCircle = goingNote3.getApproachCircle();
			appCircle.setFrameFromCenter(goingNote3.getCenterX(), goingNote3.getCenterY(), goingNote3.getCenterX()-appCircle.width/2, goingNote3.getCenterY()-appCircle.height/2);//setting frame for approach circle
			g2.draw(appCircle);
			if(goingNote3 instanceof SliderNote2) {
				SliderNote2 s = (SliderNote2)goingNote3;
				g2.draw(s.getSliderShape().getTShape());//drawing the path line if the note is a slider note
			}
		}
	}
	
	@Override
	public void run() {
		//purpose of this thread is to repeatedly repaint the Jpanel
		scoreDisplay.setBounds(1200, 25, 200, 30);
		scoreDisplay.setFont(new Font("Century Gothic", Font.BOLD, 30));
		scoreDisplay.setForeground(Color.WHITE);
		
		comboDisplay.setBounds(50, 600, 200, 30);
		comboDisplay.setFont(new Font("Century Gothic", Font.BOLD, 30));
		comboDisplay.setForeground(Color.WHITE);
		int scoreIconTimer = 0;
		int scoreIconTimer2 = 0;
		int scoreIconTimer3 = 0;
		boolean showScoreIcon = false;
		boolean showScoreIcon2 = false;
		boolean showScoreIcon3 = false;
		while(true) {//loops continuously
			this.remove(scoreDisplay);//removes score
			scoreDisplay.setText(Integer.toString(points));//updates the text of the score
			this.add(scoreDisplay);//displays new score
			
			this.remove(comboDisplay);
			comboDisplay.setText("x"+Integer.toString(combo));
			this.add(comboDisplay);
			//String pointState = "";
			//will check if it's time for the first note in the list to go up and cast appropriately
			if (timer > 380 && timer < 420) {
				clip.start();
			}
			if ((goingNote == null && list.size() == 0) && (goingNote2 == null && list2.size() == 0) && (goingNote3 == null && list3.size() == 0)) {//if all notes are used, meaning game is complete
				clip.stop();
				this.setVisible(false);
				break;
			}
			if(goingNote == null && list.size() > 0) {	
				if(list.get(0).getStartTime() == timer) {
					goingNote = list.remove(0);//setting goingNote as the next Note
				}
			}
			if(goingNote != null){//if there is a note on screen
				if(goingNote.getEndTime() <= timer && !goingNote.getPressedState()) {//if user misses note
					prevNote = goingNote;
					//pointState = "lost";
					prevNote.setPointState("lost");
					if(goingNote instanceof SliderNote2) {
						
						goingNote.getApproachCircle().width = 0;//basically removing the approach circle of the slider note
						goingNote.getApproachCircle().height = 0;
						SliderNote2 s = (SliderNote2)goingNote;//casting to SliderNote
						s.setSlideState(true);
						s.moveSlider();
						if(!s.getSlideState()) {
							this.remove(goingNote);
							missCount++;//adding one to missCount because user missed a slidernote
							goingNote = null;
						}
					}
					else {
						this.remove(goingNote);//note is removed if its end time has come and hasn't been pressed yet
						prevNote.setPointState("lost");//stores that the previous note was lost
						missCount++;
						//if this statement is satisfied, that means the user lost that note, won't get points
						goingNote = null;//resetting goingNote to null
					}
				}
				else if(goingNote.getPressedState() && goingNote instanceof CircleNote2) {
					this.remove(goingNote);//removing if a circle note is pressed
					//pointState = goingNote.getPointState();
					prevNote = goingNote;
					goingNote = null;//resetting goingNote to null
				}
				else if(goingNote.getPressedState() && goingNote instanceof SliderNote2) {
					goingNote.getApproachCircle().width = 0;//basically removing the approach circle of the slider note
					goingNote.getApproachCircle().height = 0;
					SliderNote2 s = (SliderNote2)goingNote;//casting to SliderNote
					
					/**
					 * CHECK below
					 */
					if(!s.getSlideState()) {//if the slider is pressed and done moving
						this.remove(goingNote);
						//pointState = goingNote.getPointState();
						prevNote = goingNote;
						goingNote = null;
					}
					s.moveSlider();
					
				}
				else {//decreasing approachCircle of goingNote
					if(goingNote.getApproachCircle().width > goingNote.d) {
						goingNote.getApproachCircle().width-=4.5;
						goingNote.getApproachCircle().height-=4.5;
						//goingNote.getApproachCircle().width-=2.0;
						//goingNote.getApproachCircle().height-=2.0;
					}
				}
			}
			if(goingNote2 == null && list2.size() > 0) {	
				if(list2.get(0).getStartTime() == timer) {
					goingNote2 = list2.remove(0);//setting goingNote as the next Note
				}
			}
			if(goingNote2 != null){//if there is a note on screen
				if(goingNote2.getEndTime() <= timer && !goingNote2.getPressedState()) {//if user misses note
					prevNote2 = goingNote2;
					//pointState = "lost";
					prevNote2.setPointState("lost");
					if(goingNote2 instanceof SliderNote2) {
						
						goingNote2.getApproachCircle().width = 0;//basically removing the approach circle of the slider note
						goingNote2.getApproachCircle().height = 0;
						SliderNote2 s = (SliderNote2)goingNote2;//casting to SliderNote
						s.setSlideState(true);
						s.moveSlider();
						if(!s.getSlideState()) {
							this.remove(goingNote2);
							missCount++;//adding one to missCount because user missed a slidernote
							goingNote2 = null;
						}
					}
					else {
						this.remove(goingNote2);//note is removed if its end time has come and hasn't been pressed yet
						prevNote2.setPointState("lost");//stores that the previous note was lost
						missCount++;
						//if this statement is satisfied, that means the user lost that note, won't get points
						goingNote2 = null;//resetting goingNote to null
					}
				}
				else if(goingNote2.getPressedState() && goingNote2 instanceof CircleNote2) {
					this.remove(goingNote2);//removing if a circle note is pressed
					//pointState = goingNote.getPointState();
					prevNote2 = goingNote2;
					goingNote2 = null;//resetting goingNote to null
				}
				else if(goingNote2.getPressedState() && goingNote2 instanceof SliderNote2) {
					goingNote2.getApproachCircle().width = 0;//basically removing the approach circle of the slider note
					goingNote2.getApproachCircle().height = 0;
					SliderNote2 s = (SliderNote2)goingNote2;//casting to SliderNote
					
					/**
					 * CHECK below
					 */
					if(!s.getSlideState()) {//if the slider is pressed and done moving
						this.remove(goingNote2);
						//pointState = goingNote.getPointState();
						prevNote2 = goingNote2;
						goingNote2 = null;
					}
					s.moveSlider();
					
				}
				else {//decreasing approachCircle of goingNote
					if(goingNote2.getApproachCircle().width > goingNote2.d) {
						goingNote2.getApproachCircle().width-=4.5;
						goingNote2.getApproachCircle().height-=4.5;
						//goingNote.getApproachCircle().width-=2.0;
						//goingNote.getApproachCircle().height-=2.0;
					}
				}
			}
			if(goingNote3 == null && list3.size() > 0) {	
				if(list3.get(0).getStartTime() == timer) {
					goingNote3 = list3.remove(0);//setting goingNote as the next Note
				}
			}
			if(goingNote3 != null){//if there is a note on screen
				if(goingNote3.getEndTime() <= timer && !goingNote3.getPressedState()) {//if user misses note
					prevNote3 = goingNote3;
					//pointState = "lost";
					prevNote3.setPointState("lost");
					if(goingNote3 instanceof SliderNote2) {
						
						goingNote3.getApproachCircle().width = 0;//basically removing the approach circle of the slider note
						goingNote3.getApproachCircle().height = 0;
						SliderNote2 s = (SliderNote2)goingNote3;//casting to SliderNote
						s.setSlideState(true);
						s.moveSlider();
						if(!s.getSlideState()) {
							this.remove(goingNote3);
							missCount++;//adding one to missCount because user missed a slidernote
							goingNote3 = null;
						}
					}
					else {
						this.remove(goingNote3);//note is removed if its end time has come and hasn't been pressed yet
						prevNote3.setPointState("lost");//stores that the previous note was lost
						missCount++;
						//if this statement is satisfied, that means the user lost that note, won't get points
						goingNote3 = null;//resetting goingNote to null
					}
				}
				else if(goingNote3.getPressedState() && goingNote3 instanceof CircleNote2) {
					this.remove(goingNote3);//removing if a circle note is pressed
					//pointState = goingNote.getPointState();
					prevNote3 = goingNote3;
					goingNote3 = null;//resetting goingNote to null
				}
				else if(goingNote3.getPressedState() && goingNote3 instanceof SliderNote2) {
					goingNote3.getApproachCircle().width = 0;//basically removing the approach circle of the slider note
					goingNote3.getApproachCircle().height = 0;
					SliderNote2 s = (SliderNote2)goingNote3;//casting to SliderNote
					
					/**
					 * CHECK below
					 */
					if(!s.getSlideState()) {//if the slider is pressed and done moving
						this.remove(goingNote3);
						//pointState = goingNote.getPointState();
						prevNote3 = goingNote3;
						goingNote3 = null;
					}
					s.moveSlider();
					
				}
				else {//decreasing approachCircle of goingNote
					if(goingNote3.getApproachCircle().width > goingNote3.d) {
						goingNote3.getApproachCircle().width-=4.5;
						goingNote3.getApproachCircle().height-=4.5;
						//goingNote.getApproachCircle().width-=2.0;
						//goingNote.getApproachCircle().height-=2.0;
					}
				}
			}
			repaint();//call paintComponent();
			
			if(prevNote != null && !showScoreIcon) {//only executes if the scoreIcon is not showing yet and there was a previous note 
				if(prevNote.getPointState().equalsIgnoreCase("perfect")) {
					combo++;
					points+=300*combo;
					perfectCount++;
					if (prevNote instanceof SliderNote2) {
						combo++;
						points+=300*combo;
						perfectCount++;
					}
				}
				else if(prevNote.getPointState().equalsIgnoreCase("good")) {
					combo++;
					points += 100*combo;
					goodCount++;
					if (prevNote instanceof SliderNote2) {
						combo++;
						points+=300*combo;
						perfectCount++;
					}
				}
				else if(prevNote.getPointState().equalsIgnoreCase("okay")) {
					combo++;
					points += 50*combo;
					okayCount++;
				}
				else if(prevNote.getPointState().equalsIgnoreCase("lost")) {//missing a note
					combo = 0;
				}
				else if(prevNote.getPointState().equalsIgnoreCase("lostByRelease")) {//losing by releasing a slider too early
					combo = 0;
					points+=100;
					combo++;
					goodCount++;
				}
				if(!showScoreIcon && !prevNote.getPointState().isBlank()) {
					if(prevNote instanceof SliderNote2 && goingNote != null && prevNote.getPointState().equals("lost")) {//if previous note was a missed slider note and the slider note isn't done moving yet
						showScoreIcon = false;
					}
					else {
						showScoreIcon = true;
					}
				}
			}
			
			/**
			 * this code makes it more buggy i think
			 * like slidernote ends and rest dont show kind of bugggy
			 */
			if(showScoreIcon) {
				if(scoreIconTimer == 300) {//stop showing if it has been 300 seconds of of showing the label
					showScoreIcon = false;//makes sure the label isn't shown for next loop
					prevNote.setPointState("");
					this.remove(scoreIconLabel);//remove the label
					scoreIconTimer = 0;//reset the timer
				}
				else {
					displayPoints(prevNote);
					this.add(scoreIconLabel);
					scoreIconTimer+=50;
				}
			}
			
			if(prevNote2 != null && !showScoreIcon2) {//only executes if the scoreIcon is not showing yet and there was a previous note 
				if(prevNote2.getPointState().equalsIgnoreCase("perfect")) {
					combo++;
					points+=300*combo;
					perfectCount++;
					if (prevNote2 instanceof SliderNote2) {
						combo++;
						points+=300*combo;
						perfectCount++;
					}
				}
				else if(prevNote2.getPointState().equalsIgnoreCase("good")) {
					combo++;
					points += 100*combo;
					goodCount++;
					if (prevNote2 instanceof SliderNote2) {
						combo++;
						points+=300*combo;
						perfectCount++;
					}
				}
				else if(prevNote2.getPointState().equalsIgnoreCase("okay")) {
					combo++;
					points += 50*combo;
					okayCount++;
				}
				else if(prevNote2.getPointState().equalsIgnoreCase("lost")) {//missing a note
					combo = 0;
				}
				else if(prevNote2.getPointState().equalsIgnoreCase("lostByRelease")) {//losing by releasing a slider too early
					combo = 0;
					points+=100;
					combo++;
					goodCount++;
				}
				if(!showScoreIcon2 && !prevNote2.getPointState().isBlank()) {
					if(prevNote2 instanceof SliderNote2 && goingNote2 != null && prevNote2.getPointState().equals("lost")) {//if previous note was a missed slider note and the slider note isn't done moving yet
						showScoreIcon2 = false;
					}
					else {
						showScoreIcon2 = true;
					}
				}
			}

			if(showScoreIcon2) {
				if(scoreIconTimer2 == 300) {//stop showing if it has been 300 seconds of of showing the label
					showScoreIcon2 = false;//makes sure the label isn't shown for next loop
					prevNote2.setPointState("");
					this.remove(scoreIconLabel2);//remove the label
					scoreIconTimer2 = 0;//reset the timer
				}
				else {
					displayPoints2(prevNote2);
					this.add(scoreIconLabel2);
					scoreIconTimer2+=50;
				}
			}
			
			if(prevNote3 != null && !showScoreIcon3) {//only executes if the scoreIcon is not showing yet and there was a previous note 
				if(prevNote3.getPointState().equalsIgnoreCase("perfect")) {
					combo++;
					points+=300*combo;
					perfectCount++;
					if (prevNote3 instanceof SliderNote2) {
						combo++;
						points+=300*combo;
						perfectCount++;
					}
				}
				else if(prevNote3.getPointState().equalsIgnoreCase("good")) {
					combo++;
					points += 100*combo;
					goodCount++;
					if (prevNote3 instanceof SliderNote2) {
						combo++;
						points+=300*combo;
						perfectCount++;
					}
				}
				else if(prevNote3.getPointState().equalsIgnoreCase("okay")) {
					combo++;
					points += 50*combo;
					okayCount++;
				}
				else if(prevNote3.getPointState().equalsIgnoreCase("lost")) {//missing a note
					combo = 0;
				}
				else if(prevNote3.getPointState().equalsIgnoreCase("lostByRelease")) {//losing by releasing a slider too early
					combo = 0;
					points+=100;
					combo++;
					goodCount++;
				}
				if(!showScoreIcon3 && !prevNote3.getPointState().isBlank()) {
					if(prevNote3 instanceof SliderNote2 && goingNote3 != null && prevNote3.getPointState().equals("lost")) {//if previous note was a missed slider note and the slider note isn't done moving yet
						showScoreIcon3 = false;
					}
					else {
						showScoreIcon3 = true;
					}
				}
			}
			
			/**
			 * this code makes it more buggy i think
			 * like slidernote ends and rest dont show kind of bugggy
			 */
			if(showScoreIcon3) {
				if(scoreIconTimer3 == 300) {//stop showing if it has been 300 seconds of of showing the label
					showScoreIcon3 = false;//makes sure the label isn't shown for next loop
					prevNote3.setPointState("");
					this.remove(scoreIconLabel3);//remove the label
					scoreIconTimer3 = 0;//reset the timer
				}
				else {
					displayPoints3(prevNote3);
					this.add(scoreIconLabel3);
					scoreIconTimer3+=50;
				}
			}
			
			try {
				runner.sleep(50);
				timer+=50;//count milliseconds
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * Displaying an image of how many points user got for hitting a note
	 * pre: One Note parameter: Note n
	 * post: N/A (void)
	 */
	public static void displayPoints(Note n) {
		if(n.getPointState().equals("perfect")) {
			icon = new ImageIcon("resources\\images\\perfect.png");
		}
		else if(n.getPointState().equals("good") || n.getPointState().equals("lostByRelease")) {
			icon = new ImageIcon("resources\\images\\good.png");
		}
		else if(n.getPointState().equals("okay")) {
			icon = new ImageIcon("resources\\images\\okay.png");
		}
		else if(n.getPointState().equals("lost")) {
			icon = new ImageIcon("resources\\images\\miss.png");
		}
		if(icon != null) {//test
		Image temp = icon.getImage();
		temp = temp.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(temp);
		scoreIconLabel.setIcon(icon);
		scoreIconLabel.setBounds(n.getX()-80, n.getY()-40, 200, 200);
		}
	}
	/**
	 * Displaying an image of how many points user got for hitting a note
	 * pre: One Note parameter: Note n
	 * post: N/A (void)
	 */
	public static void displayPoints2(Note n) {
		if(n.getPointState().equals("perfect")) {
			icon2 = new ImageIcon("resources\\images\\perfect.png");
		}
		else if(n.getPointState().equals("good") || n.getPointState().equals("lostByRelease")) {
			icon2 = new ImageIcon("resources\\images\\good.png");
		}
		else if(n.getPointState().equals("okay")) {
			icon2 = new ImageIcon("resources\\images\\okay.png");
		}
		else if(n.getPointState().equals("lost")) {
			icon2 = new ImageIcon("resources\\images\\miss.png");
		}
		if(icon2 != null) {//test
		Image temp2 = icon2.getImage();
		temp2 = temp2.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		icon2 = new ImageIcon(temp2);
		scoreIconLabel2.setIcon(icon2);
		scoreIconLabel2.setBounds(n.getX()-80, n.getY()-40, 200, 200);
		}
	}
	/**
	 * Displaying an image of how many points user got for hitting a note
	 * pre: One Note parameter: Note n
	 * post: N/A (void)
	 */
	public static void displayPoints3(Note n) {
		if(n.getPointState().equals("perfect")) {
			icon3 = new ImageIcon("resources\\images\\perfect.png");
		}
		else if(n.getPointState().equals("good") || n.getPointState().equals("lostByRelease")) {
			icon3 = new ImageIcon("resources\\images\\good.png");
		}
		else if(n.getPointState().equals("okay")) {
			icon3 = new ImageIcon("resources\\images\\okay.png");
		}
		else if(n.getPointState().equals("lost")) {
			icon3 = new ImageIcon("resources\\images\\miss.png");
		}
		if(icon3 != null) {//test
		Image temp3 = icon3.getImage();
		temp3 = temp3.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		icon3 = new ImageIcon(temp3);
		scoreIconLabel3.setIcon(icon3);
		scoreIconLabel3.setBounds(n.getX()-80, n.getY()-40, 200, 200);
		}
	}
}