package therobotpeople.urcap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.ur.urcap.api.domain.userinteraction.RobotPositionCallback;
import com.ur.urcap.api.domain.value.Pose;
import com.ur.urcap.api.domain.value.jointposition.JointPositions;

public class Configure_Impl extends Configure_GUI{

	
	public Configure_Impl(String preset) {
		run(preset);
	}
	
	@Override
	public ActionListener cancel_button_action() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionListener rotate_robot_button_action() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionListener configure_pallet_button_action(final String existing_preset_name, final String folder) {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Check for empty preset name text field
				if (preset_name_text.getText().equals("")) {
					JOptionPane.showMessageDialog(main, "Please Input a Preset Name");
					return;
				}
			
				// Check for no duplicate preset names when making new preset
				//existing preset name is passed in via parameter
				if (existing_preset_name == FileManipulate.default_pallet_preset) {
					FileManipulate presets_folder = new FileManipulate("trp_pallet_presets");
					String[] file_names = presets_folder.get_list_of_files();
					
					for (int i = 0; i < file_names.length; i++) {
						if (preset_name_text.getText().equals(file_names[i])) {
							JOptionPane.showMessageDialog(main, "Preset Already Exists");  
							return; 
						}
					}
				}
				
				// Check for no duplicate preset names when editing preset name
				if (existing_preset_name != FileManipulate.default_pallet_preset) {
					FileManipulate presets_folder = new FileManipulate("trp_pallet_presets");
					String[] file_names = presets_folder.get_list_of_files();
					
					for (int i = 0; i < file_names.length; i++) {
						if (preset_name_text.getText().equals(existing_preset_name) == true) {
							continue; 
						}
						
						if (preset_name_text.getText().equals(file_names[i])) {
							JOptionPane.showMessageDialog(main, "Preset Already Exists");  
							return; 
						}
					}
				}
				
				// Check if preset name was edited, if so delete the old pallet_preset file and rename the waypoints file
				if (existing_preset_name != FileManipulate.default_pallet_preset) {
					if (preset_name_text.getText().equals(existing_preset_name) == false) {
						FileManipulate f = new FileManipulate(existing_preset_name, folder);
						f.delete();
						f.close();
						
						FileManipulate old_waypoints = new FileManipulate(existing_preset_name, FileManipulate.default_waypoints_folder);
						FileManipulate new_waypoints = new FileManipulate(preset_name_text.getText(), FileManipulate.default_waypoints_folder);
						old_waypoints.rename(new_waypoints);
						old_waypoints.close();
					}
				}
				
				// Get all the inf				ormation in the text fields		
				String package_height = package_height_text.getText();
				String package_width = package_width_text.getText();
				String package_elevation = package_elevation_text.getText();
				String pallet_width = pallet_width_text.getText();
				String pallet_height = pallet_height_text.getText();
				String edge_gap = edge_gap_text.getText();
				String box_gap = box_gap_text.getText();
				String pallet_x = pallet_x_text.getText();
				String pallet_y = pallet_y_text.getText();
				String pallet_z = pallet_z_text.getText();
				String preset_name = preset_name_text.getText();
				//Uses an invisible text field to store the robots rotation
				//String rotation = rotation_text.getText();
				String rotation = "0";
				
				// Store information in file
				//folder is passed in as a variable for the runnable call
				
				FileManipulate f = new FileManipulate(preset_name, folder);
				

				f.writeln(package_width);
				f.writeln(package_height);
				f.writeln(package_elevation);
			
				f.writeln(pallet_width);
				f.writeln(pallet_height);
				
				f.writeln(edge_gap);
				f.writeln(box_gap);
				
				f.writeln(pallet_x);
				f.writeln(pallet_y);
				f.writeln(pallet_z);
				
				f.writeln(rotation);
				f.close();
				
				//Need to call the interface from here with all the variables I need
				GUIPalletSetup pallet = new GUIPalletSetup(
					Integer.parseInt(pallet_x),
					Integer.parseInt(pallet_y),
					Integer.parseInt(pallet_z),
					Integer.parseInt(pallet_width),
					Integer.parseInt(pallet_height),
					Integer.parseInt(package_width),
					Integer.parseInt(package_height),
					Integer.parseInt(edge_gap),
					Integer.parseInt(box_gap),
					Integer.parseInt(rotation),
					FileManipulate.default_waypoints_folder,
					preset_name
				);

				pallet.run();
				
				main.dispose();
			}
		};
		return res;
	}

	@Override
	public ActionListener choose_origin_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				main.setVisible(false);
				
				TRPProgramNodeContribution.api.getUserInteraction().getUserDefinedRobotPosition(new RobotPositionCallback() { 
					@SuppressWarnings("deprecation")
					@Override 
					public void onOk(Pose pose, JointPositions jointPositions) { 
						pallet_x_text.setText(String.format("%d", (int)(pose.getPosition().getX() * 1000)));
						pallet_y_text.setText(String.format("%d", (int)(pose.getPosition().getY() * 1000)));
						pallet_z_text.setText(String.format("%d", (int)(pose.getPosition().getZ() * 1000)));
						
						main.setVisible(true);
					} 
				});
			} 
		};
		return res;
	}

	

}
