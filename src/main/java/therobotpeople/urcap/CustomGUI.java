package therobotpeople.urcap;

import com.ur.urcap.api.domain.script.ScriptWriter;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class CustomGUI implements Runnable {	
	public static boolean on;
	public static boolean stopped;
	public static ScriptWriter writer;
	
	public CustomGUI(ScriptWriter w) {
		writer = w;
		on = false; 
		stopped = false;
	}
	
	public void run() {
		// Open communications to the local server
		DashboardServerInterface.Open();
		
		// Create the JFrame that the UI will be held in
		final JFrame f = new JFrame();
		f.setLayout(null);
		f.setSize(802, 630);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Status Text Box
		final JTextField status = new JTextField();
		status.setBounds(410, 200, 180, 25);
		status.setOpaque(false);
		
		// Play Button
		JButton play = new JButton();
		
		try {
			Image img = ImageIO.read(getClass().getResource("/play_btn.png"));
			play.setIcon(new ImageIcon(img));
			play.setBounds(250, 500, 100, 100);
			play.setOpaque(false);
			play.setContentAreaFilled(false);
			play.setBorderPainted(false);
			
			play.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DashboardServerInterface.Play_Program();
					status.setText("Status: Running");
					
					if (stopped == true) {
						DashboardServerInterface.Close();
		                CustomGUI.on=false;
		                f.dispose();
					}
				}
			});
		} catch (Exception ex) {
			//
		}
	
		// Pause Button
		JButton pause = new JButton();
		
		try {
			Image img = ImageIO.read(getClass().getResource("/pause_btn.png"));
		    pause.setIcon(new ImageIcon(img));
		    pause.setBounds(350, 500, 100, 100);
			pause.setOpaque(false);
			pause.setContentAreaFilled(false);
			pause.setBorderPainted(false);
			
			pause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DashboardServerInterface.Pause_Program();
					status.setText("Status: Paused");
				}
			});
		} catch (Exception ex) {
			
		}
		
		// Stop Button
		JButton stop = new JButton();
		
		try {
			Image img = ImageIO.read(getClass().getResource("/stop_btn.png"));
			stop.setIcon(new ImageIcon(img));
			stop.setBounds(450, 500, 100, 100);
			stop.setOpaque(false);
			stop.setContentAreaFilled(false);
			stop.setBorderPainted(false);
			
			stop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopped = true;
					DashboardServerInterface.Stop_Program();
					status.setText("Status: Stopped");
				}
			});
		} catch (Exception ex) {
			
		}

		// Exit Button
		JButton exit = new JButton();
		
		try {
			Image img = ImageIO.read(getClass().getResource("/exit_btn.png"));
			exit.setIcon(new ImageIcon(img));
			exit.setBounds(0, 500, 100, 100);
			exit.setOpaque(false);
			exit.setContentAreaFilled(false);
			exit.setBorderPainted(false);
			
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DashboardServerInterface.Stop_Program();
					status.setText("Status: Stopped");
					DashboardServerInterface.Close();
	                CustomGUI.on=false;
	                f.dispose();
				}
			});
		} catch (Exception ex) {
			//
		}
		
		// Setup Button
		JButton setup = new JButton("Setup");
		
		try {
			setup.setBounds(10, 200, 180, 25);
			
			setup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Interface.test();
				}
			});
		} catch (Exception ex) {
			//
		}
		

		
		f.add(exit);
		f.add(play);
		f.add(stop);
		f.add(pause);
		f.add(setup);
		f.add(status);
		//f.add(fileName);
		//f.add(load);
		
		f.setVisible(true);
		
		// Set the static variable 'on'
		on = true;
		
		// Trying to pause program when CustomGUI is run but it does not work
		DashboardServerInterface.Pause_Program();
		status.setText("Status: Paused");
	}
}
