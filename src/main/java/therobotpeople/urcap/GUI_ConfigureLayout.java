package therobotpeople.urcap;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * GUI_ConfigureLayout provides the layout of all the GUI elements for the configurations page. 
 */
public abstract class GUI_ConfigureLayout {
	
	// Default values of the parameters which describe a pallet layout, dimensions in mm
	// These are used to fill in the text boxes visible to the user.
	int default_package_width     = 100;
	int default_package_height    = 50;
	int default_package_elevation = 25;
	int default_pallet_width      = 500;
	int default_pallet_height     = 500;
	int default_edge_gap          = 0;
	int default_box_gap           = 0;
	String default_preset_name	  = "";
	
	// Parameters which describe the dimensions of GUI Elements
	private int screen_width          = 800;
	private int screen_height         = 600;
	private int default_label_width   = 150;
	private int default_label_height  = 20;
	private int default_text_width    = 50;
	private int default_text_height   = 20;
	private int default_button_width  = 200;
	private int default_button_height = 50;
	
	// GUI Elements
	JFrame main;
	JLabel package_width_label;
	JLabel package_height_label;
	JLabel package_elevation_label;
	JLabel pallet_width_label;
	JLabel pallet_height_label;
	JLabel edge_gap_label;
	JLabel box_gap_label;
	JLabel preset_name_label;

	TextField package_width_text;
	TextField package_height_text;
	TextField package_elevation_text;
	TextField pallet_width_text;
	TextField pallet_height_text;
	TextField edge_gap_text;
	TextField box_gap_text;
	TextField preset_name_text;
	
	JButton rotate_robot_button;
	JButton choose_origin_button;
	JButton choose_pickup_button;
	JButton configure_pallet_button;
	JButton cancel_button;
	
	ImagePanel rotate_robot_image;
	ImagePanel bg;
	
	// Variables used to store data that is not visible on the GUI. These
	// are objects as they need to be passed by reference.
	Integer rotation = 0;
	
	Float origin_x = 0.0f;
	Float origin_y = 0.0f;
	Float origin_z = 0.0f;
	
	Float pickup_x = 0.0f;
	Float pickup_y = 0.0f;
	Float pickup_z = 0.0f;
	
	public GUI_ConfigureLayout(String existing_preset_name) {
		// Instantiating GUI elements
		main = new JFrame();
		main.setLayout(null);
		main.setSize(screen_width,screen_height);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		package_width_label  = new JLabel("Package Width");
		package_height_label = new JLabel("Package Height");
		package_elevation_label = new JLabel("Package Elevation");
		pallet_width_label  = new JLabel("Pallet Width");
		pallet_height_label = new JLabel("Pallet Height");
		edge_gap_label = new JLabel("Edge Gap");
		box_gap_label = new JLabel("Box Gap");
		preset_name_label = new JLabel("Preset Name");
		
		package_width_text = new TextField();
		package_height_text = new TextField();
		package_elevation_text = new TextField();
		pallet_width_text = new TextField();
		pallet_height_text = new TextField();
		edge_gap_text = new TextField();
		box_gap_text = new TextField();
		preset_name_text = new TextField();
		
		rotate_robot_button = new JButton("Rotate Robot");
		choose_origin_button = new JButton("Choose Origin");
		choose_pickup_button = new JButton("Choose Pickup");
		configure_pallet_button = new JButton("Done");
		cancel_button = new JButton("Cancel");
		
		rotate_robot_image = new ImagePanel(null);
		bg = new ImagePanel("bg_configure.png");

		// Set the size of all GUI elements
		package_width_label.setSize(default_label_width,default_label_height);
		package_height_label.setSize(default_label_width,default_label_height);
		package_elevation_label.setSize(default_label_width,default_label_height);
		pallet_width_label.setSize(default_label_width,default_label_height);
		pallet_height_label.setSize(default_label_width,default_label_height);
		edge_gap_label.setSize(default_label_width,default_label_height);
		box_gap_label.setSize(default_label_width,default_label_height);
		preset_name_label.setSize(default_label_width,default_label_height);
		
		package_width_text.setSize(default_text_width,default_text_height);
		package_height_text.setSize(default_text_width,default_text_height);
		package_elevation_text.setSize(default_text_width,default_text_height);
		pallet_width_text.setSize(default_text_width,default_text_height);
		pallet_height_text.setSize(default_text_width,default_text_height);
		edge_gap_text.setSize(default_text_width,default_text_height);
		box_gap_text.setSize(default_text_width,default_text_height);
		preset_name_text.setSize(100,default_text_height);
		
		rotate_robot_button.setSize(default_button_width,default_button_height);
		choose_origin_button.setSize(default_button_width,default_button_height);
		choose_pickup_button.setSize(default_button_width,default_button_height);
		configure_pallet_button.setSize(default_button_width,default_button_height);
		cancel_button.setSize(default_button_width,25);
		
		rotate_robot_image.setBounds(500,300,240,240);
		rotate_robot_image.setOpaque(false);
		bg.setBounds(0,0,800,600);
		
		// Set the position of all the GUI elements
		package_width_label.setLocation(50,70);
		package_height_label.setLocation(50,100);
		package_elevation_label.setLocation(50,130);
		pallet_width_label.setLocation(50,160);
		pallet_height_label.setLocation(50,190);
		edge_gap_label.setLocation(50,220);
		box_gap_label.setLocation(50,250);	
		preset_name_label.setLocation(50,470);
		
		package_width_text.setLocation(200,70);
		package_height_text.setLocation(200,100);
		package_elevation_text.setLocation(200,130);
		pallet_width_text.setLocation(200,160);
		pallet_height_text.setLocation(200,190);
		edge_gap_text.setLocation(200,220);
		box_gap_text.setLocation(200,250);
		preset_name_text.setLocation(150,470);
				
		rotate_robot_button.setLocation(50,290);
		choose_origin_button.setLocation(50,350);
		choose_pickup_button.setLocation(50,410);
		configure_pallet_button.setLocation(50,500);
		cancel_button.setLocation(50,560);
		
		// Set the functions associated with each button
		rotate_robot_button.addActionListener(rotate_robot_button_action());
		choose_origin_button.addActionListener(choose_origin_button_action());
		choose_pickup_button.addActionListener(choose_pickup_button_action());
		configure_pallet_button.addActionListener(configure_pallet_button_action());
		cancel_button.addActionListener(cancel_button_action());
		
		// Add everything into the main JFrame
		main.add(pallet_width_label);
		main.add(pallet_height_label);
		main.add(package_width_label);
		main.add(package_height_label);
		main.add(package_elevation_label);
		main.add(edge_gap_label);
		main.add(box_gap_label);
		main.add(preset_name_label);
		
		main.add(pallet_width_text);
		main.add(pallet_height_text);
		main.add(package_width_text);
		main.add(package_height_text);
		main.add(package_elevation_text);
		main.add(edge_gap_text);
		main.add(box_gap_text);
		main.add(preset_name_text);
		
		main.add(rotate_robot_button);
		main.add(choose_origin_button);
		main.add(choose_pickup_button);
		main.add(configure_pallet_button);
		main.add(cancel_button);
		
		main.add(rotate_robot_image);
		main.add(bg);
		
		// Update the default parameters which describe a pallet layout if 
		// an existing pallet layout is being editted. 
		load_default_values_from_file(existing_preset_name);
		
		main.setVisible(true);
	}

	public abstract void load_default_values_from_file(String filename);
	public abstract ActionListener rotate_robot_button_action();
	public abstract ActionListener choose_origin_button_action();
	public abstract ActionListener choose_pickup_button_action();
	public abstract ActionListener configure_pallet_button_action();
	public abstract ActionListener cancel_button_action();
	
}
