package screens;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import notes.CircleNote2;
import notes.Note;
import notes.SliderNote2;
import notes.Level1;
import notes.Level2;

public class Level_Frame extends JFrame implements Runnable, ActionListener{
	//JPanel level1Panel = new JPanel(); // Panel for the first level
	
	Level1 levelPanel;
	Level2 level2Panel;
	int levelNum;//will determine which level will be shown, 1 for level1 and 2 for level2
	JButton endPanelBack;//will be initialized as the endpanel's back button that takes the user back to level select
	Thread runner = new Thread(this);//will continuously check if the test2 panel is done
	
	/**
	 * Notes ArrayList used for level 1 and level 2
	 */
	ArrayList <Note> list = new ArrayList<Note>();
	ArrayList <Note> list2 = new ArrayList<Note>();
	ArrayList <Note> list3 = new ArrayList<Note>();
	
	/**
	 * Constructor
	 * pre: One int parameter: int levelNum
	 * pre: A Level1_Panel object is instantiated with appropriate values
	 */
	public Level_Frame(int levelNum) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		super("LEVEL ONE PANEL");
		this.levelNum = levelNum;
		Actions();
	}
	
	/**
	 * Purpose of this method: Creates the panel and the buttons
	 * Pre: N/A
	 * Post: N/A
	 */
	public void Actions () throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		//setSize(1350, 690); // Sets the size of the panel
		switch(levelNum) {//decides which level is instantiated
			case 1:
				list.add(new SliderNote2(500,100,800,100,600,1300,20));
				list.add(new SliderNote2(800,500,500,500,2400,3100,20));
				list.add(new SliderNote2(300,420,300,180,4200,4900,15));
				list.add(new SliderNote2(1000,180,1000,420,5850,6550,15));
				list.add(new CircleNote2(900,200,7500,8200));
				list.add(new CircleNote2(750,350,9100,9800));
				list.add(new CircleNote2(600,200,10700,11400));
				list.add(new CircleNote2(450,350,12400,13100));
				list.add(new CircleNote2(450,550,13300,14000));
				list.add(new CircleNote2(600,550,14100,14800));
				list.add(new SliderNote2(600,450,600,400,14900,15600,5));
				list.add(new CircleNote2(650,300,16000,16700));
				list.add(new CircleNote2(650,300,17100,17800));
				list.add(new SliderNote2(625,250,525,150,17950,18650,10));
				list.add(new SliderNote2(525,100,675,100,19350,20050,10));
				list.add(new CircleNote2(725,150,20600,21300));
				list.add(new CircleNote2(725,250,21350,22050));
				list.add(new SliderNote2(775,350,800,350,22300,23000,5));
				list.add(new CircleNote2(825,250,23550,24250)); 
				list.add(new SliderNote2(875,150,975,200,24400,25100,10));
				list.add(new SliderNote2(975,250,975,350,26100,26800,10));
				list.add(new CircleNote2(925,375,27800,28500));
				list.add(new CircleNote2(825,400,28800,29500));
				list.add(new CircleNote2(600,350,30000,30700));
				list.add(new CircleNote2(300,300,30850,31550));
				list.add(new CircleNote2(550,250,31700,32400));
				list.add(new CircleNote2(850,200,32550,33250));
				list.add(new SliderNote2(975,250,975,300,33350,34050,5));
				list.add(new SliderNote2(795,450,555,450,34650,35350,15));
				list.add(new SliderNote2(455,350,695,350,36300,37000,15));
				list.add(new SliderNote2(595,250,355,250,37950,38650,15));
				list.add(new CircleNote2(300,200,39900,40600));
				list.add(new CircleNote2(300,500,40700,41400));
				list.add(new SliderNote2(375,500,675,500,41450,42150,20));
				list.add(new SliderNote2(575,400,275,400,43300,44000,20));
				list.add(new CircleNote2(400,300,45050,45750));
				list.add(new CircleNote2(550,250,45800,46500));
				list.add(new CircleNote2(750,450,46650,47350));
				list.add(new CircleNote2(850,150,47400,48100));
				list.add(new CircleNote2(950,350,48200,48900));
				levelPanel = new Level1(list, new File("resources\\songs\\song1.wav"));//setting level1Panel as Test2 panel(contains "level")
				break;
			case 2:
				list.add(new SliderNote2(300,200,400,200,100,750,5));
				list2.add(new CircleNote2(500,275,900,1550));
				list.add(new SliderNote2(550,300,750,300,1400,2050,10));
				list2.add(new CircleNote2(700,475,2250,2900));
				list3.add(new CircleNote2(600,475,2350,3000));
				list.add(new CircleNote2(400,275,2750,3400));
				list2.add(new CircleNote2(600,100,3150,3800));
				
				level2Panel = new Level2(list, list2, list3, new File("resources\\songs\\song2.wav"));
				break;
		}
		if (levelNum == 1) {
			levelPanel.setBackground(Color.BLACK);
			add(levelPanel);
		}
		else {
			level2Panel.setBackground(Color.BLACK);
			add(level2Panel);
		}
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		runner.start();
	}

	@Override
	public void run() {
		//long timer = 0;
		while(true) {
			if (levelNum == 1) {
				if(!levelPanel.isVisible()) {//if the Test2 panel is done playing the level, show end panel
					EndPanel endPanel = new EndPanel(levelPanel);//endPanel will use info (number of hits) to display info
					this.add(endPanel);
					endPanelBack = endPanel.getBack();
					endPanelBack.addActionListener(this);
					this.pack();

					//while(true) {//test
					//System.out.println(endPanel.isVisible());
					//}
					break;
				}
			}
			else if (levelNum == 2) {
				if(!level2Panel.isVisible()) {//if the Test2 panel is done playing the level, show end panel
					EndPanel2 endPanel = new EndPanel2(level2Panel);//endPanel will use info (number of hits) to display info
					this.add(endPanel);
					endPanelBack = endPanel.getBack();
					endPanelBack.addActionListener(this);
					this.pack();

					//while(true) {//test
					//System.out.println(endPanel.isVisible());
					//}
					break;
				}
			}
			try {
				runner.sleep(50);
				//timer+=50;//count milliseconds
				//runner.sleep(25);
				//timer+=25;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == endPanelBack) {
			LevelSelectFrame f = new LevelSelectFrame();
			f.setFocusable(true);
			f.requestFocus();
			f.setVisible(true);
			this.setVisible(false);
		}
	}
}