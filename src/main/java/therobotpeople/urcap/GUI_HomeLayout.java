package therobotpeople.urcap;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * GUI_HomeLayout provides the layout of all the GUI elements for the homepage. 
 */
public abstract class GUI_HomeLayout implements Runnable {	
	// Static variable to determine whether or not the GUI is currently open
	static boolean on = false;
	
	JFrame main;
	JLabel status;
	JComboBox presets;
	JButton setup;
	JButton delete;
	JButton stop;
	JButton exit;
	JButton play;
	JButton pause;	
	JPanel preview;
	ImagePanel bg;
	
	@Override
	public void run() {				
		// If the Home GUI has already been created, then it is just currently invisible.
		if (on == true) {
			refresh_presets(presets);
			main.setVisible(true);
			return;
		}
		
		main = new JFrame();
		main.setLayout(null);
		main.setSize(800,600);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		status = new JLabel("Status: Choose Preset");
		status.setBounds(10,75,180,25);
		
		presets = new JComboBox();
		presets.setBounds(10,150,180,25);
		refresh_presets(presets);
		
		setup = new JButton("New Preset");
		setup.setBounds(10,200,180,25);
		setup.addActionListener(new_preset_button_action());

		delete = new JButton("Delete Preset");
		delete.setBounds(10,250,180,25);
		delete.addActionListener(delete_preset_button_action());
		
		presets.addItemListener(preset_button_listener());
		
		stop = new JButton("Stop");
		stop.setBounds(10,375,180,50);
		stop.addActionListener(stop_button_action());
		
		exit = new JButton("Exit");
		exit.setBounds(10,450,180,50);
		exit.addActionListener(exit_button_action());
		
		play = new JButton();
		play.setBounds(0,500,100,100);
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.addActionListener(play_button_action());
		try {
			Image img = ImageIO.read(getClass().getResource("/play_btn.png"));
			play.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			
		}
		
		
		pause = new JButton();
		pause.setBounds(100,500,100,100);
		pause.setOpaque(false);
		pause.setContentAreaFilled(false);
		pause.setBorderPainted(false);
		pause.addActionListener(pause_button_action());
		try {
			Image img = ImageIO.read(getClass().getResource("/pause_btn.png"));
			pause.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			
		}
		
		// preview = new JPanel();
		// preview.setBackground(Color.WHITE);
		// preview.setLayout(null);
		
		bg = new ImagePanel("bg_home.png");
		bg.setBounds(0, 0, 800, 600);
		
		main.add(status);
		main.add(presets);
		main.add(setup);
		main.add(delete);
		main.add(stop);
		main.add(exit);
		main.add(play);
		main.add(pause);
		// main.add(preview);
		main.add(bg);
		
		main.setVisible(true);
		
		on = true;
		
		DashboardServerInterface.Open();
	}
	
	// Functions that require implementation. Found in GUI_HomeImpl.
	public abstract ActionListener play_button_action();
	public abstract ActionListener pause_button_action();
	public abstract ActionListener exit_button_action();
	public abstract ActionListener stop_button_action();
	public abstract ItemListener preset_button_listener();
	public abstract ActionListener delete_preset_button_action();
	public abstract ActionListener new_preset_button_action();
	public abstract void refresh_presets(JComboBox presets); 
}