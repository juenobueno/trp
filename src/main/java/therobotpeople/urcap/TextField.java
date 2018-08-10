package therobotpeople.urcap;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TextField extends JTextField implements MouseListener {

	TextField() {
		addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		new VirtualKeyboard((JTextField)this);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}