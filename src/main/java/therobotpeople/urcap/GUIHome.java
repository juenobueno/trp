package therobotpeople.urcap;

import com.ur.urcap.api.domain.URCapAPI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class GUIHome implements Runnable {
	public static boolean on = false;
	private final URCapAPI api;

	public GUIHome(URCapAPI api) {
		this.api = api;
	}

	public void run() {
		// Open Communication to the DashboardServer for play, pause and stop control
		DashboardServerInterface.Open();

		
		// JFrame Setup
		final JFrame f = new JFrame();
		f.setLayout(null);
		f.setSize(800, 600);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		// Status Label
		final JLabel status = new JLabel();
		status.setBounds(11, 75, 180, 25);
		status.setText("Status: Choose Preset");
		f.add(status);
		
		
		// Dropdown box for Presets
		// Presets stored in Default/pallet_presets
		final JComboBox presets = new JComboBox();
		presets.setBounds(11, 150, 180, 25);
		f.add(presets);
		
		// Populate presets
		presets.addItem("-- new preset --");
		final FileManipulate file = new FileManipulate("pallet_presets", "Default");
		String options;
		while((options = file.readLine()) != null) {
			presets.addItem(options);
		}
		file.close();
		
		presets.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				presets.removeAllItems();
				
				presets.addItem("-- new preset --");
				
				String opt;
				while((opt = file.readLine()) != null) {
					presets.addItem(opt);
				}
				file.close();
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {}
		});
		
		
		// Setup Button
		final JButton setup = new JButton("Edit Preset");
		try {
			setup.setBounds(11, 200, 180, 25);

			setup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GUIConfigure.run((String)presets.getSelectedItem());
				}
			});
		} catch (Exception ex) {
			//
		}
		f.add(setup);
		
	
		// Listening for selection on drop down box
		presets.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                //JComboBox comboBox = (JComboBox) event.getSource();

                String selection = event.getItem().toString();

		    	if (!selection.equals("-- new preset --")) {
		    		String buffer = "Status: ";
		    		buffer += selection; 
		    		buffer += " Ready";
		    		
		    		status.setText(buffer);
		    		setup.setText("Edit Preset");
		        } else {
		        	status.setText("Status: Choose Preset");
		        	setup.setText("New Preset");
		        }
            }
	    });
		
		
		// Stop Button
		JButton stop = new JButton("<html>Stop and Go<br>Back To Start</html>");
		try {
			stop.setBounds(11, 375, 180, 50);

			stop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DashboardServerInterface.Stop_Program();
					status.setText("Status: Stopped");
				}
			});
		} catch (Exception ex) {
			//
		}
		f.add(stop);

		
		// Exit Button
		JButton exit = new JButton("<html>Stop and Exit<br>to Polyscope</html>");
		try {
			exit.setBounds(11, 450, 180, 50);

			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DashboardServerInterface.Stop_Program();
					DashboardServerInterface.Close();
	                GUIHome.on=false;
	                f.dispose();
				}
			});
		} catch (Exception ex) {
			//
		}
		f.add(exit);
		
		
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
		f.add(play);

		
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
		f.add(pause);

		
		//Pallet Preview
		double pallet_width = 1.5; // metres
		double pallet_length = 2; // metres

		JPanel preview = new JPanel();
		preview.setBackground(Color.WHITE);
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

		f.add(preview);
		
		
		// Create a background and load in a custom image
		BackgroundPanel bg = null;
		try{
			Image img = ImageIO.read(getClass().getResource("/bg_home.png"));
			bg = new BackgroundPanel(img);
			bg.setBounds(0, 0, 800, 600);
		} catch(Exception ex) {
			//
		}
		f.add(bg);

		// Open the JFrame
		f.setVisible(true);

		// Set the on variable to true
		on = true;
	}
}
