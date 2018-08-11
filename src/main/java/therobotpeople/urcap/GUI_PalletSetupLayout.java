package therobotpeople.urcap;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public abstract class GUI_PalletSetupLayout {

	// Pallet Preset Parameters
	String preset_name;
	int package_width;
	int package_height;
	int package_elevation;
	int pallet_width;
	int pallet_height;
	int edge_gap;
	int box_gap;
	int rotation;
	float origin_x;
	float origin_y;
	float origin_z;
	float pickup_x;
	float pickup_y;
	float pickup_z;
	
	// GUI Elements and some associated variables 
	JFrame main;
	JButton rotate_package; 
	JButton package_normal;
	JButton package_rotated;
	JButton undo;
	JButton done;
	JLabel layer_text;
	JButton layer_up;
	JButton layer_down;
	ImagePanel bg;
	JPanel pallet;
	
	private final int pallet_width_max_px = 500;
	private final int pallet_height_max_px = 500;
	boolean is_package_rotated = false;
	
	Grid grid;
	ArrayList<ArrayList<JButton>> button_layout;
	ArrayList<JButton> palletized;
	
	// mm to Pixel Conversion
	float x_mm_per_px;
	float y_mm_per_px;
	
	final int edge_gap_x;
	final int edge_gap_y;
	final int box_gap_x;
	final int box_gap_y;
	


	public GUI_PalletSetupLayout(String pallet_preset) {		
		// Read values from file to update the corresponding instance variables
		FileManipulate saved = new FileManipulate(pallet_preset, FileManipulate.default_pallet_presets_folder);
		
		if (saved.exists()) {
			try {
				preset_name			= pallet_preset;
				
				package_width 		= Integer.parseInt(saved.readLine());
				package_height		= Integer.parseInt(saved.readLine());
				package_elevation 	= Integer.parseInt(saved.readLine());
				pallet_width  		= Integer.parseInt(saved.readLine());
				pallet_height 		= Integer.parseInt(saved.readLine());
				edge_gap      		= Integer.parseInt(saved.readLine());
				box_gap		  		= Integer.parseInt(saved.readLine());
				rotation			= Integer.parseInt(saved.readLine());
				origin_x	  		= Float.parseFloat(saved.readLine());
				origin_y	  		= Float.parseFloat(saved.readLine());
				origin_z	  		= Float.parseFloat(saved.readLine());
				pickup_x	  		= Float.parseFloat(saved.readLine());
				pickup_y	  		= Float.parseFloat(saved.readLine());
				pickup_z	  		= Float.parseFloat(saved.readLine());
				
				saved.close();
			} catch (Exception e) {
			
			}
		}
		
		// Calculate conversion ratios
		x_mm_per_px = (float)pallet_width / (float)pallet_width_max_px; 
		y_mm_per_px = (float)pallet_height / (float)pallet_height_max_px;

		this.edge_gap_x = edge_gap;
		this.edge_gap_y = edge_gap;
		this.box_gap_x = box_gap;
		this.box_gap_y = box_gap;
		
		
		this.grid = new Grid(1000, 1000);
		//grid.set(new Point(0,0), pallet_width, pallet_height);
		//grid.clear( new Point(edge_gap_x, edge_gap_y), pallet_width, pallet_height);
		
		button_layout = new ArrayList<ArrayList<JButton>>();
		button_layout.add(new ArrayList<JButton>());
		palletized = new ArrayList<JButton>();
		
		main = new JFrame();
		main.setSize(800,600);
		main.setLayout(null);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		rotate_package = new JButton();
		rotate_package.setBounds(170,0,30,30);
		rotate_package.setOpaque(false);
		rotate_package.setContentAreaFilled(false);
		rotate_package.setBorderPainted(false);
		rotate_package.addActionListener(rotate_package_action());
		try {
			Image img = ImageIO.read(getClass().getResource("/rotate.png"));
			rotate_package.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			
		}
		
		package_normal = new JButton();
		package_normal.setName("not rotated");
		package_normal.setBackground(Color.BLACK);
		package_normal.setEnabled(false);
		// Size and center the package according to the pallet preset file
		package_normal.setSize((int)Math.ceil((float)package_width / x_mm_per_px), (int)Math.ceil((float)package_height / y_mm_per_px));
		package_normal.setLocation(100 - package_normal.getWidth() / 2, 150 - package_normal.getHeight() / 2); 
		
		package_rotated = new JButton();
		package_rotated.setName("rotated");
		package_rotated.setBackground(Color.BLACK);
		package_rotated.setEnabled(false);
		// Size and center the package according to the pallet preset file
		package_rotated.setSize((int)Math.ceil((float)package_height / y_mm_per_px), (int)Math.ceil((float)package_width / x_mm_per_px));
		package_rotated.setLocation(100 - package_rotated.getWidth() / 2, 150 - package_rotated.getHeight() / 2); 
		package_rotated.setVisible(false); 

		
		undo = new JButton("<html>Delete Last Box <br> on Current Layer</html>");
		undo.setBounds(10, 480, 180, 50);
		undo.addActionListener(undoListener());
		
		done = new JButton("Done");
		done.setBounds(10, 540, 180, 50);
		done.addActionListener(doneListener());
		
		pallet = new JPanel();
		pallet.setBackground(Color.ORANGE);
		pallet.setLayout(null);
		pallet.setBounds(250,50,500,500);
		pallet.addMouseListener(palletListener());
		
		layer_text = new JLabel("Layer: 0", SwingConstants.RIGHT);
		layer_text.setSize(100,25);
		layer_text.setLocation(390,563);
		
		layer_up = new JButton("+");
		layer_up.setSize(50,25);
		layer_up.setLocation(510,563);
		layer_up.addActionListener(layerUpListener());
		
		layer_down = new JButton("-");
		layer_down.setSize(50,25);
		layer_down.setLocation(560,563);
		layer_down.addActionListener(layerDownListener());
		
		bg = new ImagePanel("bg_pallet.png");
		bg.setBounds(0,0,800,600);
		
		main.add(rotate_package);
		main.add(package_normal);
		main.add(package_rotated);
		main.add(undo);
		main.add(done);
		main.add(layer_text);
		main.add(layer_up);
		main.add(layer_down);
		main.add(pallet);
		main.add(bg);
	
		main.setVisible(true);
	}
	
	public abstract ActionListener rotate_package_action();
	public abstract ActionListener undoListener();
	public abstract ActionListener doneListener();
	public abstract ActionListener layerUpListener();
	public abstract ActionListener layerDownListener();
	public abstract MouseAdapter palletListener();

}

