package therobotpeople.urcap;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import therobotpeople.urcap.BackgroundPanel;

public class GUIConfigure {

	private static String default_string = "-- new preset --";
	
	// GUIHome can either call GUIConfigure with a preset name or without one

	public static void run(String existing_preset_name) {
		run(existing_preset_name, "Default");
	}

	public static void run(final String existing_preset_name, final String folder) {
		int default_pallet_x = 0;
		int default_pallet_y = 0;
		int default_pallet_z = 0;
		int default_pallet_width = 0;
		int default_pallet_height = 0;
		int default_package_width = 0;
		int default_package_height = 0;
		int default_package_elevation = 0;
		int default_edge_gap = 0;
		int default_box_gap = 0;
		String default_preset_name = ""; 

		if (existing_preset_name != default_string) {
			final FileManipulate saved = new FileManipulate(existing_preset_name, folder);
			
			if(saved.exists()) {
				try {
					default_preset_name 		= existing_preset_name;
					
					default_package_width 		= Integer.parseInt(saved.readLine());
					default_package_height		= Integer.parseInt(saved.readLine());
					default_package_elevation 	= Integer.parseInt(saved.readLine());
					
					default_pallet_width  		= Integer.parseInt(saved.readLine());
					default_pallet_height 		= Integer.parseInt(saved.readLine());
					
					default_edge_gap      		= Integer.parseInt(saved.readLine());
					default_box_gap		  		= Integer.parseInt(saved.readLine());
					
					default_pallet_x	  		= Integer.parseInt(saved.readLine());
					default_pallet_y	  		= Integer.parseInt(saved.readLine());
					default_pallet_z	  		= Integer.parseInt(saved.readLine());

					saved.close();
				} catch (Exception e) {
				
				}
			} else {
				saved.close();
			}
		}
		

		// JFrame Setup
		final JFrame main = new JFrame();
		main.setLayout(null);
		main.setSize(800, 600);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		// Package Dimensions Labels
		JLabel package_width = new JLabel("Package Width");
		package_width.setSize(150,20);
		package_width.setLocation(50, 70);
		main.add(package_width);

		JLabel package_height = new JLabel("Package Height");
		package_height.setSize(150,20);
		package_height.setLocation(50, 100);
		main.add(package_height);
		
		JLabel package_elevation = new JLabel("Package Elevation");
		package_elevation.setSize(150,20);
		package_elevation.setLocation(50, 130);
		main.add(package_elevation);
		
		
		// Package Dimensions Text Fields
		final GuiTextField package_width_text = new GuiTextField();
		package_width_text.setSize(50,20);
		package_width_text.setLocation(200, 70);
		package_width_text.setText(Integer.toString(default_package_width));
		main.add(package_width_text);
		
		final GuiTextField package_height_text = new GuiTextField();
		package_height_text.setSize(50,20);
		package_height_text.setLocation(200, 100);
		package_height_text.setText(Integer.toString(default_package_height));
		main.add(package_height_text);
		
		final GuiTextField package_elevation_text = new GuiTextField();
		package_elevation_text.setSize(50,20);
		package_elevation_text.setLocation(200, 130);
		package_elevation_text.setText(Integer.toString(default_package_elevation));
		main.add(package_elevation_text);
		

		// Pallet Dimensions Labels
		JLabel pallet_width = new JLabel("Pallet Width");
		pallet_width.setSize(150,20);
		pallet_width.setLocation(50,170);
		main.add(pallet_width);

		JLabel pallet_height = new JLabel("Pallet Height");
		pallet_height.setSize(150,20);
		pallet_height.setLocation(50, 200);
		main.add(pallet_height);
		
		
		// Pallet Dimensions Text Fields
		final GuiTextField pallet_width_text = new GuiTextField();
		pallet_width_text.setSize(50,20);
		pallet_width_text.setLocation(200,170);
		pallet_width_text.setText(Integer.toString(default_pallet_width));
		main.add(pallet_width_text);

		final GuiTextField pallet_height_text = new GuiTextField();
		pallet_height_text.setSize(50,20);
		pallet_height_text.setLocation(200, 200);
		pallet_height_text.setText(Integer.toString(default_pallet_height));
		main.add(pallet_height_text);

		
		// Box and Edge Gap Labels
		JLabel edge_gap = new JLabel("Edge Gap");
		edge_gap.setSize(150,20);
		edge_gap.setLocation(50,240);
		main.add(edge_gap);

		JLabel box_gap = new JLabel("Box Gap");
		box_gap.setSize(150,20);
		box_gap.setLocation(50,270);
		main.add(box_gap);

		
		// Box and Edge Gap Text Fields
		final GuiTextField edge_gap_text = new GuiTextField();
		edge_gap_text.setSize(50,20);
		edge_gap_text.setLocation(200,240);
		edge_gap_text.setText(Integer.toString(default_edge_gap));
		main.add(edge_gap_text);
		
		final GuiTextField box_gap_text = new GuiTextField();
		box_gap_text.setSize(50,20);
		box_gap_text.setLocation(200, 270);
		box_gap_text.setText(Integer.toString(default_box_gap));
		main.add(box_gap_text);

		
		// Choose Origin Button
	    JButton chooseOrigin = new JButton("Choose Origin"); 
	    chooseOrigin.setSize(200, 40); 
	    chooseOrigin.setLocation(50, 310);
	    main.add(chooseOrigin); 
	     
	    chooseOrigin.addActionListener(new ActionListener() { 
	      public void actionPerformed(ActionEvent e) { 
	        /* 
	        api.getUserInteraction().getUserDefinedRobotPosition(new RobotPositionCallback() { 
	          @Override 
	          public void onOk(Pose pose, JointPositions jointPositions) { 
	            // Do something with pose and jointPositions 
	          } 
	        }); 
	        */ 
	      } 
	    }); 
	    
		
		// Pallet Position Labels
		JLabel pallet_x_pos = new JLabel("Pallet X Pos");
		pallet_x_pos.setSize(150,20);
		pallet_x_pos.setLocation(50,360);
		main.add(pallet_x_pos);
		
		JLabel pallet_y_pos = new JLabel("Pallet Y Pos");
		pallet_y_pos.setSize(150,20);
		pallet_y_pos.setLocation(50,390);
		main.add(pallet_y_pos);
		
		JLabel pallet_z_pos = new JLabel("Pallet Z Pos");
		pallet_z_pos.setSize(150,20);	
		pallet_z_pos.setLocation(50,420);
		main.add(pallet_z_pos);
		
		
		// Pallet Position Text Fields 
		final GuiTextField pallet_x_text = new GuiTextField();
		pallet_x_text.setSize(50, 20);
		pallet_x_text.setLocation(200,360);
		pallet_x_text.setText(Integer.toString(default_pallet_x));
		main.add(pallet_x_text);
		
		final GuiTextField pallet_y_text = new GuiTextField();
		pallet_y_text.setSize(50, 20);
		pallet_y_text.setLocation(200,390);
		pallet_y_text.setText(Integer.toString(default_pallet_y));
		main.add(pallet_y_text);
		
		final GuiTextField pallet_z_text = new GuiTextField();
		pallet_z_text.setSize(50, 20);
		pallet_z_text.setText(Integer.toString(default_pallet_z));
		pallet_z_text.setLocation(200,420);
		main.add(pallet_z_text);
	    
		
		// Preset Name Label
		JLabel preset_name_label = new JLabel("Preset Name");
		preset_name_label.setSize(100,20);
		preset_name_label.setLocation(50,460);
		
		if (existing_preset_name == default_string) {
		main.add(preset_name_label);
		}
		
		
		// Preset Name Text Field
		final GuiTextField preset_name_text = new GuiTextField();
		preset_name_text.setSize(100, 20);
		preset_name_text.setText(default_preset_name);
		preset_name_text.setLocation(150,460);

		if (existing_preset_name == default_string) {
			main.add(preset_name_text);
		}
		
	    
		// Pallet Button 
		JButton pallet = new JButton("<html><center>Configure<br>Pallet Pattern</center></html>");
		pallet.setSize(200,50);
		pallet.setLocation(50,490);
		main.add(pallet);

		pallet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Get all the information in the text fields		
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
				
				// Store information in file
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
				f.close();
				
				// Update pallet_presets file
				if (existing_preset_name == default_string) {
					FileManipulate pp = new FileManipulate("pallet_presets", folder);
					pp.writeln_append(preset_name);
					pp.close();
				}
	
				
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
					Integer.parseInt(box_gap)
				);

				pallet.run();
				
				main.dispose();
			}
		});
	    
		
		// Cancel Button 
		JButton cancel = new JButton("Cancel");
		cancel.setSize(200, 40);
		cancel.setLocation(50,550);
		main.add(cancel);

		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.dispose();
			}
		});
		
		
		// Create a background and load in a custom image
		BackgroundPanel bg = null;
		try{
			Image img = ImageIO.read(GUIConfigure.class.getResource("/bg_configure.png"));
			bg = new BackgroundPanel(img);
			bg.setBounds(0, 0, 800, 600);
		} catch(Exception ex) {
			//
		}
		main.add(bg);

		
		// Open JFrame
		main.setVisible(true);
	}
}
