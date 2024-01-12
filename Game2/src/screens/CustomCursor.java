package screens;
/**
 * Fayad Alman & Mathuran Sivakaran
 * January 14, 2021
 * This program will create a custom cursor for each screen
 */
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class CustomCursor {
	// Sets the image of the cursor
	static ImageIcon customimage = (new ImageIcon("resources\\images\\cursor.png"));
	// Scales the image to cursor size
	static Image customImage = customimage.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
	// Replaces default cursor with custom cursor
	public static Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(customImage, new Point(0, 0), "customCursor");
}
