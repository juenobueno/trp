package therobotpeople.urcap;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class Setup_GUI implements Runnable {

	final int package_width;
	final int package_height;
	final int package_depth = 50;
	
	final int pallet_width;
	final int pallet_height;
	
	final int pallet_x;
	final int pallet_y;
	final int pallet_z;
	
	final int edge_gap;
	final int box_gap;

	private final int pallet_width_max = 500;
	private final int pallet_height_max = 500;
	private double x_ratio = 1;
	private double y_ratio = 1;

	final int package_width_real;
	final int package_height_real;

	final int edge_gap_x;
	final int edge_gap_y;
	final int box_gap_x;
	final int box_gap_y;
	
	final int rotation;

	final JFrame main;
	final Grid grid;
	final ArrayList<ArrayList<JButton>> button_layout;

	final String folder;
	final String file_name;

	private final int button_width = 100;
	private final int button_height = 50;
	
	public static float home_x, home_y, home_z;
	
	public static int layer = 0;
	
	
	//GUI elements
	JButton chooseOrigin;
	JButton package_up;
	JButton package_down;
	JButton package_left;
	JButton package_right;
	ArrayList<JButton> palletized;
	JPanel pallet;
	JButton undo;
	JButton exit;
	JButton get_positions;
	JTextField layer_text;
	JButton layer_up;
	JButton layer_down;
	BackgroundPanel bg;

	public Setup_GUI(int pallet_x, int pallet_y, int pallet_z,
					int pallet_width, int pallet_height,
					int package_width, int package_height,
					int edge_gap, int box_gap,
					int rotation,
					String folder, String file_name) {
		
		this.pallet_x = pallet_x;
		this.pallet_y = pallet_y;
		this.pallet_z = pallet_z;
		
		this.rotation = rotation;
		
		this.folder = folder;
		this.file_name = file_name;
		
		//need to do some calculations for package related details, so do it after
		//this is due to display vs actual size errors
		this.pallet_width = pallet_width;
		this.pallet_height = pallet_height;
		this.edge_gap = edge_gap;
		this.box_gap = box_gap;
		this.edge_gap_x = edge_gap;
		this.edge_gap_y = edge_gap;
		this.box_gap_x = box_gap;
		this.box_gap_y = box_gap;
		
		
		this.package_width_real = pallet_width;
		this.package_height_real = pallet_height;
		
		this.package_width = package_width;
		this.package_height = package_height;
		
		button_layout = new ArrayList<ArrayList<JButton>>();
		button_layout.add(new ArrayList<JButton>());
		
		this.main = new JFrame();
		this.main.setSize(800,600);
		this.main.setLayout(null);
		this.main.setLocationRelativeTo(null);
		
		this.grid = new Grid(pallet_width, pallet_height);
		grid.set(new Point(0,0), pallet_width, pallet_height);
		grid.clear( new Point(edge_gap_x, edge_gap_y), pallet_width, pallet_height);
		
		//Adding in the rest of the buttons that I need to make the setup
		chooseOrigin = new JButton("Choose Origin");
		chooseOrigin.setSize(button_width, button_height);
		chooseOrigin.setLocation(350,350);
		chooseOrigin.addActionListener(chooseOriginListener());
		//Should do a preview of the package with a rotate button. Can use a box to hold it
		//So it won't take up too much space
		package_up = new JButton("^");
		package_up.setSize(package_width, package_height);
		package_up.setLocation(0,0);
		package_up.addActionListener(packageUpListener());
		
		package_down = new JButton("v");
		package_down.setSize(package_width, package_height);
		package_down.setLocation(50,0);
		package_down.addActionListener(packageDownListener());
		
		package_left = new JButton("<");
		package_left.setSize(package_height, package_width);
		package_left.setLocation(0,100);
		package_left.addActionListener(packageLeftListener());
		
		package_right = new JButton(">");
		package_right.setSize(package_height, package_width);
		package_right.setLocation(100,100);
		package_right.addActionListener(packageRightListener());
		
		palletized = new ArrayList<JButton>();
		
		pallet = new JPanel();
		pallet.setBackground(Color.ORANGE);
		pallet.setLayout(null);
		pallet.setBounds(250,50,500,500);
		pallet.addMouseListener(palletListener());
		
		undo = new JButton("Undo");
		undo.setSize(button_width, button_height);
		undo.setLocation(0,200);
		undo.addActionListener(undoListener());
		
		exit = new JButton("Exit");
		exit.setSize(button_width, button_height);
		exit.setLocation(250, 200);
		exit.addActionListener(exitListener());
		
		get_positions = new JButton("Get Positions");
		get_positions.setSize(button_width, button_height);
		get_positions.setLocation(100, 200);
		get_positions.addActionListener(getPositionListener());
		
		layer_text = new JTextField();
		layer_text.setSize(50,40);
		layer_text.setLocation(300,400);
		layer_text.setText("1");
		
		layer_up = new JButton("+");
		layer_up.setSize(60,40);
		layer_up.setLocation(360,400);
		layer_up.addActionListener(layerUpListener());
		
		layer_down = new JButton("-");
		layer_down.setSize(60,40);
		layer_down.setLocation(420,400);
		layer_down.addActionListener(layerDownListener());
		
		bg = null;
		try {
			Image img = ImageIO.read(getClass().getResource("/bg_pallet.png"));
			bg = new BackgroundPanel(img);
			bg.setBounds(0,0,800,600);
		}catch(Exception ex) {
			//
		}
		
		main.add(chooseOrigin);
		main.add(package_up);
		main.add(package_down);
		main.add(package_left);
		main.add(package_right);
		main.add(pallet);
		main.add(undo);
		main.add(exit);
		main.add(get_positions);
		main.add(layer_text);
		main.add(layer_up);
		main.add(layer_down);
		main.add(bg);
	
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		main.setVisible(true);
		
	}
	
	public abstract ActionListener chooseOriginListener();
	public abstract ActionListener packageUpListener();
	public abstract ActionListener packageDownListener();
	public abstract ActionListener packageLeftListener();
	public abstract ActionListener packageRightListener();
	public abstract MouseAdapter palletListener();
	public abstract ActionListener undoListener();
	public abstract ActionListener exitListener();
	public abstract ActionListener getPositionListener();
	public abstract ActionListener layerUpListener();
	public abstract ActionListener layerDownListener();
	
	public void run() {
		//might need to put all constructors into here to make it runnable with threads
	}
}

