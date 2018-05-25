package therobotpeople.urcap;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
    Image image;
    
    /** Creates a new instance of Canvas */
    public ImagePanel() {
    	image = null;
    }
    
    public void changeImage(String file_name) {
    	try{
        	this.image = ImageIO.read(GUIConfigure.class.getResource("/" + file_name));
        } catch (Exception ex) {
        	//
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
    
}