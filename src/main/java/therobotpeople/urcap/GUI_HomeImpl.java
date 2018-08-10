package therobotpeople.urcap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

/*
 * GUI_ConfigureImpl implements the functionality of the Configurations GUI. 
 */
public class GUI_HomeImpl extends GUI_HomeLayout {	
	@Override
	public ActionListener pause_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DashboardServerInterface.Pause_Program();
				status.setText("Status: Paused");
			}
		};
		return res;
	}

	@Override
	public ActionListener play_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Restart only if program was not in the middle of something 
				if (status.getText() == "Status: Running") {
					return; 
				} else if (status.getText() == "Status: Paused") {
					DashboardServerInterface.Play_Program();
					status.setText("Status: Running");
				} else if (status.getText() == "Status: Choose Preset") {
					return; 
				} else {
					DashboardServerInterface.Stop_Program();
					DashboardServerInterface.Play_Program();
					status.setText("Status: Running");
				}
			}
		};
		return res;
	}

	@Override
	public ActionListener exit_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DashboardServerInterface.Stop_Program();
				DashboardServerInterface.Close();
				on = false;
				main.dispose();
			}
		};
		return res;
	}

	@Override
	public ActionListener stop_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DashboardServerInterface.Stop_Program();
				status.setText("Status: Stopped");
			}
		};
		return res;
	}

	@Override
	public ItemListener preset_button_listener() {
		ItemListener res = new ItemListener() {
			public void itemStateChanged(ItemEvent event) {

                String selection = event.getItem().toString();

		    	if (!selection.equals("-- new preset --")) {
		    		copy_urscript_to_static_location(selection);
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
		};
		return res;
	}

	void copy_urscript_to_static_location(String pallet_preset) {
		FileManipulate trp_palletiser_script = new FileManipulate("trp_palletiser.script", "../programs");
		FileManipulate pallet_preset_script = new FileManipulate(pallet_preset, FileManipulate.default_scripts_folder);
		
		String currentLine = null;
		
		// Copy the file line by line
		currentLine = pallet_preset_script.readLine();
		while(currentLine != null) {
			trp_palletiser_script.writeln(currentLine);
			currentLine = pallet_preset_script.readLine();
		}
		
		trp_palletiser_script.close();
		pallet_preset_script.close();
	}
	
	@Override
	public ActionListener delete_preset_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Delete corresponding file in trp_pallet_presets
				FileManipulate f1 = new FileManipulate((String)presets.getSelectedItem(), FileManipulate.default_pallet_presets_folder);
				f1.delete();
				f1.close();
				
				// Delete corresponding file in trp_waypoints
				FileManipulate f2 = new FileManipulate((String)presets.getSelectedItem(), FileManipulate.default_waypoints_folder);
				f2.delete();
				f2.close();
				
				// Delete corresponding file in trp_urscripts
				FileManipulate f3 = new FileManipulate((String)presets.getSelectedItem(), FileManipulate.default_scripts_folder);
				f3.delete();
				f3.close();
				
				refresh_presets(presets);
			}
		};
		return res;
	}

	@Override
	public ActionListener new_preset_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GUI_ConfigureImpl(presets.getSelectedItem().toString());
				main.setVisible(false);
			}
		};
		return res;
	}
	
	@Override
	public void refresh_presets(JComboBox presets) {
		presets.removeAllItems();
		presets.addItem("-- new preset --");
	
		FileManipulate presets_folder = new FileManipulate(FileManipulate.default_pallet_presets_folder);
		String[] file_names = presets_folder.get_list_of_files();
		
		for (int i = 0; i < file_names.length; i++) {
			presets.addItem(file_names[i]);
		}
		
		presets.repaint();
		
		presets_folder.close();
		
	}
}
