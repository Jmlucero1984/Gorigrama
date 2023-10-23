
package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/* @author jmlucero */
public class ImagePanel extends JPanel {
private BufferedImage image;

    public ImagePanel() {
        // Initialize the panel
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint(); // Repaint to display the new image
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }
}
