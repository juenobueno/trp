package therobotpeople.urcap;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.ur.urcap.api.domain.userinteraction.RobotPositionCallback;
import com.ur.urcap.api.domain.value.Pose;
import com.ur.urcap.api.domain.value.jointposition.JointPositions;
import com.ur.urcap.api.domain.URCapAPI;

public class GUIConfigure {

	public static void run(final URCapAPI api) {
		run(api, "Default");
	}
	
	public static void run(final URCapAPI api, String folder) {
		int default_pallet_x = 0;
		int default_pallet_y = 0;
		int default_pallet_z = 0;
		int default_pallet_width = 400;
		int default_pallet_height = 400;
		int default_package_width = 50;
		int default_package_height = 100;
		int default_edge_gap = 0;
		int default_box_gap = 0;
		
		final FileManipulate saved = new FileManipulate("PalletConfiguration", folder);
		if( saved.exists()) {
		
			try {
				
				default_pallet_x	  = Integer.parseInt(saved.readLine());
				default_pallet_y	  = Integer.parseInt(saved.readLine());
				default_pallet_z	  = Integer.parseInt(saved.readLine());
				default_pallet_width  = Integer.parseInt(saved.readLine());
				default_pallet_height = Integer.parseInt(saved.readLine());
				default_package_width = Integer.parseInt(saved.readLine());
				default_package_height= Integer.parseInt(saved.readLine());
				default_edge_gap      = Integer.parseInt(saved.readLine());
				default_box_gap		  = Integer.parseInt(saved.readLine());
				
				saved.close();
			} catch (Exception e) {
			}
			
			//read in data
		}else {
			//Save the data once so its in there, then save it at the end
			//for any modifications
		}
		
		final JFrame main = new JFrame();
		main.setLayout(null);
		main.setSize(800, 600);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel package_width = new JLabel("Package Width");
		package_width.setSize(100, 20);
		package_width.setLocation(50, 100);
		main.add(package_width);
		
		final JTextField package_width_text = new JTextField();
		package_width_text.setSize(50,20);
		package_width_text.setLocation(150, 100);
		package_width_text.setText(Integer.toString(default_package_width));
		main.add(package_width_text);
		
		JLabel package_height = new JLabel("Package Height");
		package_height.setSize(100,20);
		package_height.setLocation(50, 130);
		main.add(package_height);
		
		final JTextField package_height_text = new JTextField();
		package_height_text.setSize(50,20);
		package_height_text.setLocation(150, 130);
		package_height_text.setText(Integer.toString(default_package_height));
		main.add(package_height_text);
		
		JLabel pallet_width = new JLabel("Pallet Width");
		pallet_width.setSize(100,20);
		pallet_width.setLocation(50,160);
		main.add(pallet_width);
		
		final JTextField pallet_width_text = new JTextField();
		pallet_width_text.setSize(50,20);
		pallet_width_text.setLocation(150,160);
		pallet_width_text.setText(Integer.toString(default_pallet_width));
		main.add(pallet_width_text);
		
		JLabel pallet_height = new JLabel("Pallet Height");
		pallet_height.setSize(100,20);
		pallet_height.setLocation(50, 190);
		main.add(pallet_height);
		
		final JTextField pallet_height_text = new JTextField();
		pallet_height_text.setSize(50,20);
		pallet_height_text.setLocation(150, 190);
		pallet_height_text.setText(Integer.toString(default_pallet_height));
		main.add(pallet_height_text);
		
		JLabel edge_gap = new JLabel("Edge Gap");
		edge_gap.setSize(100,20);
		edge_gap.setLocation(50,220);
		main.add(edge_gap);
		
		final JTextField edge_gap_text = new JTextField();
		edge_gap_text.setSize(50,20);
		edge_gap_text.setLocation(150,220);
		edge_gap_text.setText(Integer.toString(default_edge_gap));
		main.add(edge_gap_text);
		
		JLabel box_gap = new JLabel("Box Gap");
		box_gap.setSize(100,20);
		box_gap.setLocation(50, 250);
		main.add(box_gap);
		
		final JTextField box_gap_text = new JTextField();
		box_gap_text.setSize(50,20);
		box_gap_text.setLocation(150, 250);
		box_gap_text.setText(Integer.toString(default_box_gap));
		main.add(box_gap_text);
		
		JLabel pallet_x_pos = new JLabel("Pallet X_Pos");
		JLabel pallet_y_pos = new JLabel("Pallet Y_Pos");
		JLabel pallet_z_pos = new JLabel("Pallet Z Pos");
		pallet_x_pos.setSize(100,20);
		pallet_y_pos.setSize(100,20);;
		pallet_z_pos.setSize(100,20);
		pallet_x_pos.setLocation(50,300);
		pallet_y_pos.setLocation(50,330);
		pallet_z_pos.setLocation(50,360);
		main.add(pallet_x_pos);
		main.add(pallet_y_pos);
		main.add(pallet_z_pos);

		final JTextField pallet_x_text = new JTextField();
		final JTextField pallet_y_text = new JTextField();
		final JTextField pallet_z_text = new JTextField();
		pallet_x_text.setSize(50, 20);
		pallet_y_text.setSize(50, 20);
		pallet_z_text.setSize(50, 20);
		pallet_x_text.setLocation(150, 300);
		pallet_y_text.setLocation(150, 330);
		pallet_z_text.setLocation(150, 360);
		pallet_x_text.setText(Integer.toString(default_pallet_x));
		pallet_y_text.setText(Integer.toString(default_pallet_y));
		pallet_z_text.setText(Integer.toString(default_pallet_z));
		main.add(pallet_x_text);
		main.add(pallet_y_text);
		main.add(pallet_z_text);
		
		JButton close = new JButton("Cancel");
		close.setSize(100,50);
		close.setLocation(50,500);
		main.add(close);
		
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.dispose();
			}
			
		});
		
		JButton chooseOrigin = new JButton("Choose Origin");
		chooseOrigin.setSize(100, 50);
		chooseOrigin.setLocation(700, 550);
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
		
		

		
		JButton pallet = new JButton("Pallet");
		pallet.setSize(100,50);
		pallet.setLocation(300,100);
		main.add(pallet);
		
		pallet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Need to call the interface from here with all the variables I need
				
				String package_height = package_height_text.getText();
				String package_width = package_width_text.getText();
				String pallet_width = pallet_width_text.getText();
				String pallet_height = pallet_height_text.getText();
				String edge_gap = edge_gap_text.getText();
				String box_gap = box_gap_text.getText();
				String pallet_x = pallet_x_text.getText();
				String pallet_y = pallet_y_text.getText();
				String pallet_z = pallet_z_text.getText();

				GUIPalletSetup pallet = new GUIPalletSetup(
						Integer.parseInt(pallet_x),
						Integer.parseInt(pallet_y),
						Integer.parseInt(pallet_z),
						Integer.parseInt(pallet_width),
						Integer.parseInt(pallet_height),
						Integer.parseInt(package_width),
						Integer.parseInt(package_height),
						Integer.parseInt(edge_gap),
						Integer.parseInt(box_gap));
						

				pallet.run();

				
				
				
				saved.writeln(pallet_x);
				saved.writeln(pallet_y);
				saved.writeln(pallet_z);
				saved.writeln(pallet_width);
				saved.writeln(pallet_height);
				saved.writeln(package_width);
				saved.writeln(package_height);
				saved.writeln(edge_gap);
				saved.writeln(box_gap);
				saved.close();
				
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
		
		main.setVisible(true);

	}
}
