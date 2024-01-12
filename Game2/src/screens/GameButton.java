package screens;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import javax.swing.*;

/**
 * Mathuran Sivakaran and Fayad Alman
 * January 13, 2021
 * A button class that has two different icons
 */
public class GameButton extends JButton{
	/*
	 * default icon for a button
	 */
	private ImageIcon circle;
	
	private int x,y;//coordinates for button
	private int w,h;//width and height
	private String name;//what the button says
	
	public GameButton(int x, int y, int w, int h, ImageIcon i) {
		//super(name);
		this.setForeground(Color.BLUE);//text colour to blue
		this.x = x; this.y = y;//setting coordinates
		this.w = w; this.h = h;
		//name = this.name;//setting the text in the button
		
		circle = i;
		Image temp = circle.getImage();
		//temp = temp.getScaledInstance(160, 40, java.awt.Image.SCALE_SMOOTH);
		temp = temp.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		circle = new ImageIcon(temp);
		this.setIcon(circle);//default, smaller icon
		//temp = temp.getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH);
		temp = temp.getScaledInstance(w+w/4, h+h/4, java.awt.Image.SCALE_SMOOTH);
		circle = new ImageIcon(temp);
		this.setRolloverIcon(circle);//roll over, larger icon
		//making sure only the icon and label are visible, none of the surrounding parts of the button
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setHorizontalTextPosition(JButton.CENTER);//making sure text in in center of button
		this.setVerticalTextPosition(JButton.CENTER);//making sure text in in center of button
		
		//this.setBounds(x, y, 160, 80);//default size
		this.setBounds(x, y, w, h);
	}
	
	
	
	/**
	 * Adjusting button bounds for larger icon
	 * pre: One Point paramter: Point mousePoint
	 * post: N/A (void)
	 */
	public void adjustButton(Point mousePoint) {
		if(mousePoint != null) {//if mouse is with the default button
			//this.setBounds(x-60,y-15, 240, 70);//moving buttons left and up and making it bigger for the new icon
			this.setBounds(x-(w/3),y-h/2, w+w/2, h*2);//moving buttons left and up and making it bigger for the new icon
			this.setHorizontalTextPosition(JButton.CENTER);//making sure text in in center of button
			this.setVerticalTextPosition(JButton.CENTER);//making sure text in in center of button
		}
		else {//if mouse is not within the default button or larger button
			this.setBounds(x, y, w,h);
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}
	
	
	
}
