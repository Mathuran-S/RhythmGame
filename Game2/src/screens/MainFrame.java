package screens;
/***
 * Fayad Alman & Mathuran Sivakaran
 * December 22, 2021
 * This program will create a JPanel for the title screen of the game
 * The user will click a button (either play or instructions) and this panel will transport them to that respective screen
 */
import javax.swing.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener {
	public final double resizeFactor = 0.7105;
	/*JPanel titlePanel = new JPanel(); // Panel for the title screen
	JLabel title = new JLabel("Welcome to C-Beats!"); // Title of the game
	*/
	TitlePanel tPanel = new TitlePanel();
	JButton play = tPanel.getPlay();//new JButton("Play"); // Button that transports user to the LevelSelectPanel
	JButton instructions = tPanel.getInstructions();//new JButton("Instructions"); // Button that transports user to the InstructionsPanel
	/*static LevelSelectPanel gui1;
	static InstructionFrame gui2;*/
	
	/**
	 * Purpose of this method: Constructor for the panel
	 * Pre: N/A
	 * Post: N/A
	 */
	public MainFrame() {
		super("MainFrame"); 
		//setSize(1900, 1000); // Sets the size of the frame
		//titlePanel.setLayout(null); // Eliminates the default layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exits the frame when the close button is pressed
        Actions();
        setVisible(true); // Reveals the frame as visible
	}
	public static void main(String[] args) {
		MainFrame gui = new MainFrame();
	}
	/**
	 * Purpose of this method: Creates the panel and the buttons
	 * Pre: N/A
	 * Post: N/A
	 */
	public void Actions () {
		setSize(1350, 690); // Sets the size of the panel
		// Creates the interactive buttons
		play.addActionListener(this);
		instructions.addActionListener(this);
		// Creates the labels and adds the buttons
		/*title.setBounds(480, 50, 400, 200);
		title.setFont(new Font("Verdana", Font.BOLD, 32));
		play.setBounds(375, 450, 200, 100);
		instructions.setBounds(775, 450, 200, 100);
		titlePanel.add(title);
		titlePanel.add(play);
		titlePanel.add(instructions);
		titlePanel.setBackground(Color.PINK);
		add(titlePanel);*/
		this.add(tPanel);
		this.pack();
		//setVisible(true);
	}
	/**
	 * Purpose of this method: The responses when the user clicks one of the buttons; it will transport the user to a different screen
	 * Pre: N/A
	 * Post: N/A
	 */
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == play) {
			setVisible(false);
			/*gui1 = new LevelSelectPanel(); // Sends user to the Level Select screen
			gui1.setFocusable(true);
		    gui1.setBounds(0, 0, 1350, 690);
		    gui1.requestFocus();
		    gui1.setVisible(true); // Makes the Instructions screen visible*/
		}
		if(event.getSource() == instructions) {
			setVisible(false);
			/*gui2 = new InstructionFrame(); // Sends user to the Instructions screen
			gui2.setFocusable(true);
		    gui2.setBounds(0, 0, 1350, 690);
		    gui2.requestFocus();
		    gui2.setVisible(true); // Makes the Instructions screen visible*/
		}
	}
	
}