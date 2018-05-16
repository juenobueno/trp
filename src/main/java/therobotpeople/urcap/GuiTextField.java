package therobotpeople.urcap;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GuiTextField extends JTextField implements MouseListener {


	GuiTextField(){
		addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//QWERTYKeyboard temp = new QWERTYKeyboard();
		//temp.text = this;
		VirtualKeyboard temp = new VirtualKeyboard();
		temp.set_text(this);
		Thread t = new Thread(temp);
		t.start();
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