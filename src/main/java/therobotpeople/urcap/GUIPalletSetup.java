package therobotpeople.urcap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIPalletSetup {
	final int pallet_x;
	final int pallet_y;
	final int pallet_z;
	
	final int pallet_width;
	final int pallet_height;
	
	final int package_width;
	final int package_height;
	
	final int edge_gap;
	final int box_gap;
	
	final JFrame main;
	final Grid grid;
	final ArrayList<ArrayList<JButton>> button_layout;
	
	private static int layer = 0;
	
	public GUIPalletSetup() {
		this(0,0,0,400,400,100,50,0,0);
	}
	
	public GUIPalletSetup(int pallet_x, int pallet_y, int pallet_z, int pallet_width, int pallet_height, int package_width, int package_height, int edge_gap, int box_gap) {
		this.pallet_x = pallet_x;
		this.pallet_y = pallet_y;
		this.pallet_z = pallet_z;
		
		this.pallet_width = pallet_width;
		this.pallet_height = pallet_height;
		
		this.package_width = package_width;
		this.package_height = package_height;
		
		this.edge_gap = edge_gap;
		this.box_gap = box_gap;
		
		this.main = new JFrame();
		this.main.setLayout(null);
		this.main.setSize(800, 600);
		
		
		grid = new Grid(pallet_width, pallet_height);
		grid.set(new Point(0,0), pallet_width, pallet_height);
		grid.clear(new Point(edge_gap,edge_gap),pallet_width - edge_gap, pallet_height - edge_gap);

		button_layout = new ArrayList<ArrayList<JButton>>();
		button_layout.add(new ArrayList<JButton>());
	}
	
	public void run() {

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

					System.out.println("The x and y positions are x: "+x_pos+" , y: "+y_pos+" ");

					int left_distance = grid.left_limit(position, pack_width, pack_height);
					int right_distance= grid.right_limit(position, pack_width, pack_height);

					System.out.println("The Left Distance to nearest object is: "+left_distance);
					System.out.println("The Right distance to nearest object is: "+right_distance);
					if(left_distance == position.x) {
						System.out.println("LL: There is no object between this and the Left wall");

						if(right_distance+pack_width+position.x == pallet_width) {
							//In line with the grid, lock into grid
							System.out.println("RR: There is no object between this and the Right wall");

							x_pos = x_pos - x_pos%pack_width;

						}else {
							//need to align the right
							System.out.println("==NEED TO ALIGN RIGHT");
							x_pos = x_pos + (right_distance)%pack_width+1; //need to +1 to get correct alignment
							//x_pos = x_pos + pack_width-((x_pos+pack_width)%pack_width);

						}

					}else {
						//need to align the left

						if(left_distance == 0 && right_distance == 0) {
							x_pos = x_pos - x_pos%pack_width + edge_gap;
						}else {

							System.out.println("==NEED TO ALIGN LEFT");
							x_pos = x_pos  - left_distance%pack_width;
						}
						//x_pos = x_pos - x_pos%pack_width;
					}
					//The box is now lined up horizontally
					System.out.println("====New X_POS is "+x_pos);

					int top_distance = grid.top_limit(position, pack_width, pack_height);
					int bottom_distance = grid.bottom_limit(position, pack_width, pack_height);

					System.out.println("The Top Distance to nearest object is: "+top_distance);
					System.out.println("The Bottom Distance to nearest object is: "+bottom_distance);

					if(top_distance == position.y) {
						System.out.println("TT: There is no object between this and the Top wall");
						if(bottom_distance+pack_height+position.y == pallet_height) {
							//In line with grid lock into grid
							System.out.println("BB: There is no object between this and the Bottom wall");

							y_pos = y_pos - y_pos%pack_height;
						}else {
							//need to align the bottom
							System.out.println("==NEED TO ALIGN BOTTOM");
							//y_pos = y_pos + pack_height - ((y_pos+pack_height)%pack_height);
							y_pos = y_pos+(bottom_distance)%pack_height+1;
						}

					}else {
						//need to align the top
						if(top_distance== 0 && bottom_distance == 0) {
							y_pos = y_pos - y_pos%pack_height + edge_gap;

						}else {
						System.out.println("==NEED TO ALIGN TOP");
						y_pos = y_pos - top_distance%pack_height;
						//y_pos = y_pos - y_pos%pack_height;
						}
					}
					System.out.println("====New Y_POS is "+y_pos);

					pack.setLocation(x_pos, y_pos);

					System.out.println(position.x);

					if(grid.set_Object(new Point(pack.getLocation().x, pack.getLocation().y ),  pack_width, pack_height)) {


						System.out.println(pack.getLocation());

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
		undo.setSize(package_width, package_height);
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
		exit.setSize(package_width, package_height);
		exit.setLocation(250, 200);
		main.add(exit);

		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.dispose();
			}
		});

		JButton get_positions = new JButton("Get Positions");
		get_positions.setSize(package_width, package_height);
		get_positions.setLocation(100,200);
		main.add(get_positions);


		get_positions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileManipulate save = new FileManipulate("Waypoint");
				for( int i = 0; i < button_layout.size(); i++) {
					save.writeln("==== Layer "+i+" ====");
					for( int j = 0; j < button_layout.get(i).size(); j++) {
						JButton temp = button_layout.get(i).get(j);
						int x_pos = temp.getLocation().x + temp.getSize().width/2;
						int y_pos = temp.getLocation().y + temp.getSize().height/2;
						int z_pos = 0;
						String orientation = "";
						if(temp.getText().contains("^")) {
							orientation = "0";
						}else if(temp.getText().contains("v")) {
							orientation = "180";
						}else if(temp.getText().contains("<")) {
							orientation = "270";
						}else {
							orientation = "90";
						}
						save.writeln(x_pos +", "+ y_pos +", "+ z_pos +", "+ orientation);
					}
				}
				
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
				
				//Need to clear the pallet and load in any relevant boxes
				//repopulate(pallet, button_layout.get(GUIPalletSetup.layer));
				
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
				//repopulate(pallet, button_layout.get(GUIPalletSetup.layer));
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
		
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		main.setVisible(true);
	}
	
	public void repopulate(JPanel pallet, ArrayList<JButton> layer) {
		//Will take the pallet and repopulate it
		pallet.removeAll();
		for( int i = 0; i < layer.size(); i++) {
			pallet.add(layer.get(i));
		}
		
	}
}
