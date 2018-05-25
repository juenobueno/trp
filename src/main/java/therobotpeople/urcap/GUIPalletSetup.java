package therobotpeople.urcap;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ur.urcap.api.domain.userinteraction.RobotPositionCallback;
import com.ur.urcap.api.domain.value.Pose;
import com.ur.urcap.api.domain.value.jointposition.JointPositions;

public class GUIPalletSetup {
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
	
	private static int layer = 0;

	/* Never used
	public GUIPalletSetup() {
		this(0,0,0,400,400,100,50,0,0, "Default", "Waypoint1");
	}

	public GUIPalletSetup(int pallet_x, int pallet_y, int pallet_z, int pallet_width, int pallet_height, int package_width, int package_height, int edge_gap, int box_gap) {
		this(pallet_x,pallet_y,pallet_z,pallet_width,pallet_height,package_width, package_height, edge_gap, box_gap, "Default", "Waypoint2");
	}
	*/

	public GUIPalletSetup(int pallet_x, int pallet_y, int pallet_z, int pallet_width, int pallet_height, int package_width, int package_height, int edge_gap, int box_gap, int rotation, String folder, String file_name) {
		
		this.pallet_x = pallet_x;
		this.pallet_y = pallet_y;
		this.pallet_z = pallet_z;
		
		this.rotation = rotation;

		if( pallet_width > pallet_width_max || pallet_height > pallet_height_max) {

			if(pallet_width > pallet_width_max) {
				x_ratio = pallet_width/pallet_width_max;
			}
			if(pallet_height > pallet_height_max) {
				y_ratio = pallet_height/pallet_height_max;
			}
			this.pallet_width = pallet_width_max;
			this.pallet_height = pallet_height_max;

			this.package_width_real = package_width;
			this.package_height_real = package_height;

			this.package_width = (int)(package_width/x_ratio+0.5); //+0.5 for natural rounding to cloests int
			this.package_height = (int)(package_height/y_ratio+0.5);

			//Ratios would need to be split into x and y components
			this.edge_gap = edge_gap;
			this.box_gap = box_gap;

			this.edge_gap_x =(int)(edge_gap/x_ratio + 0.5);
			this.edge_gap_y = (int)(edge_gap/y_ratio + 0.5);
			this.box_gap_x = (int)(box_gap/x_ratio + 0.5);
			this.box_gap_y = (int)(box_gap/y_ratio + 0.5);
		}else {
			this.pallet_width = pallet_width;
			this.pallet_height = pallet_height;

			this.package_width = package_width;
			this.package_height = package_height;

			this.package_width_real = package_width;
			this.package_height_real = package_height;

			this.edge_gap = edge_gap;
			this.box_gap = box_gap;

			this.edge_gap_x =edge_gap;
			this.edge_gap_y = edge_gap;
			this.box_gap_x = box_gap;
			this.box_gap_y = box_gap;
		}

		this.folder = folder;
		this.file_name = file_name;
		this.main = new JFrame();
		this.main.setSize(800, 600);
		this.main.setLayout(null);
		this.main.setLocationRelativeTo(null);

		grid = new Grid(pallet_width, pallet_height);
		grid.set(new Point(0,0), pallet_width, pallet_height);
		grid.clear(new Point(edge_gap_x,edge_gap_y),pallet_width - edge_gap_x, pallet_height - edge_gap_y);

		button_layout = new ArrayList<ArrayList<JButton>>();
		button_layout.add(new ArrayList<JButton>());
	}

	public void run() {

		// Choose Origin Button
	    JButton chooseOrigin = new JButton("Choose Origin"); 
	    chooseOrigin.setSize(200, 40); 
	    chooseOrigin.setLocation(350, 350);
	    main.add(chooseOrigin); 
	
	    chooseOrigin.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {	
				main.setVisible(false);
				
				TRPProgramNodeContribution.api.getUserInteraction().getUserDefinedRobotPosition(new RobotPositionCallback() { 
					@SuppressWarnings("deprecation")
					@Override 
					public void onOk(Pose pose, JointPositions jointPositions) { 
						home_x = (float)(pose.getPosition().getX());
						home_y = (float)(pose.getPosition().getY());
						home_z = (float)(pose.getPosition().getZ());
						
					} 
				});
			} 
	    });
		
		
		JButton package_up = new JButton("^");
		package_up.setSize(package_width, package_height);
		package_up.setLocation(0, 0);
		main.add(package_up);

		package_up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Selector.selected = "^";
			}
		});

		JButton package_down = new JButton("v");
		package_down.setSize(package_width, package_height);
		package_down.setLocation(50,0);
		main.add(package_down);

		package_down.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Selector.selected = "v";
			}
		});

		JButton package_left = new JButton("<");
		package_left.setSize(package_height, package_width);
		package_left.setLocation(0,100);
		main.add(package_left);


		package_left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Selector.selected = "<";
			}
		});

		JButton package_right = new JButton(">");
		package_right.setSize(package_height, package_width);
		package_right.setLocation(100,100);
		main.add(package_right);


		package_right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Selector.selected = ">";
			}
		});

		final ArrayList<JButton> palletized = new ArrayList<JButton>();

		final JPanel pallet = new JPanel();
		pallet.setBackground(Color.ORANGE);
		pallet.setLayout(null);
		pallet.setSize(pallet_width,pallet_height);
		pallet.setLocation(400, 0);
		pallet.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if(Selector.selected != "" && Selector.selected != null) {
					Point position = e.getPoint();

					JButton pack = new JButton(Selector.selected + Selector.count + (GUIPalletSetup.layer+1));

					int x_pos = -1;
					int y_pos = -1;
					int pack_height = -1;
					int pack_width = -1;
					if(Selector.selected == "<" || Selector.selected == ">") {
						pack_width = package_height;
						pack_height = package_width;
					}else {
						pack_width = package_width;
						pack_height = package_height;
					}

					pack.setSize(pack_width, pack_height);
					pack_width = pack_width+box_gap;
					pack_height= pack_height+box_gap;

					x_pos = position.x;
					y_pos = position.y;

					//System.out.println("The x and y positions are x: "+x_pos+" , y: "+y_pos+" ");

					int left_distance = grid.left_limit(position, pack_width, pack_height);
					int right_distance= grid.right_limit(position, pack_width, pack_height);

					//System.out.println("The Left Distance to nearest object is: "+left_distance);
					//System.out.println("The Right distance to nearest object is: "+right_distance);
					if(left_distance == position.x) {
						//System.out.println("LL: There is no object between this and the Left wall");

						if(right_distance+pack_width+position.x == pallet_width) {
							//In line with the grid, lock into grid
							//System.out.println("RR: There is no object between this and the Right wall");

							x_pos = x_pos - x_pos%pack_width;

						}else {
							//need to align the right
							//System.out.println("==NEED TO ALIGN RIGHT");
							x_pos = x_pos + (right_distance)%pack_width+1; //need to +1 to get correct alignment
							//x_pos = x_pos + pack_width-((x_pos+pack_width)%pack_width);

						}

					}else {
						//need to align the left

						if(left_distance == 0 && right_distance == 0) {
							x_pos = x_pos - x_pos%pack_width + edge_gap_x;
						}else {

							System.out.println("==NEED TO ALIGN LEFT");
							x_pos = x_pos  - left_distance%pack_width;
						}
						//x_pos = x_pos - x_pos%pack_width;
					}
					//The box is now lined up horizontally
					//System.out.println("====New X_POS is "+x_pos);

					int top_distance = grid.top_limit(position, pack_width, pack_height);
					int bottom_distance = grid.bottom_limit(position, pack_width, pack_height);

					//System.out.println("The Top Distance to nearest object is: "+top_distance);
					//System.out.println("The Bottom Distance to nearest object is: "+bottom_distance);

					if(top_distance == position.y) {
						//System.out.println("TT: There is no object between this and the Top wall");
						if(bottom_distance+pack_height+position.y == pallet_height) {
							//In line with grid lock into grid
							//System.out.println("BB: There is no object between this and the Bottom wall");

							y_pos = y_pos - y_pos%pack_height;
						}else {
							//need to align the bottom
							//System.out.println("==NEED TO ALIGN BOTTOM");
							//y_pos = y_pos + pack_height - ((y_pos+pack_height)%pack_height);
							y_pos = y_pos+(bottom_distance)%pack_height+1;
						}

					}else {
						//need to align the top
						if(top_distance== 0 && bottom_distance == 0) {
							y_pos = y_pos - y_pos%pack_height + edge_gap_y;

						}else {
						//System.out.println("==NEED TO ALIGN TOP");
						y_pos = y_pos - top_distance%pack_height;
						//y_pos = y_pos - y_pos%pack_height;
						}
					}
					//System.out.println("====New Y_POS is "+y_pos);

					pack.setLocation(x_pos, y_pos);

					//System.out.println(position.x);

					if(grid.set_Object(new Point(pack.getLocation().x, pack.getLocation().y ),  pack_width, pack_height)) {


						//System.out.println(pack.getLocation());

						pack.setVisible(true);

						palletized.add(pack);

						//button_history.add(pack);

						button_layout.get(GUIPalletSetup.layer).add(pack);

						pallet.add(pack);
						main.repaint();

						Selector.selected = "";
						Selector.count++;
					}


				}

			}
		});

		JButton undo = new JButton("Undo");
		undo.setSize(button_width, button_height);
		undo.setLocation(0,200);
		main.add(undo);

		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if( button_layout.get(GUIPalletSetup.layer).size() >= 1) {

					JButton to_Remove= button_layout.get(GUIPalletSetup.layer).get(button_layout.get(GUIPalletSetup.layer).size() - 1);
					button_layout.get(GUIPalletSetup.layer).remove(to_Remove);

					System.out.println("Removing a button at: "+ to_Remove.getLocation());

					grid.clear(to_Remove.getLocation(), to_Remove.getSize().width+box_gap, to_Remove.getSize().height+box_gap);

					Selector.count--;

					pallet.remove(to_Remove);
					pallet.revalidate();
					pallet.repaint();
				}
			}
		});

		JButton exit = new JButton("Exit");
		exit.setSize(button_width, button_height);
		exit.setLocation(250, 200);
		main.add(exit);

		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TRPProgramNodeContribution.gui_home_thread.run();
				main.dispose();
			}
		});

		JButton get_positions = new JButton("Get Positions");
		get_positions.setSize(button_width, button_height);
		get_positions.setLocation(100,200);
		main.add(get_positions);


		get_positions.addActionListener(new ActionListener() {
			@Override
			//
			public void actionPerformed(ActionEvent arg0) {
				FileManipulate urscript = new FileManipulate("waypointTest.script", "../programs"); 
				FileManipulate save = new FileManipulate(file_name, folder);
				for( int i = 0; i < button_layout.size(); i++) {
					save.writeln("==== Layer "+i+" ====");
					for( int j = 0; j < button_layout.get(i).size(); j++) {
						JButton temp = button_layout.get(i).get(j);
						float x_pos = ((float)pallet_x/1000) + ((float)((temp.getLocation().x + temp.getSize().width/2)*x_ratio)/1000);
						float y_pos = ((float)pallet_y/1000) + ((float)((temp.getLocation().y + temp.getSize().height/2)* y_ratio)/1000);
						// Package Dimensions Labels
						float z_pos = (float)pallet_z/1000 + (float)package_depth/1000;
						String orientation = "";
						if(temp.getText().contains("^")) {
							orientation = "0";
						}else if(temp.getText().contains("v")) {
							orientation = "0";
						}else if(temp.getText().contains("<")) {
							orientation = "1";
						}else {
							orientation = "1";
						}
						save.writeln(x_pos +", "+ y_pos +", "+ z_pos +", "+ orientation);
						
						urscript.writeln("movej(p["+Float.toString(home_x)+", "+Float.toString(home_y)+", "+Float.toString((float)(home_z+0.05))+", 3.2, -0.0383, 0.011],3.000,0.300,0,0.001)");
						
						urscript.writeln("set digital out(0, 1)");
						urscript.writeln("movel(p["+Float.toString(home_x)+", "+Float.toString(home_y)+", "+Float.toString((float)(home_z))+", 3.2, -0.0383, 0.011],3.000,0.300,0,0.001)");
						urscript.writeln("movel(p["+Float.toString(home_x)+", "+Float.toString(home_y)+", "+Float.toString((float)(home_z+0.05))+", 3.2, -0.0383, 0.011],3.000,0.300,0,0.001)");
						
						urscript.writeln("movej(p["+Float.toString(x_pos)+", "+Float.toString(y_pos)+", "+Float.toString((float)(z_pos+0.05))+", 3.2, -0.0383, 0.011],3.000,0.300,0,0.001)");
						urscript.writeln("movel(p["+Float.toString(x_pos)+", "+Float.toString(y_pos)+", "+Float.toString(z_pos)+", 3.2, -0.0383, 0.011],3.000,0.300,0,0.001)");
						urscript.writeln("set digital out(0, 0)");
						urscript.writeln("movel(p["+Float.toString(x_pos)+", "+Float.toString(y_pos)+", "+Float.toString((float)(z_pos+0.05))+", 3.2, -0.0383, 0.011],3.000,0.300,0,0.001)");
						
						urscript.writeln("movej(p["+Float.toString(home_x)+", "+Float.toString(home_y)+", "+Float.toString((float)(home_z+0.05))+", 3.2, -0.0383, 0.011],3.000,0.300,0,0.001)");
						
						//urscript.writeln("The pallet position is:"+Float.toString((float)pallet_x/1000)+","+Float.toString((float)pallet_y/1000)+","+Float.toString((float)pallet_z/1000));
						//urscript.writeln("The package position is:"+Float.toString(x_pos)+","+Float.toString(y_pos)+","+Float.toString(z_pos));
						//urscript.writeln("The original position is:"+Float.toString(((float)((temp.getLocation().x + temp.getSize().width/2)*x_ratio)/1000))+","+Float.toString(((float)((temp.getLocation().y + temp.getSize().height/2)* y_ratio)/1000)));
						
						//  movel(p[-0.400000, 0.065382, -0.031471, -1.926875, -1.926875, 0.516304],3.000,0.300,0,0.001)
					}
				}
				urscript.close();
				save.close();
			}
		});

		main.add(pallet);

		//Add a layer textbox with 2 buttons to go up and down
		final JTextField layer_text = new JTextField();
		JButton layer_up = new JButton("+");
		JButton layer_down = new JButton("-");

		layer_text.setSize(50, 40);
		layer_up.setSize(60,40);
		layer_down.setSize(60,40);

		layer_text.setLocation(300, 400);
		layer_up.setLocation(360,400);
		layer_down.setLocation(420,400);

		layer_text.setText("1");

		layer_up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int layer = Integer.parseInt(layer_text.getText());
				layer = layer+1;

				if( button_layout.size() <= layer) {
					button_layout.add(new ArrayList<JButton>());
				}
				GUIPalletSetup.layer = layer-1;
				layer_text.setText(Integer.toString(layer));

				pallet.removeAll();
				for( int i = 0; i < button_layout.get(GUIPalletSetup.layer).size(); i++) {
					pallet.add(button_layout.get(GUIPalletSetup.layer).get(i));
				}
				pallet.repaint();

			}
		});

		layer_down.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int layer = Integer.parseInt(layer_text.getText());
				if( layer > 1) {
					layer = layer-1;
				}
				GUIPalletSetup.layer = layer-1;
				layer_text.setText(Integer.toString(layer));

				pallet.removeAll();
				for( int i = 0; i < button_layout.get(GUIPalletSetup.layer).size(); i++) {
					pallet.add(button_layout.get(GUIPalletSetup.layer).get(i));
				}
				pallet.repaint();
			}
		});
		

		main.add(layer_text);
		main.add(layer_up);
		main.add(layer_down);



		// Create a background and load in a custom image
		BackgroundPanel bg = null;
		try{
			Image img = ImageIO.read(getClass().getResource("/bg_pallet.png"));
			bg = new BackgroundPanel(img);
			bg.setBounds(0, 0, 800, 600);
		} catch(Exception ex) {
			//
		}
		main.add(bg);

		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		main.setVisible(true);
	}

}
