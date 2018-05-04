package therobotpeople.urcap;

import java.awt.*;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
    Image image;
    /** Creates a new instance of Canvas */
    public BackgroundPanel(Image img) {
        image = img;              
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
    
}