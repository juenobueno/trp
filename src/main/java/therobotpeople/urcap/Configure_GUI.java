package therobotpeople.urcap;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

// This Class Stores the GUI settings for the Configure Page
// It also contains abstract definitions for certain buttons
public abstract class Configure_GUI {
	
	// Varaibles required to setup a Pallet which are set in Configure	
	int default_pallet_x          = 0;
	int default_pallet_y          = 0;
	int default_pallet_z          = 0;
	int default_pallet_width      = 400;
	int default_pallet_height     = 400;
	int default_package_width     = 100;
	int default_package_height    = 50;
	int default_package_elevation = 0;
	int default_edge_gap          = 0;
	int default_box_gap           = 0;
	int default_rotation          = 0;
	
	// GUI Elements
	
	// To add a new GUI element, you will need to also add it to
	// the functions Add_to_main and the constructor
	JFrame main;
	JLabel pallet_x_label;
	JLabel pallet_y_label;
	JLabel pallet_z_label;
	JLabel pallet_width_label;
	JLabel pallet_height_label;
	JLabel package_width_label;
	JLabel package_height_label;
	JLabel package_elevation_label;
	JLabel edge_gap_label;
	JLabel box_gap_label;
	JLabel preset_name_label;
	
	GuiTextField pallet_x_text;
	GuiTextField pallet_y_text;
	GuiTextField pallet_z_text;
	GuiTextField pallet_width_text;
	GuiTextField pallet_height_text;
	GuiTextField package_width_text;
	GuiTextField package_height_text;
	GuiTextField package_elevation_text;
	GuiTextField edge_gap_text;
	GuiTextField box_gap_text;
	GuiTextField preset_name_text;
	
	JButton choose_origin_button;
	JButton configure_pallet_button;
	JButton cancel_button;
	JButton rotate_robot_button;
	
	ImagePanel rotate_robot_image;
	BackgroundPanel background;

	private int sw, screen_width          = 800;
	private int sh, screen_height         = 600;
	private int lw ,default_label_width   = 150;
	private int lh ,default_label_height  = 20;
	private int tw ,default_text_width    = 50;
	private int th ,default_text_height   = 20;
	private int bw, default_button_width  = 200;
	private int bh, default_button_height = 50;
	//Constructor to instantiate all the images
	//Custom functions will call functions which will need to be
	//Implemented in another class
	
	//Need to handle the preset
	
	public Configure_GUI() {
		this("","");
	}
	
	public Configure_GUI(final String existing_preset_name, final String folder) {
		//Need a function to get existing values which have been saved
		this.main = new JFrame();
		this.main.setLayout(null);
		this.main.setSize(sw,sh);
		this.main.setLocationRelativeTo(null);
		this.main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Load in preset values
		this.load_default_values_from_file(existing_preset_name);
		
		//Create all gui elements
		//Instanstiate all the elements
		pallet_x_label = new JLabel("Pallet X");
		pallet_y_label = new JLabel("Pallet Y");
		pallet_z_label = new JLabel("Pallet Z");
		pallet_width_label  = new JLabel("Pallet Width");
		pallet_height_label = new JLabel("Pallet Height");
		package_width_label  = new JLabel("Package Width");
		package_height_label = new JLabel("Package Height");
		package_elevation_label = new JLabel("Package Elevation");
		edge_gap_label = new JLabel("Edge Gap");
		box_gap_label = new JLabel("Box Gap");
		preset_name_label = new JLabel("Preset Name");
		
		pallet_x_text = new GuiTextField();
		pallet_y_text = new GuiTextField();
		pallet_z_text = new GuiTextField();
		pallet_width_text = new GuiTextField();
		pallet_height_text = new GuiTextField();
		package_width_text = new GuiTextField();
		package_height_text = new GuiTextField();
		package_elevation_text = new GuiTextField();
		edge_gap_text = new GuiTextField();
		box_gap_text = new GuiTextField();
		preset_name_text = new GuiTextField();
		
		choose_origin_button = new JButton("Choose Origin");
		configure_pallet_button = new JButton("Configure");
		cancel_button = new JButton("Cancel");
		rotate_robot_button = new JButton("Rotate Robot");
		
		rotate_robot_image = new ImagePanel();
		background = null;

		//Set the Size of all Elements
		pallet_x_label.setSize(lw,lh);
		pallet_y_label.setSize(lw,lh);
		pallet_z_label.setSize(lw,lh);
		pallet_width_label.setSize(lw,lh);
		pallet_height_label.setSize(lw,lh);
		package_width_label.setSize(lw,lh);
		package_height_label.setSize(lw,lh);
		package_elevation_label.setSize(lw,lh);
		edge_gap_label.setSize(lw,lh);
		box_gap_label.setSize(lw,lh);
		preset_name_label.setSize(lw,lh);
		
		pallet_x_text.setSize(tw,th);
		pallet_y_text.setSize(tw,th);
		pallet_z_text.setSize(tw,th);
		pallet_width_text.setSize(tw,th);
		pallet_height_text.setSize(tw,th);
		package_width_text.setSize(tw,th);
		package_height_text.setSize(tw,th);
		package_elevation_text.setSize(tw,th);
		edge_gap_text.setSize(tw,th);
		box_gap_text.setSize(tw,th);
		preset_name_text.setSize(tw,th);
		
		choose_origin_button.setSize(bw,bh);
		configure_pallet_button.setSize(bw,bh);
		cancel_button.setSize(bw,bh);
		rotate_robot_button.setSize(bw,bh);
		
		rotate_robot_image.setBounds(500,300,240,240);
		background.setBounds(0,0,800,600);

		
		//Set position
		pallet_x_label.setLocation(50,360);
		pallet_y_label.setLocation(50,390);
		pallet_z_label.setLocation(50,420);
		pallet_width_label.setLocation(50,170);
		pallet_height_label.setLocation(50,200);
		package_width_label.setLocation(50,70);
		package_height_label.setLocation(50,100);
		package_elevation_label.setLocation(50,130);
		edge_gap_label.setLocation(50,240);
		box_gap_label.setLocation(50,270);
		preset_name_label.setLocation(50,460);
		
		pallet_x_text.setLocation(150,360);
		pallet_y_text.setLocation(150,390);
		pallet_z_text.setLocation(150,420);
		pallet_width_text.setLocation(150,170);
		pallet_height_text.setLocation(150,200);
		package_width_text.setLocation(150,70);
		package_height_text.setLocation(150,100);
		package_elevation_text.setLocation(150,130);
		edge_gap_text.setLocation(150,240);
		box_gap_text.setLocation(150,270);
		preset_name_text.setLocation(150,460);
		
		choose_origin_button.setLocation(50,310);
		configure_pallet_button.setLocation(50,490);
		cancel_button.setLocation(50,550);
		rotate_robot_button.setLocation(520,550);;
		
		//rotate_robot_image; //Set using setBounds
		//background;

		//set text
		pallet_x_text.setText(Integer.toString(default_pallet_x));
		pallet_y_text.setText(Integer.toString(default_pallet_y));
		pallet_z_text.setText(Integer.toString(default_pallet_z));
		pallet_width_text.setText(Integer.toString(default_pallet_width));
		pallet_height_text.setText(Integer.toString(default_pallet_height));
		package_width_text.setText(Integer.toString(default_package_width));
		package_height_text.setText(Integer.toString(default_package_height));
		package_elevation_text.setText(Integer.toString(default_package_elevation));
		edge_gap_text.setText(Integer.toString(default_edge_gap));
		box_gap_text.setText(Integer.toString(default_box_gap));
		preset_name_text.setText(existing_preset_name);
		
		// Set bution actions up using functions that need to be implemented
		choose_origin_button.addActionListener(choose_origin_button_action());
		configure_pallet_button.addActionListener(configure_pallet_button_action(existing_preset_name, folder));
		cancel_button.addActionListener(cancel_button_action());
		rotate_robot_button.addActionListener(rotate_robot_button_action());
		
		
		//Add everything into the main JFrame
		this.add_all_to_main();
	}
	public abstract ActionListener cancel_button_action();
	public abstract ActionListener rotate_robot_button_action();
	
	public abstract ActionListener configure_pallet_button_action(final String existing_preset_name, final String folder);
	
	public abstract ActionListener choose_origin_button_action();
	
	private void load_default_values_from_file(String filename) {
		
	}
	
	private void add_all_to_main() {
		this.main.add(pallet_x_label);
		this.main.add(pallet_y_label);
		this.main.add(pallet_z_label);
		this.main.add(pallet_width_label);
		this.main.add(pallet_height_label);
		this.main.add(package_width_label);
		this.main.add(package_height_label);
		this.main.add(package_elevation_label);
		this.main.add(edge_gap_label);
		this.main.add(box_gap_label);
		this.main.add(preset_name_label);
		
		this.main.add(pallet_x_text);
		this.main.add(pallet_y_text);
		this.main.add(pallet_z_text);
		this.main.add(pallet_width_text);
		this.main.add(pallet_height_text);
		this.main.add(package_width_text);
		this.main.add(package_height_text);
		this.main.add(package_elevation_text);
		this.main.add(edge_gap_text);
		this.main.add(box_gap_text);
		this.main.add(preset_name_text);
		
		this.main.add(choose_origin_button);
		this.main.add(configure_pallet_button);
		this.main.add(cancel_button);
		this.main.add(rotate_robot_button);
		
		this.main.add(rotate_robot_image);
		this.main.add(background);
		
	}
}
