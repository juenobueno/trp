package therobotpeople.urcap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

public class Home_Impl extends Home_GUI implements Runnable {

	
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
				DashboardServerInterface.Play_Program();
				status.setText("Status: Running");
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
				
				refresh_presets(presets);
			}
		};
		return res;
	}

	@Override
	public ActionListener new_preset_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GUIConfigure((String)presets.getSelectedItem());
				main.dispose();
			}
		};
		return res;
	}
	
	private void refresh_presets(JComboBox<String> presets) {
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
