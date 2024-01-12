package notes;
/**
 * Mathuran Sivakaran and Fayad Alman
 * January 8, 2022
 * A class that contains a Shape that represents the shape used in SliderNote
 */
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D.Double;

import javax.swing.*;

public class SliderShape{
	/**
	 * Fields
	 */
	private int x1,y1,x2,y2;//x1 and y1 are the start coordinates and x2 and y2 are the end coordinates
	//these coordinates are going to be the center points for the start and end circles
	private Ellipse2D.Double startCircle, endCircle;//the circles indicating the start and end positions of the SliderNote
	//Line2D.Double line1,line2;//the top and bottom lines of the slider shape
	private Rectangle2D.Double line1, line2;//the two lines connecting the start and end circles together
	private AffineTransform t;
	private Area tShape;//to group the shapes together
	int d = 77;
	
	/**
	 * Constructor
	 * pre: Four int parameters: int x1, int y1, int x2, int y2
	 * post: A SliderShape object is instantiated with the following values
	 * this.x1 set to x1, this.y1 set to y1, this.x2 set to x2, this.y2 set to y2
	 */
	public SliderShape(int x1, int y1, int x2, int y2) {
		this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;//setting all x and y coordinates
		/**
		 * Initializing the start and end circles and their coordinates
		 */
		startCircle = new Ellipse2D.Double();
		startCircle.width = d; startCircle.height = d;
		startCircle.setFrameFromCenter(x1, y1, x1-d/2, y1-d/2);
		
		
		
		/**
		 * Initializing the lines of the slider shape
		 * Note: Temporary positions before rotating
		 * The slider shape is made a straight horizontal shape. 
		 * It will be rotated later to match the direction of the SliderNote
		 * It will be rotated about the startCircle
		 */
		int xChange = x2-x1;//change in x coordinates
		int yChange = y2-y1;//change in y coordinates
		double angle = Math.atan2(yChange, xChange);//angle to rotate
		int lineLength = (int)(Math.sqrt(Math.pow(xChange, 2) + Math.pow(yChange, 2)));
		line1 = new Rectangle2D.Double(x1, y1-d/2, lineLength, 1);//top line
		line2 = new Rectangle2D.Double(x1, y1+d/2-1, lineLength, 1);//bottom line
		
		endCircle = new Ellipse2D.Double();
		endCircle.width = d; endCircle.height = d;
		endCircle.setFrameFromCenter(x1+lineLength, y1, x1+lineLength-d/2, y1-d/2);//temp coordinates for endCircle
		
		Area shape = new Area();//groups circles and lines together
		shape.add(new Area(startCircle));
		shape.add(new Area(endCircle));
		shape.add(new Area(line1));
		shape.add(new Area(line2));
		/**
		 * The following lines rotate the initial Area, which was just a straight slider shape
		 * This will match the direction of the SliderShape movement
		 * tShape is the object that will be used to display the slider shape
		 */
		t = new AffineTransform();
		t.rotate(angle,x1,y1);
		tShape = new Area(t.createTransformedShape(shape));//rotates shape
		//System.out.println(lineLength);
	}
	
	/**
	 * Accessor and Modifier methods for SliderShape fields
	 */
	
	/**
	 * Accessor method for int x1
	 * pre: N/A
	 * post: Returns an int value of the objects's x1
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * Modifier method for int x1
	 * One int parameter: int x1
	 * post: N/A(void)
	 */
	public void setX1(int x1) {
		this.x1 = x1;//sets object's x1 to argument x1
	}
	
	
	/**
	 * Accessor method for int y1
	 * pre: N/A
	 * post: Returns an int value of the objects's y1
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Modifier method for int y1
	 * One int parameter: int y1
	 * post: N/A(void)
	 */
	public void setY1(int y1) {
		this.y1 = y1;//sets object's y1 to argument y1
	}

	
	/**
	 * Accessor method for int x2
	 * pre: N/A
	 * post: Returns an int value of the objects's x2
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Modifier method for int x2
	 * One int parameter: int x2
	 * post: N/A(void)
	 */
	public void setX2(int x2) {
		this.x2 = x2;//sets object's x2 to argument x2
	}
	
	/**
	 * Accessor method for int y2
	 * pre: N/A
	 * post: Returns an int value of the objects's y2
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * Modifier method for int y2
	 * One int parameter: int y2
	 * post: N/A(void)
	 */
	public void setY2(int y2) {
		this.y2 = y2;//sets object's y2 to argument y2
	}
	
	/**
	 * Accessor method for Area tShape
	 * pre: N/A
	 * post: Returns an Area object of this object's tShape
	 */
	public Area getTShape() {
		return tShape;
	}
	
}
