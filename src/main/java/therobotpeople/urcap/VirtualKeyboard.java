package therobotpeople.urcap;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

// Rewrite of QWERTY Keyboard with most of the static removed
public class VirtualKeyboard {
	private String output;
	private Boolean running;
	private JTextField text;
	private final int button_width = 50;
	private final int button_height = 50;
	private Boolean lower_case;
	
	public final String[] firstRow = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
	public final String[] secondRow = {"q","w","e","r","t","y","u","i","o","p"};
	public final String[] thirdRow = {"a","s","d","f","g","h","j","k","l"};
	public final String[] fourthRow = {"z","x","c","v","b","n","m"};
	
	public final String[] FIRSTROW = {"!","@","#","$","%","^","&","*","(",")","_","+"};
	public final String[] SECONDROW = {"Q","W","E","R","T","Y","U","I","O","P","{","}"};
	public final String[] THIRDROW = {"A","S","D","F","G","H","J","K","L",":","\""};
	public final String[] FOURTHROW = {"Z","X","C","V","B","N","M","<",">","?"};

	private final JFrame mainframe;
	
	public VirtualKeyboard(JTextField current_textfield){
		this.output = current_textfield.getText();
		this.running = false;
		this.text = current_textfield;
		this.lower_case = true;
		this.mainframe = new JFrame();

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addVetoableChangeListener("focusedWindow", new VetoableChangeListener() {
			private boolean gained = false;
			
			@Override
			public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException{
				if( evt.getNewValue() == mainframe) {
					gained = true;
				}
				if ( gained && evt.getNewValue() != mainframe) {
					mainframe.dispose();
				}
			}
		});
		
		set_running(true);
		
		this.mainframe.setLayout(null);
		this.mainframe.setSize(500, 250);
		this.mainframe.setLocationRelativeTo(null);
		this.mainframe.setLocation(300, 350);
		
		setLower();
		setBottom();
		
		//preview.setSize(button_width*5,button_width);
		//preview.setLocation(0, 0);
		//mainframe.add(preview);
		
		this.mainframe.setVisible(true);
		
	}
	
	private void setLower() {
		keyRow(this.firstRow, 0, 0);
		keyRow(this.secondRow, 0, 1*this.button_height);
		keyRow(this.thirdRow, 25, 2*this.button_height);
		keyRow(this.fourthRow, 75, 3*this.button_height);
	}
	
	private void setUpper() {
		keyRow(this.FIRSTROW, 0, 0);
		keyRow(this.SECONDROW, 0, 1*this.button_height);
		keyRow(this.THIRDROW, 25, 2*this.button_height);
		keyRow(this.FOURTHROW, 75, 3*this.button_height);
	}
	
	
	private void keyRow(String[] characters, int start_x, int start_y) {
		for( int i = 0; i < characters.length; i++) {
			String val = characters[i];
			
			final JButton temp = new JButton(val);
			temp.setSize(this.button_width, this.button_height);
			temp.setLocation(start_x+i*this.button_width, start_y);
			temp.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if( temp.getText() == "<<") {
						output = output.substring(0, output.length()-1);
						text.setText(output);
					}else {
						output += temp.getText();
						text.setText(output);
					}
					if( lower_case == false) {
						lower_case = true;
						mainframe.removeAll();
						setLower();
						setBottom();
						mainframe.repaint();
					}
					
				}
				
			});

			mainframe.add(temp);
		}
	}
	
	
	private void setBottom() {
		/*
		
		//shift
		JButton shift = new JButton("Shift");
		shift.setSize(button_width * 2, button_height);
		shift.setLocation(0, 5*button_height);
		shift.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainframe.removeAll();
				
				if( lower_case == true) {
					lower_case = false;
					setUpper();
					setBottom();
				}else {
					lower_case = true;
					setLower();
					setBottom();
				}
				mainframe.repaint();
			}
		});
		
		*/
		
		// Close
		JButton no = new JButton("Close");
		no.setSize(button_width * 3, button_height);
		no.setLocation(0, 4*button_height);
		no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent argo0) {
				//output = "";
				running = false;
				mainframe.dispose();
			}
		});
		
		//space
		JButton space = new JButton();
		space.setSize(button_width * 4, button_height);
		space.setLocation((int)(no.getLocation().x + no.getSize().getWidth()), 4*button_height);
		space.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				output += " ";
				text.setText(output);
			}
		});
		// Backspace 
		JButton yes = new JButton("Backspace");
		yes.setSize(button_width * 3, button_height);
		yes.setLocation((int)(space.getLocation().x + space.getSize().getWidth()), 4*button_height);
		yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent argo0) {
				if (output.length() == 0) {
					return; 
				}
				
				output = output.substring(0, output.length()-1);
				text.setText(output);
			}
		});
		
		
		
		//mainframe.add(shift);
		mainframe.add(space);
		mainframe.add(yes);
		mainframe.add(no);
		
	}
	
	
	public void toggle_lower_case() {
		this.lower_case = !this.lower_case;
	}
	
	public Boolean get_lower_case() {
		return this.lower_case;
	}
	
	public void set_lower_case(Boolean val) {
		this.lower_case = val;
	}
	
	public void set_text(JTextField val) {
		this.text = val;
	}
	
	public void set_output(String val) {
		this.output = val;
	}
	
	public void toggle_running() {
		this.running = !this.running;
	}
	
	public void set_running(Boolean val) {
		this.running = val;
	}
	
	public Boolean get_running() {
		return running;
	}
	
	public void add_output(String val) {
		this.output += val;
	}
	
	public String get_output() {
		return this.output;
	}
	
	public void reset_output() {
		this.output = "";
	}
}
