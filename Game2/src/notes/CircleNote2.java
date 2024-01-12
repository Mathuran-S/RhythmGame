package notes;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
/**
 * Mathuran Sivakaran, Fayad Alman
 * December 17, 2021
 * A class for the CircleNote
 */
public class CircleNote2 extends Note implements KeyListener{
	
	
	/**
	 * Constructor
	 * pre: Two int parameters: int x, int y. Two long parameters: long startTime, long endTime
	 * post: A CircleNote object is instantiated with the following values
	 * this.x set to x, this.y set to y, this.startTime set to startTime, this.endTime to endTime
	 */
	public CircleNote2(int x, int y, long startTime, long endTime) {
		super(x,y,startTime, endTime);
		this.addKeyListener(this);
	}
	
	/**
	 * Overridden KeyListener Methods
	 */
	
	//not used
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	/**
	 * Detects if q or w is pressed while the mouse is within the CircleNote ImageIcon
	 * pre: N/A
	 * post: N/A (void)
	 */
	public void keyPressed(KeyEvent e) {
		//checks if the key q or w is pressed and the mouse is within the CircleNote and the note isn't pressed yet
		if((e.getKeyChar() == 'q' || e.getKeyChar() == 'w') && !pressedState) {
			if(this.getMousePosition() != null) {//if the mouse is within the component, getMousePosition() will not return null
				Point mouseP = this.getMousePosition();//stores mouse position in variable(relative to CircleNote, not actual panel)
				if(Level1.timer >= endTime-50 || Level2.timer >= endTime-50) {//if perfect scoring range is done (50 milliseconds before endTime)
					firstEnd = true;
				}
				
				Line2D.Double line = new Line2D.Double(centerXLocal, centerYLocal, centerXLocal+d/2-20, centerYLocal+d/2-20);//sets a line from CircleNote center to the edge of the circle
				//20 is subtracted from this radius at this radius includes the rectangular image, not the circle itself
				AffineTransform t = new AffineTransform();
				double rotateCount = 0;
				while(rotateCount < 6.28) {
					Shape checkLine = t.createTransformedShape(line);//the line gets repeatedly rotated about the center of the circle
					t.rotate(0.01, centerXLocal, centerYLocal);//sets rotation
					if(checkLine.getBounds().contains(mouseP)) {//checks if the mouse is within the bounds of rotated line
						System.out.println("POINT!");//test print to see if it detects key press within CircleNote
						pressedState = true;
						/**
						 * giving an appropriate amount of points depending on timing
						 */
						if(approachCircle.width <= d+7.0 && !firstEnd) {
							pointState = "perfect";
							//System.out.println("POINTS: PERFECT!!");
						}
						else if(approachCircle.width <= d+12) {
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

	//not used
	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
	
}
