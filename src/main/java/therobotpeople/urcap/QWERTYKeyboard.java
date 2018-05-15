package therobotpeople.urcap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class QWERTYKeyboard implements Runnable{
	public static String output = "";
	public static Boolean running = false;
	public static JTextField text = new JTextField();
	public static int button_width = 50;
	public static int button_height = 50;
	public static Boolean lower_case = true;
	private static String[] firstRow = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "<<"};
	private static String[] secondRow = {"q","w","e","r","t","y","u","i","o","p","[","]"};
	private static String[] thirdRow = {"a","s","d","f","g","h","j","k","l",";","'"};
	private static String[] fourthRow = {"z","x","c","v","b","n","m",",",".","/"};
	
	private static String[] FIRSTROW = {"!","@","#","$","%","^","&","*","(",")","_","+"};
	private static String[] SECONDROW = {"Q","W","E","R","T","Y","U","I","O","P","{","}"};
	private static String[] THIRDROW = {"A","S","D","F","G","H","J","K","L",":","\""};
	private static String[] FOURTHROW = {"Z","X","C","V","B","N","M","<",">","?"};

	public final static JFrame mainframe = new JFrame();;
	
	/*public QWERTYKeyboard() {
		QWERTYKeyboard.mainframe.addFocusListener(new FocusListener() {
			private boolean gained = false;
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				gained = true;
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(gained == true) {
					QWERTYKeyboard.mainframe.dispose();
				}
			}
			
		});
		
	}//*/
	
	public static void reset() {
		QWERTYKeyboard.output = "";
		QWERTYKeyboard.running = false;
	}
	
	//public static void run() {
	public void run() {
		//open a jframe with a bit for text
		QWERTYKeyboard.running = true;
		
		//JFrame main = new JFrame();
		//main.setUndecorated(true);
		mainframe.setLayout(null);
		mainframe.setSize(480,300);
		//main.setLocation(0, 300);
		

		setLower();
		setBottom();
		
		
		mainframe.setVisible(true);
		
		//Shift, Space, yes, no, backspace are special
	}
	
	private static void setLower() {
		keyRow(firstRow, 0, 0);
		keyRow(secondRow, 0, QWERTYKeyboard.button_height);
		keyRow(thirdRow, 0, 2*QWERTYKeyboard.button_height);
		keyRow(fourthRow, 0, 3*QWERTYKeyboard.button_height);
	}
	
	private static void setUpper() {
		keyRow(FIRSTROW, 0, 0);
		keyRow(SECONDROW, 0, QWERTYKeyboard.button_height);
		keyRow(THIRDROW, 0, 2*QWERTYKeyboard.button_height);
		keyRow(FOURTHROW, 0, 3*QWERTYKeyboard.button_height);
		
	}
	
	private static void setBottom() {
		//shift
		JButton shift = new JButton("Shift");
		shift.setSize(QWERTYKeyboard.button_width, QWERTYKeyboard.button_height);
		shift.setLocation(0, 4*QWERTYKeyboard.button_height);
		shift.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				QWERTYKeyboard.mainframe.removeAll();
				
				if( QWERTYKeyboard.lower_case == true) {
					QWERTYKeyboard.lower_case = false;
					setUpper();
					setBottom();
				}else {
					QWERTYKeyboard.lower_case = true;
					setLower();
					setBottom();
				}
				QWERTYKeyboard.mainframe.repaint();
			}
		});
		//space
		JButton space = new JButton();
		space.setSize(200, QWERTYKeyboard.button_height);
		space.setLocation((int)(shift.getLocation().x + shift.getSize().getWidth()), 4*QWERTYKeyboard.button_height);
		space.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				QWERTYKeyboard.output += " ";
			}
		});
		//yes
		JButton yes = new JButton("yes");
		yes.setSize(QWERTYKeyboard.button_width, QWERTYKeyboard.button_height);
		yes.setLocation((int)(space.getLocation().x + space.getSize().getWidth()), 4*QWERTYKeyboard.button_height);
		yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent argo0) {
				QWERTYKeyboard.running = false;
				QWERTYKeyboard.mainframe.dispose();
				
				QWERTYKeyboard.text.setText(QWERTYKeyboard.output);
			}
		});
		//no
		JButton no = new JButton("no");
		no.setSize(QWERTYKeyboard.button_width, QWERTYKeyboard.button_height);
		no.setLocation((int)(yes.getLocation().x + yes.getSize().getWidth()), 4*QWERTYKeyboard.button_height);
		no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent argo0) {
				QWERTYKeyboard.output = "";
				QWERTYKeyboard.running = false;
				QWERTYKeyboard.mainframe.dispose();
			}
		});
		
		
		QWERTYKeyboard.mainframe.add(shift);
		QWERTYKeyboard.mainframe.add(space);
		QWERTYKeyboard.mainframe.add(yes);
		QWERTYKeyboard.mainframe.add(no);
	}
	
	private static void keyRow(String[] characters, int start_x, int start_y) {
		for( int i = 0; i < characters.length; i++) {
			String val = characters[i];
			
			final JButton temp = new JButton(val);
			temp.setSize(QWERTYKeyboard.button_width, QWERTYKeyboard.button_height);
			temp.setLocation(start_x+i*QWERTYKeyboard.button_width, start_y);
			temp.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					QWERTYKeyboard.output += temp.getText();
					
					if( QWERTYKeyboard.lower_case == false) {
						QWERTYKeyboard.lower_case = true;
						QWERTYKeyboard.mainframe.removeAll();
						setLower();
						setBottom();
						QWERTYKeyboard.mainframe.repaint();
					}
				}
				
			});

			QWERTYKeyboard.mainframe.add(temp);
		}
	}
}
