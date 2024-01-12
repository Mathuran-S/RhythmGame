package screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class InstructionFrame extends JFrame implements ActionListener{

	//static MainFrame gui1;
	InstructionPanelStuff iPanel = new InstructionPanelStuff();
	
	JButton back = iPanel.getBack();
	
	/**
	 * Constructor
	 */
	public InstructionFrame() {
		super("HOW TO PLAY");
		this.add(iPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		back.addActionListener(this);
		this.setResizable(false);
		this.setVisible(true);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == back) {
			this.setVisible(false);
		}
		
	}
}
