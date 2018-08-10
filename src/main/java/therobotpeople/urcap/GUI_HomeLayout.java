package therobotpeople.urcap;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Home_GUI implements Runnable {
	JFrame main;
	
	JLabel status;
	JComboBox<String> presets;
	JButton setup;
	JButton delete;
	JButton stop;
	JButton exit;
	JButton play;
	JButton pause;
	
	double pallet_width;
	double pallet_length;
	
	JPanel preview;
	BackgroundPanel bg;
	
	static boolean on = false;
	
	@Override
	public void run() {
		main = new JFrame();
		main.setLayout(null);
		main.setSize(800,600);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		status = new JLabel("Status: Choose Preset");
		status.setBounds(10,75,180,25);
		
		presets = new JComboBox<String>();
		presets.setBounds(10,150,180,25);
		refresh_presets();
		
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
		
		play = new JButton("Play");
		play.setBounds(0,500,100,100);
		play.addActionListener(play_button_action());
		
		pause = new JButton("Pause");
		pause.setBounds(100,500,100,100);
		pause.addActionListener(pause_button_action());
		
		preview = new JPanel();
		preview.setBackground(Color.WHITE);
		preview.setLayout(null);
		//some weird stuff here to set bounds
		
		bg = null;
		//some image loading here
		
		main.add(status);
		main.add(presets);
		main.add(setup);
		main.add(delete);
		main.add(stop);
		main.add(exit);
		main.add(play);
		main.add(pause);
		main.add(preview);
		main.add(bg);
	}
	public abstract ActionListener pause_button_action();
	public abstract ActionListener play_button_action();
	public abstract ActionListener exit_button_action();
	public abstract ActionListener stop_button_action();
	public abstract ItemListener preset_button_listener();
	public abstract ActionListener delete_preset_button_action();
	public abstract ActionListener new_preset_button_action();
	public void refresh_presets() {}; //reload the presets
}
