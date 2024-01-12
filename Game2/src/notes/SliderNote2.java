package notes;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class SliderNote2 extends Note implements KeyListener{
	/**
	 * Fields
	 */
	private int x2,y2;//end coordinates of the pathLine
	private double xChange, yChange;//the change from x to x2 and y to y2
	private int rate; //number that determines the speed of slider note
	private Line2D.Double pathLine;//the line across which the circle part has to move. Coordinates relative to JPanel
	private SliderShape sliderShape;//the shape across with the circle part has to move
	private boolean slideState = false;//checks and determines if the slider note is moving or not
	protected ImageIcon good;//the image that will represent a 100 
	protected ImageIcon okay;//the image that will represent a 50
	protected ImageIcon miss;//the image that will represent a miss
	
	/**
	 * Constructor
	 * pre: Five int parameters: int x1, int y1, int x2, int y2, int change. Two long parameters: long startTime, long endTime. 
	 * post: A SlderNote Object is instantiated witht the following values:
	 * this.x set to x1, this.y set to y1, this.x2 set to x2, this.y2 set to y2, rate set to change, this.startTime set to startTime, this.endTime to endTime
	 */
	public SliderNote2(int x1, int y1, int x2, int y2, long startTime, long endTime, int change) {
		super(x1,y1,startTime, endTime);
		this.x2 = x2; this.y2 = y2;//setting both coordinates
		xChange = x2-x;  yChange = y2-y;//calculating the change (rise and run) of the two coordinates
		pathLine = new Line2D.Double(centerX, centerY, centerX+xChange, centerY+yChange);//the path the circle part of SliderNote will follow
		sliderShape = new SliderShape((int)(centerX), (int)(centerY), (int)(centerX+xChange), (int)(centerY+yChange));//the shape the circle part will move through
		rate = change;
		this.setBounds(x1, y1, d, d);
		this.addKeyListener(this);
	}

	/**
	 * Accessor and Modifier methods for SliderNote class
	 */
	
	
	/**
	 * Accessor method for int rate
	 * pre: N/A
	 * post: Returns the int value of this object's rate
	 */
	public int getRate() {
		return rate;
	}
	
	/**
	 * Accessor method for Line2D.Double pathLine
	 * pre: N/A
	 * post: Returns the Line2D.Double object of this object's pathLine
	 */
	public Line2D.Double getPathLine(){
		return pathLine;
	}
	
	/**
	 * Accessor method for boolean slideState
	 * pre: N/A
	 * post: Returns the boolean value of this object's slideState
	 */
	public boolean getSlideState() {
		return slideState;
	}
	
	/**
	 * Modifier method for boolean slideState
	 * pre: One boolean parameter: boolean slideState
	 * post:N/A (void)
	 */
	public void setSlideState(boolean slideState) {
		this.slideState = slideState;//sets slideState to argument's slideState
	}
	
	/**
	 * Accessor method for SliderShape sliderShape
	 * pre: N/A
	 * post: Returns a SliderShape object of this object's sliderShape
	 */
	public SliderShape getSliderShape() {
		return sliderShape;
	}
	
	
	/**
	 * Moves the slider until it reaches its end coordinates
	 * pre: N/A
	 * post: N/A (void)
	 */
	public void moveSlider() {
		if(slideState) {
			x += xChange/rate;//_Change/rate is how much the circle part moves in each loop of the thread
			y += yChange/rate;
			
			/*
			 * Below code for checking mouse position is copied from keyPressed method
			 */
			Point mouseP = this.getMousePosition();
			boolean mouseIn = false;//stores if mouse is within the circle part or not
			Line2D.Double line = new Line2D.Double(centerXLocal, centerYLocal, centerXLocal+d/2+200, centerYLocal+d/2+200);//sets a line from CircleNote center to the edge of the circle
			//check line is longer (+200 for x and y) for leniency
			//20 is subtracted from this radius at this radius is that of the rectangular image, not the circle itself
			AffineTransform t = new AffineTransform();
			double rotateCount = 0;
			while(rotateCount < 6.28) {
				Shape checkLine = t.createTransformedShape(line);//the line gets repeatedly rotated about the center of the circle
				t.rotate(0.01, centerXLocal, centerYLocal);//sets rotation
				if(mouseP != null) {
					if(checkLine.getBounds().contains(mouseP)) {//checks if the mouse is within the bounds of rotated line
						mouseIn = true;
						break;
					}
				}
				rotateCount+=0.01;//increments rotate count until a full rotation (2 pi radians is complete)
			}
			if(x == x2+xChange/rate && y == y2+yChange/rate) {//checks until the x and y coordinates are passed x2 and y2
				slideState = false;//done moving
				return;
			}
			else if(!mouseIn) {
				//slideState = false;
				pointState = "lost";//user's mouse is not within the circle, and therefore does not get points
			}
			this.setBounds(x, y, d, d);//setting the new bounds for the slider note
		}
	}
	
	/**
	 * Overridden KeyListener Methods
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		/**
		 * Copied from CircleNote keyPressed
		 */
		//checks if the key q is pressed and the mouse is within the CircleNote if it hasn't been pressed yet
				if((e.getKeyChar() == 'q' || e.getKeyChar() == 'w') && !pressedState && !slideState) {
					if(this.getMousePosition() != null) {//if the mouse is within the component, getMousePosition() will not return null
						Point mouseP = this.getMousePosition();//stores mouse position in variable(relative to CircleNote, not actual panel)
						if(Level1.timer >= endTime-50 || Level2.timer >= endTime-50) {//if perfect scoring range is done(50 milliseconds before endTime)
							firstEnd = true;
						}
						Line2D.Double line = new Line2D.Double(centerXLocal, centerYLocal, centerXLocal+d/2-20, centerYLocal+d/2-20);//sets a line from CircleNote center to the edge of the circle
						//20 is subtracted from this radius at this radius includes the rectangular image, not the circle itself (Check this later)
						AffineTransform t = new AffineTransform();
						double rotateCount = 0;
						while(rotateCount < 6.28) {
							Shape checkLine = t.createTransformedShape(line);//the line gets repeatedly rotated about the center of the circle
							t.rotate(0.01, centerXLocal, centerYLocal);//sets rotation
							if(checkLine.getBounds().contains(mouseP)) {//checks if the mouse is within the bounds of rotated line
								pressedState = true;
								slideState = true;//the slider should start moving
								if (Level1.timer > 0)
									System.out.println("POINT!!"+Level1.timer);
								else if (Level2.timer > 0)
									System.out.println("POINT!!"+Level2.timer);
								/**
								 * giving an appropriate amount of points depending on timing
								 */
								if(approachCircle.width <= d+10.0 && !firstEnd) {
									pointState = "perfect";
									//System.out.println("POINTS: PERFECT!!");
								}
								else if(approachCircle.width <= d+15) {
									pointState = "good";
									//System.out.println("POINTS: GOOD!");
									
								}
								else {
									pointState = "okay";
									//System.out.println("POINTS: OKAY");
								}
								break;
							}
							rotateCount+=0.01;//increments rotate count unitl a full rotation (2 pi radians is complete)
						}
					}
				}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == 'q' || e.getKeyChar() == 'w') {
			if(slideState) {//if slider is moving and the user released the key, they should not get full points even if they hit perfectly
				pointState = "lostByRelease";
				//System.out.println("LOST by release");
			}
		}
		
	}
}
