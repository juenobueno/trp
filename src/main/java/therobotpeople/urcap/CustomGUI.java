package therobotpeople.urcap;

import com.ur.urcap.api.domain.script.ScriptWriter;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class CustomGUI implements Runnable {
	public static boolean on = false;
	public static ScriptWriter writer;

	public CustomGUI(ScriptWriter w) {
		writer = w;
	}

	public void run() {
		//Open Communication to the DashboardServer for play, pause and stop control
		DashboardServerInterface.Open();

		// Create the JFrame that the UI will be held in
		final JFrame f = new JFrame();
		f.setLayout(null);
		f.setSize(802, 630);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create a background and load in a custom image
		BackgroundPanel bg = null;
		try{
			Image img = ImageIO.read(getClass().getResource("/bg.png"));
			bg = new BackgroundPanel(img);
			bg.setBounds(0, 0, 802, 630);
		} catch(Exception ex) {
			//
		}

		// Status Text Box
		final JTextField status = new JTextField();
		status.setBounds(11, 150, 180, 25);
		status.setOpaque(false);
		
		// Play Button
		JButton play = new JButton();
		try {
			Image img = ImageIO.read(getClass().getResource("/play_btn.png"));
			play.setIcon(new ImageIcon(img));
			play.setBounds(0, 500, 100, 100);
			play.setOpaque(false);
			play.setContentAreaFilled(false);
			play.setBorderPainted(false);

			play.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DashboardServerInterface.Play_Program();
					status.setText("Status: Running");
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
		    pause.setBounds(100, 500, 100, 100);
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

		// Setup Button
		JButton setup = new JButton("Setup");
		try {
			setup.setBounds(11, 200, 180, 25);

			setup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Precursor.run();
					GUIConfigure.run();
				}
			});
		} catch (Exception ex) {
			//
		}

		// Stop Button
		JButton stop = new JButton("<html>Stop and Go<br>Back To Start</html>");
		try {
			stop.setBounds(11, 300, 180, 50);

			stop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DashboardServerInterface.Stop_Program();
					status.setText("Status: Stopped");
				}
			});
		} catch (Exception ex) {
			//
		}

		// Exit Button
		JButton exit = new JButton("<html>Stop and Exit<br>to Polyscope</html>");
		try {
			exit.setBounds(11, 400, 180, 50);

			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DashboardServerInterface.Stop_Program();
					DashboardServerInterface.Close();
	                CustomGUI.on=false;
	                f.dispose();
				}
			});
		} catch (Exception ex) {
			//
		}

		double pallet_width = 1.5; // metres
		double pallet_length = 2; // metres

		//Pallet Preview
		JPanel preview = new JPanel();
		preview.setBackground(Color.ORANGE);
		preview.setLayout(null);

		if (pallet_width == pallet_length) {
			int x = (int) (202 + (600 - 550) / 2);
			int y = (int) ((600 - 550) / 2);
			preview.setBounds(x, y, 550, 550);
		} else if (pallet_width > pallet_length) {
			int h = (int) (pallet_length / pallet_width * 550);
			int x = (int) (202 + (600 - 550) / 2);
			int y = (int) ((600 - h) / 2);
			preview.setBounds(x, y, 550, h);
		} else if (pallet_width < pallet_length) {
			int w = (int) (pallet_width / pallet_length * 550);
			int x = (int) (202 + (600 - w) / 2);
			int y = (int) ((600 - 550) / 2);
			preview.setBounds(x, y, w, 550);
		}
		
		//Creating a load file button/preset
		//Has a file called default which stores all the file names
		//A drop down box readers out of default, re-read on drop down?
		//Load button to load the script file
		
		final FileManipulate file = new FileManipulate("default");
		final JComboBox<String> drop_down = new JComboBox<String>();
		drop_down.setBounds(0, 0, 100, 50 );
		String options;
		while((options = file.readLine()) != null) {
			drop_down.addItem(options);
		}
		file.close();
		drop_down.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				drop_down.removeAllItems();
				String opt;
				while((opt = file.readLine()) != null) {
					drop_down.addItem(opt);
				}
				file.close();
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {}
		});
		

		f.add(drop_down);
		f.add(exit);
		f.add(stop);
		f.add(play);
		f.add(pause);
		f.add(setup);
		f.add(status);
		f.add(preview);
		f.add(bg);

		f.setVisible(true);

		// Trying to pause program when CustomGUI is run but it does not work
		status.setText("Status: Setup Required");

		// Set the on variable to true
		on = true;
	}
}
