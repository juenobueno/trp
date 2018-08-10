package therobotpeople.urcap;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * ImagePanel allows images to be imported on to JPanels. 
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
    Image image;
    
    // Constructor takes in the file name of the image located in the resources folder. 
    public ImagePanel(String file_name) {
    	try {
        	this.image = ImageIO.read(getClass().getResource("/" + file_name));
        } catch (Exception ex) {
        	//
        }
    }
    
    public void changeImage(String file_name) {
    	try {
        	this.image = ImageIO.read(getClass().getResource("/" + file_name));
        	this.repaint();
        } catch (Exception ex) {
        	//
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
    
}