package screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import notes.CircleNote2;
import notes.SliderNote2;

public class InstructionPanelStuff extends JPanel implements Runnable, ActionListener {
	static MainFrame gui1;
	/**
	 * Instruction JLabels
	 */
	JLabel title = new JLabel("How to Play");
	JLabel circleExplain1 = new JLabel("C-Beats is a rhythm game based on clicking circles to the beat.");
	JLabel circleExplain2 = new JLabel("<html>When the approach circle touches the outside of the inner circle, <br/>press either Q or W on your keyboard to click.</html>");
	JLabel circleExplain3 = new JLabel("This game uses Q and W instead of your mouse click as that is much easier and more effective.");
	JLabel sliderExplain1 = new JLabel("Sliders extend on circles. When you see a slider, click the circle using Q or W.");
	JLabel sliderExplain2 = new JLabel("<html>Then drag your mouse to follow the circle as it moves across the slider while <br/>holding Q or W.</html>");
	JLabel sliderExplain3 = new JLabel("<html>Make sure to follow the slider all the way through, otherwise it won't count <br/>towards your final score.</html>");
	JLabel finalTips1 = new JLabel("Make sure you time your hits as accurately as possible in order to obtain the maximum score possible.");
	JLabel finalTips2 = new JLabel("The closer the approach circle is to the inner circle, the more points you get!");
	JLabel finalTips3= new JLabel("A perfect hit is 300 points. If you see a 100, 50, or a miss, that means you are clicking too fast or too slow.");
	JLabel finalTips4 = new JLabel("Keep that in mind and have fun playing C-Beats!");
	

	//JButton back = new JButton("Back"); // Button that transports user to the MainFrame
	GameButton back = new GameButton(20,20, 80,20,new ImageIcon("resources\\images\\buttons\\backButton.png"));
	/**
	 * Sample circle and slider notes for demonstration
	 * will loop repeatedly
	 * Thread will be used to repeatedly paint and loop
	 */
	CircleNote2 sampleC = new CircleNote2(1100,130,500,1200);//check if this is right time for appCircle to shrink in
	SliderNote2 sampleS = new SliderNote2(900,250,1200,250,500,1200,30);
	Thread runner = new Thread(this);
	boolean threadLoop = true;//whether the thread should run or not
	static long timer = 0;
	
	/**
	 * Constructor
	 */
	public InstructionPanelStuff() {
		setPreferredSize(new Dimension(1350, 690)); // Sets the size of the panel
		this.setLayout(null);
		this.setCursor(CustomCursor.customCursor);
		// Creates the interactive buttons
		back.addActionListener(this);
		// Creates the labels and adds the buttons
		title.setBounds(555, 30, 275, 40);
		title.setFont(new Font("Century Gothic", Font.BOLD, 35));
		title.setForeground(Color.BLACK);
		this.add(title);
		
		Font normalFont = new Font("Verdana", Font.PLAIN, 21);

		int horPlacement = 20;//in case it needs to be changed
		/*
		 * Adds the instructions explaining the circle
		 */
		circleExplain1.setBounds(horPlacement,100,700,50);
		circleExplain1.setFont(normalFont);
		circleExplain1.setForeground(Color.BLACK);//text colour changes (jan 16)
		this.add(circleExplain1);
		circleExplain2.setBounds(horPlacement,135,1300,60);
		circleExplain2.setFont(normalFont);
		circleExplain2.setForeground(Color.BLACK);
		this.add(circleExplain2);
		circleExplain3.setBounds(horPlacement,185,1000,50);
		circleExplain3.setFont(normalFont);
		circleExplain3.setForeground(Color.BLACK);
		this.add(circleExplain3);
		/*
		 * Adds the instructions explaining the slider
		 */
		sliderExplain1.setBounds(horPlacement,250,1000,50);
		sliderExplain1.setFont(normalFont);
		sliderExplain1.setForeground(Color.BLACK);
		this.add(sliderExplain1);
		sliderExplain2.setBounds(horPlacement,285,1000,60);
		sliderExplain2.setFont(normalFont);
		sliderExplain2.setForeground(Color.BLACK);
		this.add(sliderExplain2);
		sliderExplain3.setBounds(horPlacement,335,1000,60);
		sliderExplain3.setFont(normalFont);
		sliderExplain3.setForeground(Color.BLACK);
		this.add(sliderExplain3);
		
		/*
		 * Adds the instructions explaining some extra details
		 */
		finalTips1.setBounds(100,440,1300,50);
		finalTips1.setFont(normalFont);
		finalTips1.setForeground(Color.BLACK);
		this.add(finalTips1);
		finalTips2.setBounds(250,470,1000,50);
		finalTips2.setFont(normalFont);
		finalTips2.setForeground(Color.BLACK);
		this.add(finalTips2);
		finalTips3.setBounds(100,500,1500,50);
		finalTips3.setFont(normalFont);
		finalTips3.setForeground(Color.BLACK);
		this.add(finalTips3);
		finalTips4.setBounds(300,550,1000,50);
		finalTips4.setFont(new Font("Century Gothic", Font.BOLD, 35));
		finalTips4.setForeground(new Color(150,50,200));
		this.add(finalTips4);
		//back.setBounds(20, 20, 70, 30);
		this.add(back);
		runner.start();
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
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );//makes rounded edges look smoother
		g2.setColor(Color.PINK);
		g2.fillRect(0, 0, 1350, 690);
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(3));

		//for circlenote
		Ellipse2D.Double appCircleC = sampleC.getApproachCircle();
		appCircleC.setFrameFromCenter(sampleC.getCenterX(), sampleC.getCenterY(), sampleC.getCenterX()-appCircleC.width/2, sampleC.getCenterY()-appCircleC.height/2);//setting frame for approach circle
		g2.draw(appCircleC);
		//for slidernote
		Ellipse2D.Double appCircleS= sampleS.getApproachCircle();
		appCircleS.setFrameFromCenter(sampleS.getCenterX(), sampleS.getCenterY(), sampleS.getCenterX()-appCircleS.width/2, sampleS.getCenterY()-appCircleS.height/2);//setting frame for approach circle
		g2.draw(appCircleS);
		g2.draw(sampleS.getSliderShape().getTShape());

	}
	
	@Override
	public void run() {
		//purpose of this thread is to repeatedly repaint the jpanel
		//get's original coordinates of the slider note to reset after each 3000 milliseconds
		int sX = sampleS.getX(); int sY = sampleS.getY(); int sW = sampleS.getHeight(); int sH = sampleS.getHeight();
		boolean slideLoop = false;//checks if sampleS is done moving for the 3 second loop
		while(threadLoop) {//loops continuously
			Point mousePoint = back.getMousePosition();
			back.adjustButton(mousePoint);
			this.add(sampleC);
			this.add(sampleS);
			/*
			 * Shrinks the size of the approach circle for the demonstration
			 */
			if(sampleC.getApproachCircle().width > sampleC.d) {
				sampleC.getApproachCircle().width -= 3.5; sampleC.getApproachCircle().height -= 3.5;
			}
			if(sampleS.getApproachCircle().width > sampleS.d) {
				sampleS.getApproachCircle().width -= 3.5; sampleS.getApproachCircle().height -= 3.5;
			}
			/*
			 * Creates the slider animation
			 */
			else if(sampleS.getEndTime() < timer && !slideLoop){
				sampleS.setSlideState(true);
			}
			if(sampleS.getSlideState() && !slideLoop) {
				sampleS.moveSlider();
			}
			if(!sampleS.getSlideState() && timer > sampleS.getEndTime()){
				slideLoop = true;//slider is done moving for the 3 second loop
			}
			repaint();//call paintComponent();
			try {
				runner.sleep(50);
				timer+=50;//count milliseconds
				if(timer == 3000) {
					timer = 0;
					sampleC.getApproachCircle().width = 130; sampleC.getApproachCircle().height = 130;
					sampleS.getApproachCircle().width = 130; sampleS.getApproachCircle().height = 130;
					slideLoop = false;
					sampleS.setBounds(sX,sY,sW,sH);//resetting the sampleS position
					sampleS.setX(sX); sampleS.setY(sY);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Purpose of this method: The responses when the user clicks one of the buttons; it will transport the user to a different screen
	 * Pre: N/A
	 * Post: N/A
	 */
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == back) {
			setVisible(false);
			threadLoop = false;
			gui1 = new MainFrame(); // Sends user to the Title Screen
			gui1.setFocusable(true);
		   // gui1.setBounds(0, 0, 1350, 690);
		    gui1.requestFocus();
		    gui1.setVisible(true); // Makes the Title screen visible
		}
	}
}
