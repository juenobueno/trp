package therobotpeople.urcap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class GuiTextField extends JTextField implements ActionListener{

	GuiTextField() {
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		QWERTYKeyboard temp = new QWERTYKeyboard();
		temp.text = this;
		Thread t = new Thread(temp);
		t.start();
		
	}
}

/*
package_width_text.addActionListener(new ActionListener() {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		//GUIHome gui = new GUIHome(writer);
	    
		QWERTYKeyboard temp = new QWERTYKeyboard();
		temp.text = package_width_text;
		//QWERTYKeyboard.run();
		Thread t = new Thread(temp);
		t.start();
		
		
	}

});

*/
