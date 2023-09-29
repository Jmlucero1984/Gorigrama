 

package misc;
 
import GUI.Main;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashGorigrama  extends Main implements ActionListener  {
    static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"Crucigramas", "Autodefinidos", "Ejercicios Mentales"};
        Font font;
        try {
                    font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/JetBrainsMono-Medium.ttf"))
                    .deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //  g.setComposite(AlphaComposite.Clear);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.fillRect(295,385,200,20);
        g.setPaintMode();
        g.setColor(Color.BLACK);

        g.drawString("Loading "+comps[(frame/5)%3]+"...", 300, 400);
    }
    public SplashGorigrama() throws IOException {
       super( );

        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        for(int i=0; i<100; i++) {
            renderSplashFrame(g, i);
            splash.update();
            try {
                Thread.sleep(10);
            }
            catch(InterruptedException e) {
            }
        }
        splash.close();
        setVisible(true);
        toFront();
    }
    public void actionPerformed(ActionEvent ae) {
        System.exit(0);
    }
    
    private static WindowListener closeWindow = new WindowAdapter(){
        public void windowClosing(WindowEvent e){
            e.getWindow().dispose();
        }
    };
    
    public static void main (String args[]) {

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            SplashGorigrama test = new SplashGorigrama();
            
        } catch (IOException ex) {
            System.out.println("Exception "+ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SplashGorigrama.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}
