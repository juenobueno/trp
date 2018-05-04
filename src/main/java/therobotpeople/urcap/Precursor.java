package therobotpeople.urcap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Precursor {

	public static void main(String[] args) {
		
		int default_package_width = 50;
		int default_package_height = 100;
		int default_pallet_width = 400;
		int default_pallet_height = 400;
		int default_edge_gap = 0;
		int default_box_gap = 0;
		
		File saved = new File("Data");
		if( saved.exists()) {
		
			try {
				BufferedReader read = new BufferedReader(new FileReader(saved));
				
				default_package_width = Integer.parseInt(read.readLine());
				default_package_height= Integer.parseInt(read.readLine());
				default_pallet_width  = Integer.parseInt(read.readLine());
				default_pallet_height = Integer.parseInt(read.readLine());
				default_edge_gap      = Integer.parseInt(read.readLine());
				default_box_gap		  = Integer.parseInt(read.readLine());
				
			} catch (NumberFormatException | IOException e) {
			}
			
			//read in data
		}else {
			//Save the data once so its in there, then save it at the end
			//for any modifications
		}
		
		
		
		// TODO Auto-generated method stub
		final JFrame main = new JFrame();
		main.setLayout(null);
		
		JLabel package_width = new JLabel("Package Width");
		package_width.setSize(100, 20);
		package_width.setLocation(50, 50);
		main.add(package_width);
		
		JTextField package_width_text = new JTextField();
		package_width_text.setSize(50,20);
		package_width_text.setLocation(150, 50);
		package_width_text.setText(Integer.toString(default_package_width));
		main.add(package_width_text);
		
		JLabel package_height = new JLabel("Package Height");
		package_height.setSize(100,20);
		package_height.setLocation(50, 100);
		main.add(package_height);
		
		JTextField package_height_text = new JTextField();
		package_height_text.setSize(50,20);
		package_height_text.setLocation(150, 100);
		package_height_text.setText(Integer.toString(default_package_height));
		main.add(package_height_text);
		
		JLabel pallet_width = new JLabel("Pallet Width");
		pallet_width.setSize(100,20);
		pallet_width.setLocation(50,150);
		main.add(pallet_width);
		
		JTextField pallet_width_text = new JTextField();
		pallet_width_text.setSize(50,20);
		pallet_width_text.setLocation(150,150);
		pallet_width_text.setText(Integer.toString(default_pallet_width));
		main.add(pallet_width_text);
		
		JLabel pallet_height = new JLabel("Pallet Height");
		pallet_height.setSize(100,20);
		pallet_height.setLocation(50, 200);
		main.add(pallet_height);
		
		JTextField pallet_height_text = new JTextField();
		pallet_height_text.setSize(50,20);
		pallet_height_text.setLocation(150, 200);
		pallet_height_text.setText(Integer.toString(default_pallet_height));
		main.add(pallet_height_text);
		
		JLabel edge_gap = new JLabel("Edge Gap");
		edge_gap.setSize(100,20);
		edge_gap.setLocation(50,250);
		main.add(edge_gap);
		
		JTextField edge_gap_text = new JTextField();
		edge_gap_text.setSize(50,20);
		edge_gap_text.setLocation(150,250);
		edge_gap_text.setText(Integer.toString(default_edge_gap));
		main.add(edge_gap_text);
		
		JLabel box_gap = new JLabel("Box Gap");
		box_gap.setSize(100,20);
		box_gap.setLocation(50, 300);
		main.add(box_gap);
		
		JTextField box_gap_text = new JTextField();
		box_gap_text.setSize(50,20);
		box_gap_text.setLocation(150, 300);
		box_gap_text.setText(Integer.toString(default_box_gap));
		main.add(box_gap_text);
		
		JButton close = new JButton("Exit");
		close.setSize(100,50);
		close.setLocation(300,50);
		main.add(close);
		
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.dispose();
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
				
				
			}
		});
		
		main.setSize(640, 480);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		main.setVisible(true);
	}

}
