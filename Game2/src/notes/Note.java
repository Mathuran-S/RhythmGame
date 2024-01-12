package notes;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

/**
 * Parent class for CircleNote and SliderNote
 */
import javax.swing.*;

public class Note extends JLabel implements KeyListener{
	/**
	 * Fields
	 */
	protected int x, y;//x and y coordinates, and diameter d
	public int d;//will be common for all. just here for test in case we need to change diameter length
	protected double centerX, centerY;//x and y coordinates of the center of the circle of the Note relative to JPanel it is set on
	protected double centerXLocal, centerYLocal;//x and y coordinates of the center of the circle of the Note relative to the Note componenent
	protected long startTime, endTime;//the time during the song when the Note will appear, and when it will disappear
	protected boolean firstEnd = false;//stores whether the time has reached the end of the perfect note times (50 ms before it goes offscreen)
	
	protected boolean pressedState = false;//used to determine when the note is pressed 
	protected String pointState = "";//will be used to store if the user hit the note at perfect, good or okay time
	protected ImageIcon circle;//the image that will represent a circle note
	protected Ellipse2D.Double approachCircle;//this circle will shrink into the circle note for the user to time their click/press	
	
	/**
	 * Constructor
	 * pre: Two int parameters: int x, int y. Two long parameters: long startTime, long endTime
	 * post: A Note object is instantiated with the following values
	 * this.x set to x, this.y set to y, this.startTime set to startTime, this.endTime to endTime
	 * Other fields are calculated and instantiated based on the x and y values
	 */
	public Note(int x, int y, long startTime, long endTime) {
		this.x = x; this.y = y; //setting values to x and y fields
		d = 80;
		
		/* The below lines calculate the center x coordinate and y coordinate of the Note
		 * This is done by adding half of the Note length to the x or y coordinate
		 */
		centerX = x + d/2; centerY = y + d/2;//this is the coordinates relative to the JPanel it is on
		centerXLocal = d/2; centerYLocal = d/2;//this is the coordinates relative to itself
		
		this.startTime = startTime;
		this.endTime = endTime;
		
		/*
		 * Setting the ImageIcon for the circle of the Note
		 */
		circle = new ImageIcon("resources\\images\\circleNote.png");
		Image temp = circle.getImage();
		temp = temp.getScaledInstance(d, d, java.awt.Image.SCALE_SMOOTH);
		circle = new ImageIcon(temp);
		this.setIcon(circle);
		
		this.setBounds(x, y, d, d);//setting its coordinates on the JPanel it will be added on
		
		/*
		 * Initializing the approachCircle
		 */
		approachCircle = new Ellipse2D.Double();
		approachCircle.setFrameFromCenter(centerX, centerY, centerX-d/2-25, centerY-d/2-25);//this circle will start bigger than CircleNote
		this.setFocusable(true);
		this.requestFocus();//setting focus to the Note, will allow it to detect key presses
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("FROM NOTE CLASS");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Accessor and Modifier methods for Note fields
	 * Note: Accessor methods for int x and int y are already defined as part of the Component class
	 */
	
	/**
	 * Modifier method for int x
	 * pre: One int parameter: int x
	 * post: N/A(void)
	 */
	public void setX(int x) {
		this.x = x;//object's x value is set to argument x value
	}
	
	/**
	 * Modifier method for int y
	 * pre: One int parameter: int y
	 * post: N/A(void)
	 */
	public void setY(int y) {
		this.y = y;//object's y value is set to argument y value
	}
	
	/**
	 * Accessor method for double centerX
	 * pre: N/A
	 * post: Returns a double value of the objects's centerX
	 */
	public double getCenterX() {
		return centerX;
	}

	/**
	 * Modifier method for double centerX
	 * One double parameter: double centerX
	 * post: N/A(void)
	 */
	public void setCenterX(double centerX) {
		this.centerX = centerX;//sets object's centerX to argument centerX
	}
	
	/**
	 * Accessor method for double centerY
	 * pre: N/A
	 * post: Returns a double value of the object's centerY
	 */
	public double getCenterY() {
		return centerY;
	}
	
	/**
	 * Modifier method for double centerY
	 * pre: One double parameter: double centerY
	 * post: N/A(void)
	 */
	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	/**
	 * Accessor method for double centerXLocal
	 * pre: N/A
	 * post: Returns a double value of the objects's centerXLocal
	 */
	public double getCenterXLocal() {
		return centerXLocal;
	}

	/**
	 * Modifier method for double centerXLocal
	 * One double parameter: double centerXLocal
	 * post: N/A(void)
	 */
	public void setCenterXLocal(double centerXLocal) {
		this.centerXLocal = centerXLocal;//sets object's centerXLocal to argument centerXLocal
	}
	
	/**
	 * Accessor method for double centerYLocal
	 * pre: N/A
	 * post: Returns a double value of the object's centerYLocal
	 */
	public double getCenterYLocal() {
		return centerYLocal;
	}
	
	/**
	 * Modifier method for double centerYLocal
	 * pre: One double parameter: double centerYLocal
	 * post: N/A(void)
	 */
	public void setCenterYLocal(double centerYLocal) {
		this.centerYLocal = centerYLocal;//sets object's centerYLocal to argument centerYLocal
	}
	
	/**
	 * Accessor method for long startTime
	 * pre: N/A
	 * post: Returns a long value of the object's startTime
	 */
	public long getStartTime() {
		return startTime;
	}
	
	/**
	 * Modifier method for long startTime
	 * pre: One long parameter: long startTime
	 * post: N/A(void)
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;//sets object's startTime to argument startTime
	}
	
	/**
	 * Accessor method for long endTime
	 * pre: N/A
	 * post: Returns a long value of the object's endTime
	 */
	public long getEndTime() {
		return endTime;
	}
	
	/**
	 * Modifier method for long endTime
	 * pre: One long parameter: long endTime
	 * post: N/A(void)
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;//sets object's endTime to argument endTime
	}
	
	/**
	 * Accessor method for boolean pressedState
	 * pre: N/A
	 * post: Returns the boolean value of this object's pressedState
	 */
	public boolean getPressedState() {
		return pressedState;
	}
	
	/**
	 * Modifier method for boolean pressedState
	 * pre: One boolean parameter: boolean pressedState
	 * post: N/A (void)
	 */
	public void setPressedState(boolean pressedState) {
		this.pressedState = pressedState;//sets object's pressedState to argument pressedState
	}

	/**
	 * Accessor method for String pointState
	 * pre: N/A
	 * post: Returns the String value of the object's pointState
	 */
	public String getPointState() {
		return pointState;
	}
	
	/**
	 * Modifier method for String pointState
	 * pre: One String parameter: String pointState
	 * post: N/A (void)
	 */
	public void setPointState(String pointState) {
		this.pointState = pointState;//sets object's pointState to argument pointState
	}
	
	/**
	 * Accessor modifier for ImageIcon circle
	 * pre: N/A
	 * post: Returns the ImageIcon of the object's circle
	 */
	public ImageIcon getCircle() {
		return circle;
	}

	/**
	 * Modifier method for ImageIcon circle
	 * pre: One ImageIcon parameter: ImageIcon circle
	 * post: N/A(void)
	 */
	public void setCircle(ImageIcon circle) {
		this.circle = circle;//sets object's circle to argument circle
	}

	/**
	 * Accessor method for Ellipse2D.Double approachCircle
	 * pre: N/A
	 * post: Returns the Ellipse2D.Double of the object's approachCircle
	 */
	public Ellipse2D.Double getApproachCircle() {
		return approachCircle;
	}

	/**
	 * Modifier method for Ellipse2D.Double approachCircle
	 * pre: One Ellipse2D.Double parameter: Ellipse2D.Double approachCircle
	 * post: N/A(void)
	 */
	public void setApproachCircle(Ellipse2D.Double approachCircle) {
		this.approachCircle = approachCircle;//sets object's approachCircle to argument approachCircle;
	}
}
