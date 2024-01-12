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

public class Level1 extends JPanel implements Runnable{

	Thread runner = new Thread(this);//in charge of repainting jpanel with new changes
	File song; //= new File("songs\\song1.wav");
	public Clip clip;
	public static long timer = 0;//counts time
	Note goingNote;//will be cast to appropriate note (Slider or Circle) when needed
	ArrayList <Note> list;//all notes added to ArrayList and then removed when their time comes
	
	
	/**
	 * Scoring related variables and objects
	 * Includes counter variables, JLabels, and icons
	 */
	//public static int points = 0;//keeps track of score
	private int points = 0;
	private int combo = 0;//counts combo
	JLabel scoreDisplay = new JLabel();
	JLabel comboDisplay = new JLabel();

	static ImageIcon icon = new ImageIcon();//for scoring icons, figure out later
	static JLabel scoreIconLabel = new JLabel();//the JLabel that holds the icon
	Note prevNote;//prevNote is set to goingNote whenever pointState is set 
	
	/*
	 * Variables that count the number of perfect, good, or okay hits and misses
	 */
	private int perfectCount, goodCount, okayCount, missCount;
	
	/**
	 * Constructor
	 */
	public Level1(ArrayList<Note> list, File song) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.setPreferredSize(new Dimension(1350,690));
		this.setLayout(null);
		this.setCursor(screens.CustomCursor.customCursor);
		this.setVisible(true);
		this.song = song;
		clip = AudioSystem.getClip();
		AudioInputStream aStream = AudioSystem.getAudioInputStream(this.song);
		clip.open(aStream);
		
		this.list = (ArrayList<Note>) list.clone();//test to see if it works
		
		perfectCount = 0; goodCount = 0; okayCount = 0; missCount = 0;
		points = 0; combo = 0;
		timer = 0;
		clip.start();//test
		runner.start();
	}
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		JFrame frame = new JFrame();
		//frame.add(new Test2());
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
		/**
		 * This part is still a work in progress
		 */
	}
	
	@Override
	public void run() {
		//purpose of this thread is to repeatedly repaint the jpanel
		//clip1.start();
		scoreDisplay.setBounds(1200, 25, 200, 30);
		scoreDisplay.setFont(new Font("Century Gothic", Font.BOLD, 30));
		scoreDisplay.setForeground(Color.WHITE);
		
		comboDisplay.setBounds(50, 600, 200, 30);
		comboDisplay.setFont(new Font("Century Gothic", Font.BOLD, 30));
		comboDisplay.setForeground(Color.WHITE);
		int scoreIconTimer = 0;
		boolean showScoreIcon = false;
		while(true) {//loops continuously
			this.remove(scoreDisplay);//removes score
			scoreDisplay.setText(Integer.toString(points));//updates the text of the score
			this.add(scoreDisplay);//displays new score
			
			this.remove(comboDisplay);
			comboDisplay.setText("x"+Integer.toString(combo));
			this.add(comboDisplay);
			//String pointState = "";
			//will check if it's time for the first note in the list to go up and cast appropriately
			if(goingNote == null && list.size() > 0) {	
				if(list.get(0).getStartTime() == timer) {
					goingNote = list.remove(0);//setting goingNote as the next Note
				}
			}
			else if (goingNote == null && list.size() == 0) {//if all notes are used, meaning game is complete
				clip.stop();
				this.setVisible(false);
				break;
			}
			else if(goingNote != null){//if there is a note on screen
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
						goingNote.getApproachCircle().width-=4.0;
						goingNote.getApproachCircle().height-=4.0;
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
					/*if(prevNote instanceof SliderNote2) {//if missed note is a slider note
						SliderNote2 prevS = (SliderNote2)prevNote;
						if(prevS.getEndTime() <= timer) {
							combo = 0;
						}
					}
					else {//just set combo to 0 if they missed a circle note
						combo = 0;
					}*/
					combo = 0;
					//missCount++;
					/**
					 * note: Miss is counted differently
					 * this is because once a slidernote is missed, the count repeatedly increases as it slides
					 * therefore, the missCount will be increments when the missed slider is removed and when a missed circle note is removed
					 */
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
					//System.out.println("showing score");
					scoreIconTimer+=50;
					//scoreIconTimer+=25;
				}
			}
			
			//System.out.println(timer+Boolean.toString(goingNote == null));
			try {
				runner.sleep(50);
				timer+=50;//count milliseconds
				//runner.sleep(25);
				//timer+=25;
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
}