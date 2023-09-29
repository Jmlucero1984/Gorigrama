
package Utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/* @author jmlucero */
public class imageSaver {
    public static void save(BufferedImage bufferedImg, int width, int height) throws IOException {
        
 
     
     
    
    File  file = new File("C:\\Users\\jmluc\\Desktop\\cruci__.jpg");
    ImageIO.write(bufferedImg, "jpg", file);
    }
 


}
