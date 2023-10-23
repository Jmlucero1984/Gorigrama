/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import static java.awt.image.AffineTransformOp.TYPE_BICUBIC;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;

import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;
import org.apache.fontbox.util.autodetect.FontFileFinder;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author jmlucero
 */
public class TextWrapper extends javax.swing.JFrame {

    /**
     * Creates new form TextWrapper
     */
    //jTextPane.setEditorKit(new HTMLEditorKit());
    String desiderata = "Camina plácido entre el ruido y la prisa, y recuerda la paz que se puede encontrar en el silencio."
            + " En cuanto te sea posible y sin rendirte, mantén buenas relaciones con todas las personas. "
            + "Enuncia tu verdad de una manera serena y clara, y escucha a los demás, incluso al torpe e ignorante,"
            + " también ellos tienen su propia historia. Evita a las personas ruidosas y agresivas,"
            + " ya que son un fastidio para el espíritu. Si te comparas con los demás, te volverás vano"
            + " o amargado pues siempre habrá personas más grandes y más pequeñas que tú. Disfruta de tus éxitos,"
            + " lo mismo que de tus planes. Mantén el interés en tu propia carrera, por humilde que sea, ella es un "
            + "verdadero tesoro en el fortuito cambiar de los tiempos. Sé cauto en tus negocios, pues el mundo está lleno"
            + " de engaños. Pero no dejes que esto te vuelva ciego para la virtud que existe, hay muchas personas que se"
            + " esfuerzan por alcanzar nobles ideales, la vida está llena de heroísmo. Sé tú mismo, y en especial no"
            + " finjas el afecto, y no seas cínico acerca del amor, pues en medio de todas las arideces y desengaños,"
            + " es perenne como la hierba. Toma amablemente el consejo de los años, abandonando con donaire las cosas"
            + " de la juventud. Cultiva la firmeza del espíritu para que te proteja de las adversidades repentinas, "
            + "mas no te agotes con pensamientos oscuros, muchos temores nacen de la fatiga y la soledad. Más allá de"
            + " una sana disciplina, sé benigno contigo mismo. Tú eres una criatura del universo, no menos que los árboles"
            + " y las estrellas, tienes derecho a existir, y sea que te resulte claro o no, indudablemente el universo "
            + "marcha como debiera. Por eso debes estar en paz con Dios, cualquiera que sea tu idea de Él, y sean cualesquiera"
            + " tus trabajos y aspiraciones, conserva la paz con tu alma en la bulliciosa confusión de la vida."
            + " Aún con todas sus farsas, penalidades y sueños fallidos, el mundo es todavía hermoso. Sé alegre. "
            + "Esfuérzate por ser feliz";

    javax.swing.text.Document doc;
    SimpleAttributeSet normal;
    BufferedImage bfToPrint;
    ImagePanel imp = new ImagePanel();

    public TextWrapper() {
        initComponents();

        doc = this.jTextPane2.getDocument();

    }

    public TextWrapper(BufferedImage bf) {
        this();
        this.bfToPrint = bf;

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new ImagePanel();
        jTextPane3 = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("LOAD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("PRINT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.setDoubleBuffered(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 300));
        jPanel2.setRequestFocusEnabled(false);

        jTextPane3.setBackground(new java.awt.Color(153, 255, 204));
        jTextPane3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jTextPane3.setPreferredSize(new java.awt.Dimension(595, 300));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 595, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTextPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTextPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel2);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        jTextPane2.setBackground(new java.awt.Color(153, 255, 204));
        jTextPane2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jTextPane2.setPreferredSize(new java.awt.Dimension(595, 300));
        jScrollPane1.setViewportView(jTextPane2);

        jPanel1.add(jScrollPane1);

        jButton3.setText("SAVE TO PDF");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToPdf(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(159, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(40, 40, 40)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap(161, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(37, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(26, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(593, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(37, 37, 37))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(69, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(69, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    private BufferedImage scaleAffine(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        AffineTransform tx = AffineTransform.getScaleInstance(0.5, 0.5);
        tx.translate(0, 30);
        BufferedImageOp bdop = new AffineTransformOp(tx, TYPE_BICUBIC);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, bdop, 0, 0);
        graphics2D.dispose();

        return resizedImage;
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            //Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\jmluc\\Desktop\\cruci__.jpg");
            //  ImageIcon icon = new ImageIcon(image);
            //    jTextPane1.insertIcon(icon);

            ImageIcon icon = new ImageIcon(scaleAffine(bfToPrint, 400, 300));
            jTextPane3.insertIcon(icon);
            // gr.drawImage(bfToPrint, 0   , 0, null);

            /*AffineTransform tx = AffineTransform.getScaleInstance(0.5, 0.5);
            tx.translate(0, 30);
            BufferedImageOp  bdop = new AffineTransformOp(tx, TYPE_BICUBIC);
            Graphics2D gr = (Graphics2D) bfToPrint.getGraphics();
            gr.drawImage(bfToPrint, bdop, 0, 0);
            gr.dispose();*/
            ((ImagePanel) jPanel2).setImage(resizeImage(bfToPrint, 400, 300));
            normal = new SimpleAttributeSet();
            StyleConstants.setAlignment(normal, StyleConstants.ALIGN_JUSTIFIED);
            StyleConstants.setForeground(normal, Color.BLACK);
            StyledDocument documentStyle = jTextPane2.getStyledDocument();

            documentStyle.setParagraphAttributes(0, documentStyle.getLength(), normal, false);
            doc.insertString(doc.getLength(), desiderata, normal);

            jTextPane2.setDocument(doc);
            jTextPane2.setSize(500, 1000);

        } catch (BadLocationException ex) {
            Logger.getLogger(TextWrapper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TextWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        MessageFormat headerFormat = new MessageFormat("dsfsdf");
        MessageFormat footerFormat = new MessageFormat("sdfsdfsf");
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        attributes.add(OrientationRequested.PORTRAIT);

        attributes.add(new JobName("My job", null));

        PrinterJob pj = PrinterJob.getPrinterJob();
        Printable pt = new Printable() {
            @Override
            public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
                if (page > 0) {
                    /* We have only one page, and 'page' is zero-based */
                    return NO_SUCH_PAGE;
                }

                /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
                 */
                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pf.getImageableX(), pf.getImageableY());

                /* Now print the window and its visible contents */
                jPanel1.printAll(g);

                /* tell the caller that this page is part of the printed document */
                return PAGE_EXISTS;
            }
        };

        pj.setPrintable(pt);

        /* locate a print service that can handle the request */
        PrintService[] services = PrinterJob.lookupPrintServices();

        System.out.println("selected printer " + services[2].getName());
        try {
            pj.setPrintService(services[2]);
            pj.pageDialog(attributes);
            if (pj.printDialog(attributes)) {
                pj.print(attributes);
            }
        } catch (PrinterException pe) {
            System.err.println(pe);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
public byte[] convertImageToByteArray(BufferedImage image, String format) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    try {
        // Write the BufferedImage to the ByteArrayOutputStream in the specified format
        ImageIO.write(image, format, byteArrayOutputStream);
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Get the byte array from the ByteArrayOutputStream
    return byteArrayOutputStream.toByteArray();
}
    private void saveToPdf(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToPdf
        com.itextpdf.text.Document pdfDoc = new com.itextpdf.text.Document(PageSize.A4);
        try {
            PdfWriter.getInstance(pdfDoc, new FileOutputStream("C:\\Users\\jmluc\\Desktop\\c.pdf"))
                    .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            pdfDoc.open();
    

            com.itextpdf.text.Font myfo;
             
             FontFactory.register("src\\main\\resources\\fonts\\JetBrainsMono-Medium.ttf", "JetBrains");
              FontFactory.registerDirectory("src\\main\\resources\\fonts");
               myfo = FontFactory.getFont("jetbrains", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
             System.out.println("REGISTERED FONTS");
             System.out.println(FontFactory.getRegisteredFonts());
               System.out.println("REGISTERED FAMILIES");
             System.out.println(FontFactory.getRegisteredFamilies());
        
       
   
           
        
            //myfo.setStyle(com.itextpdf.text.Font.NORMAL);
            myfo.setSize(12);
            pdfDoc.add(new Paragraph("\n"));
            com.itextpdf.text.Image img = new com.itextpdf.text.Jpeg(convertImageToByteArray(bfToPrint,"JPG"));
            img.setDpi(150, 150);
            img.scaleToFit(500, 500);
            pdfDoc.add(img);
            pdfDoc.add(new Paragraph("\n"));
 
            
            
            
            //BufferedReader br = new BufferedReader(new FileReader(filename));
            //String strLine;
            /*while ((strLine = br.readLine()) != null) {
			Paragraph para = new Paragraph(strLine + "\n", myfont);
			para.setAlignment(Element.ALIGN_JUSTIFIED);
			pdfDoc.add(para);
            }*/
            Paragraph para = new Paragraph(desiderata, myfo);
            para.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED);
            pdfDoc.add(para);

            pdfDoc.close();
        } catch (Exception ex) {
            Logger.getLogger(TextWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveToPdf

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TextWrapper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TextWrapper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TextWrapper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TextWrapper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TextWrapper().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    // End of variables declaration//GEN-END:variables

}
