package therobotpeople.urcap;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class GUI_PalletSetupImpl extends GUI_PalletSetupLayout {
	
	int current_layer = 0;
	
	public GUI_PalletSetupImpl(String pallet_preset) {
		super(pallet_preset);
		loadWaypoints();
		populatePallet();
	}
	
	@Override
	public ActionListener rotate_package_action() {
		ActionListener res = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (is_package_rotated == false) {
					package_normal.setVisible(false);
					package_rotated.setVisible(true);
					is_package_rotated = true;
				} else {
					package_normal.setVisible(true);
					package_rotated.setVisible(false);
					is_package_rotated = false;
				}
			}
		};
		return res;
	}
	

	@Override
	public ActionListener undoListener() {
		ActionListener res = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(button_layout.get(current_layer).size() >= 1) {
					JButton to_remove = button_layout.get(current_layer).get(button_layout.get(current_layer).size() - 1);
					button_layout.get(current_layer).remove(to_remove);
					
					grid.clear(to_remove.getLocation(), to_remove.getSize().width+box_gap, to_remove.getSize().height+box_gap);
					
					pallet.remove(to_remove);
					pallet.revalidate();
					pallet.repaint();
				}
			}
		};
		return res;
	}

	@Override
	public ActionListener doneListener() {
		ActionListener res = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveWaypoints();
				generateURScript(); 
				
				TRPProgramNodeContribution.gui_thread.run();
				main.dispose();
			}
		};
		return res;
	}
	
	private void saveWaypoints() {
		// Open the file that will contain the x, y, rotated of each button 
		FileManipulate waypoints = new FileManipulate(preset_name, FileManipulate.default_waypoints_folder); 
		
		waypoints.write("");
		// Loop over each layer
		for( int i = 0; i < button_layout.size(); i++) {
			
			if (button_layout.get(i).size() == 0) {
				continue;
			}
			
			waypoints.writeln(Integer.toString(i));
			// Loop over each button in each layer
			for( int j = 0; j < button_layout.get(i).size(); j++) {
				JButton current_package = button_layout.get(i).get(j);
				
				waypoints.writeln(Integer.toString(current_package.getLocation().x) + " " +
								  Integer.toString(current_package.getLocation().y) + " " +
								  current_package.getName());
			}
		}
		
		waypoints.close();	
	} 
	
	private void loadWaypoints() {
		// Open the file that will contain the x, y, rotated of each button 
		FileManipulate waypoints = new FileManipulate(preset_name, FileManipulate.default_waypoints_folder); 
		
		String currentLine = null;
		int current_button_tmp = 1;
		int current_layer_tmp = 0;

		// Read the file line by line, populating the arrays as it goes
		currentLine = waypoints.readLine();
		while(currentLine != null) {
			// Check for a new layer (lazy solution of assuming less than 99 layers exist)
			if (currentLine.length() < 3) {
				// If the layer read from current line is greater than 0, another
				// array needs to be added to button_layout
				if (!currentLine.equals("0")) {
					button_layout.add(new ArrayList<JButton>());
					current_layer_tmp++;
					current_button_tmp = 1;
				}
	
				currentLine = waypoints.readLine();
				continue;
			}
			
			
			
			String[] button_info = currentLine.split(" ");
			int x = Integer.parseInt(button_info[0]);
			int y = Integer.parseInt(button_info[1]);
			
			JButton button_tmp = new JButton(Integer.toString(current_button_tmp));

			// Configure the appearance of the button
			if (button_info[2].equals("not")) {
				button_tmp.setName("not rotated");
				button_tmp.setBackground(Color.BLACK);
				button_tmp.setEnabled(false);
				// Size and center the package according to the pallet preset file
				button_tmp.setSize((int)Math.ceil((float)package_width / x_mm_per_px), (int)Math.ceil((float)package_height / y_mm_per_px));
				button_tmp.setLocation(x, y); 
			} else if (button_info[2].equals("rotated")) {
				button_tmp.setName("rotated");
				button_tmp.setBackground(Color.BLACK);
				button_tmp.setEnabled(false);
				// Size and center the package according to the pallet preset file
				button_tmp.setSize((int)Math.ceil((float)package_height / y_mm_per_px), (int)Math.ceil((float)package_width / x_mm_per_px));
				button_tmp.setLocation(x, y); 
			}

			button_layout.get(current_layer_tmp).add(button_tmp);
			
			current_button_tmp++;
			currentLine = waypoints.readLine();
			
		}
		
		waypoints.close();
	}
	
	private void generateURScript() {
		// Create/Open the file that will contain the URScript for the current pallet configuration
		FileManipulate urscript = new FileManipulate(preset_name, FileManipulate.default_scripts_folder);
		
		// Generate the pick up position strings from the pallet preset parameters
		String pick_up_pose = "p[" + Float.toString(pickup_x) + "," 
								   + Float.toString(pickup_y) + "," 
								   + Float.toString(pickup_z) + ", 0, 3.14159, 0]";
		
		String above_pick_up_pose = "p[" + Float.toString(pickup_x) + "," 
								   		 + Float.toString(pickup_y) + "," 
								   		 + Float.toString(pickup_z + (float)(package_elevation * 1.1 / 1000.0f)) + ", 0, 3.14159, 0]";
		
		// Loop over each layer
		for( int i = 0; i< button_layout.size(); i++) {
			
			// Loop over each button in each layer
			for( int j = 0; j < button_layout.get(i).size(); j++) {
				JButton current_package = button_layout.get(i).get(j);
				
				// These three variables tell us how far the centre of the current package is from the origin of the pallet (in metres)
				float horizontal_offset = ((float)current_package.getLocation().x + (float)(current_package.getSize().width / 2)) * x_mm_per_px / 1000.0f;
				float vertical_offset = ((float)current_package.getLocation().y + (float)(current_package.getSize().height / 2)) * y_mm_per_px / 1000.0f;
				float elevation_offset = (float)(i * package_elevation) / 1000.0f; 
				
				String xyz = "";
				String above_xyz = "";
				String rxryrz = "";
				
				// Account for the rotation of the robot
				if (rotation == 0) {
					String x = Float.toString((origin_x) + horizontal_offset); 
					String y = Float.toString((origin_y) - vertical_offset);
					String z = Float.toString((origin_z) + elevation_offset);
					String above_z = Float.toString((origin_z / 1000.0f) + elevation_offset + (float)(package_elevation / 1000.0f));
					
					xyz = x + "," + y + "," + z + ","; 
					above_xyz = x + "," + y + "," + above_z + ","; 
				} else if (rotation == 1) {
					String x = Float.toString((origin_x) + vertical_offset); 
					String y = Float.toString((origin_y) + horizontal_offset);
					String z = Float.toString((origin_z) + elevation_offset);
					String above_z = Float.toString((origin_z) + elevation_offset + (float)(package_elevation / 1000.0f));
					
					xyz = x + "," + y + "," + z + ","; 
					above_xyz = x + "," + y + "," + above_z + ","; 
				} else if (rotation == 2) {
					String x = Float.toString((origin_x) - horizontal_offset); 
					String y = Float.toString((origin_y) + vertical_offset);
					String z = Float.toString((origin_z) + elevation_offset);
					String above_z = Float.toString((origin_z) + elevation_offset + (float)(package_elevation / 1000.0f));
					
					xyz = x + "," + y + "," + z + ","; 
					above_xyz = x + "," + y + "," + above_z + ","; 
				} else if (rotation == 3) {
					String x = Float.toString((origin_x) - vertical_offset); 
					String y = Float.toString((origin_y) - horizontal_offset);
					String z = Float.toString((origin_z) + elevation_offset);
					String above_z = Float.toString((origin_z) + elevation_offset + (float)(package_elevation / 1000.0f));
					
					xyz = x + "," + y + "," + z + ","; 
					above_xyz = x + "," + y + "," + above_z + ","; 
				}
				
				// Account for the rotation of the package
				if (current_package.getName() == "not rotated") {
					rxryrz = "0, 3.14159, 0"; 
				} else if (current_package.getName() == "rotated") {
					rxryrz = "2.222, 2.222, 0";
				}
				
				// Finally generate the package position strings 
				String package_pose = "p[" + xyz + rxryrz + "]";
				String above_package_pose = "p[" + above_xyz + rxryrz + "]";
				
				// Perform the package pick and place 
				urscript.writeln("movej(" + above_pick_up_pose + ")");
				urscript.writeln("movel(" + pick_up_pose + ")");
				urscript.writeln("movel(" + above_pick_up_pose + ")");
				urscript.writeln("movej(" + above_package_pose + ")");
				urscript.writeln("movel(" + package_pose + ")");
				urscript.writeln("movel(" + above_package_pose + ")");
			}
		}
		
		urscript.close();
	}
	
	@Override
	public MouseAdapter palletListener() {
		MouseAdapter res = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point position = e.getPoint();
				
				JButton pack = new JButton(Integer.toString(button_layout.get(current_layer).size() + 1));

				pack.setBackground(Color.BLACK);
				pack.setEnabled(false);
				// Size and center the package according to the pallet preset file


				int x_pos, y_pos, pack_width, pack_height;
				
				if (is_package_rotated == false) {
					pack_width = (int)Math.ceil((float)package_width / x_mm_per_px);
					pack_height = (int)Math.ceil((float)package_height / y_mm_per_px);
					pack.setSize((int)Math.ceil((float)package_width / x_mm_per_px), (int)Math.ceil((float)package_height / y_mm_per_px));
					pack.setName("not rotated");
				} else {
					pack_width = (int)Math.ceil((float)package_height / y_mm_per_px);
					pack_height = (int)Math.ceil((float)package_width / x_mm_per_px);
					pack.setSize((int)Math.ceil((float)package_height / y_mm_per_px), (int)Math.ceil((float)package_width / x_mm_per_px));
					pack.setName("rotated");
				}
				
				pack_width = pack_width+box_gap;
				pack_height = pack_height+box_gap;
				
				x_pos = position.x;
				y_pos = position.y;
				
				int left_distance = grid.left_limit(position, pack_width, pack_height);
				int right_distance= grid.right_limit(position, pack_width, pack_height);

				if( left_distance == position.x) {
					if( right_distance+pack_width+position.x == 1000) {
						x_pos = x_pos - x_pos%pack_width;
					}else {
						x_pos = x_pos + (right_distance)%pack_width+1;
					}
				}else {
					if( left_distance == 0 && right_distance == 0){
						x_pos = x_pos - x_pos%pack_width + edge_gap_x;
					}else {
						x_pos = x_pos - left_distance%pack_width;
					}
				}
				
				int top_distance = grid.top_limit(position, pack_width, pack_height);
				int bottom_distance = grid.bottom_limit(position, pack_width, pack_height);

				if( top_distance == position.y) {
					if( bottom_distance+pack_height+position.y == 1000) {
						y_pos = y_pos - y_pos%pack_height;
					}else {
						y_pos = y_pos+(bottom_distance)%pack_height+1;
					}
				}else {
					if( top_distance == 0 && bottom_distance == 0) {
						y_pos = y_pos - y_pos%pack_height+edge_gap_y;
					}else {
						y_pos = y_pos - top_distance%pack_height;
					}
				}
				
				pack.setLocation(x_pos, y_pos);
				
				if(grid.set_Object(new Point(pack.getLocation().x, pack.getLocation().y), pack_width, pack_height)) {
					pack.setVisible(true);
					//palletized.add(pack);
					
					button_layout.get(current_layer).add(pack);
					
					pallet.add(pack);
					main.repaint();
				}
			}
		};
		return res;
	}


	

	@Override
	public ActionListener layerUpListener() {
		ActionListener res = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				unpopulatePallet();
				
				// Increment the layer number
				current_layer++; 
				
				// Increase the size of the button_layout array if necessary
				if (button_layout.size() < current_layer + 1) {
					button_layout.add(new ArrayList<JButton>());
				}

				// Update the Layer label
				layer_text.setText("Layer: " + Integer.toString(current_layer));
				
				// Update the pallet
				populatePallet();
			}
		};
		return res;
	}

	@Override
	public ActionListener layerDownListener() {
		ActionListener res = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Check if current layer is 0, if so do nothing
				if (current_layer == 0) {
					return; 
				}
				
				unpopulatePallet();
				
				// Decrement the layer
				current_layer--;

				// Update the Layer label
				layer_text.setText("Layer: " + Integer.toString(current_layer));
				
				// Update the pallet
				populatePallet();
			}
		};
		return res;
	}

	void unpopulatePallet() {
		//JOptionPane.showMessageDialog(main, "UNPOPULATE: " + Integer.toString(button_layout.get(current_layer).size()));
		for (int i = 0; i < button_layout.get(current_layer).size(); i++) {
			JButton to_remove_from_grid = button_layout.get(current_layer).get(i);
			grid.clear(to_remove_from_grid.getLocation(), to_remove_from_grid.getSize().width + box_gap, to_remove_from_grid.getSize().height+box_gap);
		}
		
		pallet.removeAll();
	}
	
	void populatePallet() {
		//JOptionPane.showMessageDialog(main, "POPULATE: " + Integer.toString(button_layout.get(current_layer).size()));
		for (int i = 0; i < button_layout.get(current_layer).size(); i++) {
			JButton to_be_added = button_layout.get(current_layer).get(i);
			pallet.add(to_be_added);
			
			grid.set_Object(to_be_added.getLocation(), to_be_added.getSize().width + box_gap, to_be_added.getSize().height + box_gap);
		}
		
		pallet.repaint();
	}
}
