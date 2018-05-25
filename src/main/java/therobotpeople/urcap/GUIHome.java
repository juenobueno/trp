package therobotpeople.urcap;

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

import com.ur.urcap.api.domain.variable.Variable;

public class GUIHome implements Runnable {
	public static boolean on = false;

	public GUIHome() {
		
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
		final JLabel status = new JLabel("Status: Choose Preset");
		status.setBounds(10, 75, 180, 25);
		f.add(status);
		
		
		// Dropdown box for Presets: Stored in path "Default/pallet_presets"
		final JComboBox presets = new JComboBox();
		presets.setBounds(10, 150, 180, 25);
		f.add(presets);
		refresh_presets(presets);
		
		
		// Setup Button
		final JButton setup = new JButton("New Preset");
		try {
			setup.setBounds(10, 200, 180, 25);

			setup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new GUIConfigure((String)presets.getSelectedItem());
					f.dispose();
				}
			});
		} catch (Exception ex) {
			//
		}
		f.add(setup);
		
		
		// Delete Button
		final JButton delete = new JButton("Delete Preset");
		try {
			delete.setBounds(10, 250, 180, 25);

			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Delete corresponding file in trp_pallet_presets
					FileManipulate f1 = new FileManipulate((String)presets.getSelectedItem(), FileManipulate.default_pallet_presets_folder);
					f1.delete();
					f1.close();
					
					// Delete corresponding file in trp_waypoints
					FileManipulate f2 = new FileManipulate((String)presets.getSelectedItem(), FileManipulate.default_waypoints_folder);
					f2.delete();
					f2.close();
					
					refresh_presets(presets);
				}
			});
		} catch (Exception ex) {
			//
		}
		f.add(delete);

		
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
			stop.setBounds(10, 375, 180, 50);

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
			exit.setBounds(10, 450, 180, 50);

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
	
	private void refresh_presets(JComboBox presets) {
		presets.removeAllItems();
		presets.addItem("-- new preset --");
	
		FileManipulate presets_folder = new FileManipulate(FileManipulate.default_pallet_presets_folder);
		String[] file_names = presets_folder.get_list_of_files();
		
		for (int i = 0; i < file_names.length; i++) {
			presets.addItem(file_names[i]);
		}
		
		presets_folder.close();
	}
	

}
