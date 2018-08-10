package therobotpeople.urcap;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;

import com.ur.urcap.api.domain.userinteraction.RobotPositionCallback;
import com.ur.urcap.api.domain.value.Pose;
import com.ur.urcap.api.domain.value.jointposition.JointPositions;

public class Setup_Impl extends Setup_GUI {

	public Setup_Impl(int pallet_x, int pallet_y, int pallet_z, int pallet_width, int pallet_height, int package_width,
			int package_height, int edge_gap, int box_gap, int rotation, String folder, String file_name) {
		super(pallet_x, pallet_y, pallet_z, pallet_width, pallet_height, package_width, package_height, edge_gap, box_gap,
				rotation, folder, file_name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionListener chooseOriginListener() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.setVisible(false);
				
				TRPProgramNodeContribution.api.getUserInteraction().getUserDefinedRobotPosition(new RobotPositionCallback() {
					@SuppressWarnings("deprecation")
					@Override
					public void onOk(Pose pose, JointPositions jointPositions) {
						home_x = (float)(pose.getPosition().getX());
						home_y = (float)(pose.getPosition().getY());
						home_z = (float)(pose.getPosition().getZ());
						main.setVisible(true);
					}
				});
			}
		};
		return res;
	}

	@Override
	public ActionListener packageUpListener() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Selector.selected = "^";
			}
		};
		return res;
	}

	@Override
	public ActionListener packageDownListener() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Selector.selected = "v";
			}
		};
		return res;
	}

	@Override
	public ActionListener packageLeftListener() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Selector.selected = "<";
			}
		};
		return res;
	}

	@Override
	public ActionListener packageRightListener() {
		ActionListener res = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Selector.selected = ">";
			}
		};
		return res;
	}

	@Override
	public MouseAdapter palletListener() {
		MouseAdapter res = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if( Selector.selected != "" || Selector.selected != null) {
					Point position = e.getPoint();
					JButton pack = new JButton(Selector.selected + Selector.count + (Setup_GUI.layer+1));

					int x_pos, y_pos, pack_height, pack_width = -1;
					if( Selector.selected == "<" || Selector.selected == ">") {
						pack_width = package_height;
						pack_height = package_width;
					}else {
						pack_width = package_width;
						pack_height = package_height;
					}
					pack.setSize(pack_width, pack_height);
					pack_width = pack_width+box_gap;
					pack_height = pack_height+box_gap;
					
					x_pos = position.x;
					y_pos = position.y;
					
					int left_distance = grid.left_limit(position, pack_width, pack_height);
					int right_distance= grid.right_limit(position, pack_width, pack_height);

					if( left_distance == position.x) {
						if( right_distance+pack_width+position.x == pallet_width) {
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
						if( bottom_distance+pack_height+position.y == pallet_height) {
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
						palletized.add(pack);
						
						button_layout.get(layer).add(pack);
						
						pallet.add(pack);
						main.repaint();
						
						Selector.selected = "";
						Selector.count++;
					}
				}
			}
		};
		return res;
	}

	@Override
	public ActionListener undoListener() {
		// TODO Auto-generated method stub
		ActionListener res = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(button_layout.get(layer).size() >= 1) {
					JButton to_Remove = button_layout.get(layer).get(button_layout.get(layer).size()-1);
					button_layout.get(layer).remove(to_Remove);
					
					grid.clear(to_Remove.getLocation(), to_Remove.getSize().width+box_gap, to_Remove.getSize().height+box_gap);
					
					Selector.count--;
					
					pallet.remove(to_Remove);
					pallet.revalidate();
					pallet.repaint();
				}
			}
		};
		return res;
	}

	@Override
	public ActionListener exitListener() {
		// TODO Auto-generated method stub
		ActionListener res = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TRPProgramNodeContribution.gui_home_thread.run();
				main.dispose();
			}
		};
		return res;
	}

	@Override
	public ActionListener getPositionListener() {
		// TODO Auto-generated method stub
		ActionListener res = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileManipulate urscript = new FileManipulate("wayPoint.script","../programs");
				FileManipulate save = new FileManipulate(file_name, folder);
				
				for( int i = 0; i< button_layout.size(); i++) {
					for( int j = 0; j < button_layout.get(i).size(); j++) {
						JButton temp = button_layout.get(i).get(j);
						float x_pos = ((float)pallet_x/1000) + ((float)((temp.getLocation().x + temp.getSize().width/2)*x_ratio)/1000);
						float y_pos = ((float)pallet_y/1000) + ((float)((temp.getLocation().y + temp.getSize().height/2)* y_ratio)/1000);
						float z_pos = (float)pallet_z/1000 + (float)(package_depth*i)/1000;
						float orientation = 0;
						if( temp.getText().contains("<") || temp.getText().contains(">")) {
							orientation = 90;
						}
						
						//moveJ(Home)
						//moveL(pick up)
						//digital_out(1)
						//moveL(home)
						//movej(BoxApproach + orientation)
						//moveL(box)
						//digital_out(0)
						//nivel(boxApproach)
						//moveJ(Home)
						
						
					}
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
				int layer = Integer.parseInt(layer_text.getText());
				layer = layer+1;
				
				if( button_layout.size() <= layer) {
					button_layout.add(new ArrayList<JButton>());
				}
				layer = layer-1;
				layer_text.setText(Integer.toString(layer));
				
				pallet.removeAll();
				for( int i = 0; i < button_layout.get(layer).size(); i++) {
					pallet.add(button_layout.get(layer).get(i));
				}
				pallet.repaint();
			}
		};
		return res;
	}

	@Override
	public ActionListener layerDownListener() {
		// TODO Auto-generated method stub
		ActionListener res = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int layer = Integer.parseInt(layer_text.getText());
				if( layer > 1) {
					layer = layer-1;
				}
				layer = layer-1;
				layer_text.setText(Integer.toString(layer));

				pallet.removeAll();
				for( int i = 0; i < button_layout.get(layer).size(); i++) {
					pallet.add(button_layout.get(layer).get(i));
				}
				pallet.repaint();
			}
		};
		return res;
	}

}
