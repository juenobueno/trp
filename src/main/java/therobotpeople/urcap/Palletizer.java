package therobotpeople.urcap;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Palletizer {

	private int width;
	private int height;
	private Color color;
	
	public Palletizer() {
		this(400,400,Color.ORANGE);
	}
	
	public Palletizer(int width, int height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public JPanel Pallet() {
		JPanel pallet = new JPanel();
		pallet.setBackground(this.color);
		pallet.setLayout(null);
		pallet.setSize(this.width, this.height);
		
		pallet.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(Selector.selected != "" && Selector.selected != null) {
					Point position = e.getPoint();
					
					int x_pos = position.x;
					int y_pos = position.y;
					int pack_height, pack_width;
					if (Selector.selected == "<" || Selector.selected == ">") {
						//pack_width = this.package_height;
						//pack_height = this.packcage_width;
					}else {
						//pack_width = this.package_width;
						//pack_height = this.package_height;
					}
					
				}
			}
			
		});

		return pallet;
	}
}
