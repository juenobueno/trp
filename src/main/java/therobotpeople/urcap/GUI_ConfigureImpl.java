package therobotpeople.urcap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.ur.urcap.api.domain.userinteraction.RobotPositionCallback;
import com.ur.urcap.api.domain.value.Pose;
import com.ur.urcap.api.domain.value.jointposition.JointPositions;

/*
 * GUI_ConfigureImpl implements the functionality of the Configurations GUI. 
 */
public class GUI_ConfigureImpl extends GUI_ConfigureLayout{

	public GUI_ConfigureImpl(String existing_preset_name) {
		super(existing_preset_name);
	}
	
	@Override
	public void load_default_values_from_file(String existing_preset_name) {
		default_preset_name = existing_preset_name;
		
		// Read values from file to update the corresponding instance variables
		if (existing_preset_name != FileManipulate.default_pallet_preset) {
			FileManipulate saved = new FileManipulate(existing_preset_name, FileManipulate.default_pallet_presets_folder);
			
			if (saved.exists()) {
				try {
					default_package_width 		= Integer.parseInt(saved.readLine());
					default_package_height		= Integer.parseInt(saved.readLine());
					default_package_elevation 	= Integer.parseInt(saved.readLine());
					default_pallet_width  		= Integer.parseInt(saved.readLine());
					default_pallet_height 		= Integer.parseInt(saved.readLine());
					default_edge_gap      		= Integer.parseInt(saved.readLine());
					default_box_gap		  		= Integer.parseInt(saved.readLine());
					rotation			        = Integer.parseInt(saved.readLine());
					origin_x	  				= Float.parseFloat(saved.readLine());
					origin_y	  				= Float.parseFloat(saved.readLine());
					origin_z	  				= Float.parseFloat(saved.readLine());
					pickup_x	  				= Float.parseFloat(saved.readLine());
					pickup_y	  				= Float.parseFloat(saved.readLine());
					pickup_z	  				= Float.parseFloat(saved.readLine());
					
					saved.close();
				} catch (Exception e) {
				
				}
			} else {
				saved.close();
			}
		}
		
		// Update the GUI text fields using the parameters and the robot rotation image
		package_width_text.setText(Integer.toString(default_package_width));
		package_height_text.setText(Integer.toString(default_package_height));
		package_elevation_text.setText(Integer.toString(default_package_elevation));
		pallet_width_text.setText(Integer.toString(default_pallet_width));
		pallet_height_text.setText(Integer.toString(default_pallet_height));
		edge_gap_text.setText(Integer.toString(default_edge_gap));
		box_gap_text.setText(Integer.toString(default_box_gap));
		
		
		if (rotation == 0) {
			rotate_robot_image.changeImage("rot_0.png");
		} else if (rotation == 1) {
			rotate_robot_image.changeImage("rot_1.png");
		} else if (rotation == 2) {
			rotate_robot_image.changeImage("rot_2.png");
		} else if (rotation == 3) {
			rotate_robot_image.changeImage("rot_3.png");
		}
		
		if (default_preset_name != FileManipulate.default_pallet_preset) {
			preset_name_text.setText(default_preset_name);
		}
		
	}
	
	@Override
	public ActionListener rotate_robot_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rotation = (rotation + 1) % 4;
				
				if (rotation == 0) {
					rotate_robot_image.changeImage("rot_0.png");
				} else if (rotation == 1) {
					rotate_robot_image.changeImage("rot_1.png");
				} else if (rotation == 2) {
					rotate_robot_image.changeImage("rot_2.png");
				} else if (rotation == 3) {
					rotate_robot_image.changeImage("rot_3.png");
				}
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
						origin_x = (float)(pose.getPosition().getX()); // Multiplied by 1000 as URScript returns values in metres
						origin_y = (float)(pose.getPosition().getY()); // Multiplied by 1000 as URScript returns values in metres
						origin_z = (float)(pose.getPosition().getZ()); // Multiplied by 1000 as URScript returns values in metres
						
						main.setVisible(true);
						JOptionPane.showMessageDialog(main, "Origin Point has been saved");
					}
					
					@Override
					public void onCancel() {
						main.setVisible(true);
						JOptionPane.showMessageDialog(main, "Origin Point has NOT been saved");
					}
				});
			} 
		};
		return res;
	}


	@Override
	public ActionListener choose_pickup_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				main.setVisible(false);
				
				TRPProgramNodeContribution.api.getUserInteraction().getUserDefinedRobotPosition(new RobotPositionCallback() { 
					@SuppressWarnings("deprecation")
					@Override 
					public void onOk(Pose pose, JointPositions jointPositions) { 
						pickup_x = (float)(pose.getPosition().getX()); // Multiplied by 1000 as URScript returns values in metres
						pickup_y = (float)(pose.getPosition().getY()); // Multiplied by 1000 as URScript returns values in metres
						pickup_z = (float)(pose.getPosition().getZ()); // Multiplied by 1000 as URScript returns values in metres
						
						main.setVisible(true);
						JOptionPane.showMessageDialog(main, "Pickup Point has been saved");
					} 
					
					@Override
					public void onCancel() {
						main.setVisible(true);
						JOptionPane.showMessageDialog(main, "Pickup Point has NOT been saved");
					}
				});
			} 
		};
		return res;
	}

	@Override
	public ActionListener configure_pallet_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Check for empty preset name text field
				if (preset_name_text.getText().equals("")) {
					JOptionPane.showMessageDialog(main, "Please Input a Preset Name");
					return;
				}
			
				// Check for no duplicate preset names when making new preset.
				if (default_preset_name == FileManipulate.default_pallet_preset) {
					FileManipulate presets_folder = new FileManipulate("trp_pallet_presets");
					String[] file_names = presets_folder.get_list_of_files();
					
					for (int i = 0; i < file_names.length; i++) {
						if (preset_name_text.getText().equals(file_names[i])) {
							JOptionPane.showMessageDialog(main, "Preset Already Exists");  
							return; 
						}
					}
				}
				
				// Check for no duplicate preset names when editing preset namex
				if (default_preset_name != FileManipulate.default_pallet_preset) {
					FileManipulate presets_folder = new FileManipulate("trp_pallet_presets");
					String[] file_names = presets_folder.get_list_of_files();
					
					for (int i = 0; i < file_names.length; i++) {
						if (preset_name_text.getText().equals(default_preset_name) == true) {
							continue; 
						}
						
						if (preset_name_text.getText().equals(file_names[i])) {
							JOptionPane.showMessageDialog(main, "Preset Already Exists");  
							return; 
						}
					}
				}
				
				// Check if preset name was edited, if so delete the old pallet_preset file and rename the waypoints file and the script file
				if (default_preset_name != FileManipulate.default_pallet_preset) {
					if (preset_name_text.getText().equals(default_preset_name) == false) {
						FileManipulate f = new FileManipulate(default_preset_name, FileManipulate.default_pallet_presets_folder);
						f.delete();
						f.close();
						
						FileManipulate old_waypoints = new FileManipulate(default_preset_name, FileManipulate.default_waypoints_folder);
						FileManipulate new_waypoints = new FileManipulate(preset_name_text.getText(), FileManipulate.default_waypoints_folder);
						old_waypoints.rename(new_waypoints);
						old_waypoints.close();
						
						FileManipulate old_script = new FileManipulate(default_preset_name, FileManipulate.default_scripts_folder);
						FileManipulate new_script = new FileManipulate(preset_name_text.getText(), FileManipulate.default_scripts_folder);
						old_script.rename(new_script);
						old_waypoints.close();
					}
				}
				
				// Write all the parameters into a text file using the user's given pallet preset name
				// The order of these writes is in the exact same order of the reads at the top. 
				FileManipulate pallet_preset_file = new FileManipulate(preset_name_text.getText(), FileManipulate.default_pallet_presets_folder);
				
				pallet_preset_file.writeln(package_width_text.getText());
				pallet_preset_file.writeln(package_height_text.getText());
				pallet_preset_file.writeln(package_elevation_text.getText());
				pallet_preset_file.writeln(pallet_width_text.getText());
				pallet_preset_file.writeln(pallet_height_text.getText());
				pallet_preset_file.writeln(edge_gap_text.getText());
				pallet_preset_file.writeln(box_gap_text.getText());
				pallet_preset_file.writeln(rotation.toString());
				pallet_preset_file.writeln(origin_x.toString());
				pallet_preset_file.writeln(origin_y.toString());
				pallet_preset_file.writeln(origin_z.toString());
				pallet_preset_file.writeln(pickup_x.toString());
				pallet_preset_file.writeln(pickup_y.toString());
				pallet_preset_file.writeln(pickup_z.toString());
				
				pallet_preset_file.close();
				
				// Close the Configure page and open the Pallet Setup Page.
				// The name of the current pallet preset is passed in as a parameter.
				new GUI_PalletSetupImpl(preset_name_text.getText());
				main.dispose();
			}
		};
		return res;
	}

	@Override
	public ActionListener cancel_button_action() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TRPProgramNodeContribution.gui_thread.run();
				main.dispose();
			}
		};
		return res;
	}
}
